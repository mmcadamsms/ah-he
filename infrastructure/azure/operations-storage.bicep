targetScope = 'resourceGroup'

@minLength(3)
@maxLength(8)
param namePrefix string
param location string = resourceGroup().location
@allowed(['Standard_LRS', 'Standard_ZRS'])
param operationalStorageSku string
@allowed(['Standard_LRS', 'Standard_ZRS'])
param loggingStorageSku string
@minValue(1)
@maxValue(365)
param evidenceSoftDeleteDays int
@minValue(1)
@maxValue(365)
param loggingSoftDeleteDays int
param workQueueName string
param privateEndpointSubnetId string
param privateDnsZoneIds object
param kubernetesOidcIssuer string
param kubernetesNamespace string = 'operations'

var suffix = uniqueString(resourceGroup().id, namePrefix)
var dataContributorTable = '0a9a7e1f-b9d0-4cc4-a60d-0319b160aaa3'
var dataContributorBlob = 'ba92f5b4-2d11-453d-a403-e96b0029c9fe'
var dataContributorQueue = '974c5e8b-45b9-4653-ba55-5f855dd0fb88'
var services = ['blob', 'queue', 'table']

resource apiIdentity 'Microsoft.ManagedIdentity/userAssignedIdentities@2023-01-31' = {
  name: '${namePrefix}-api'
  location: location
}
resource workerIdentity 'Microsoft.ManagedIdentity/userAssignedIdentities@2023-01-31' = {
  name: '${namePrefix}-worker'
  location: location
}
resource lokiIdentity 'Microsoft.ManagedIdentity/userAssignedIdentities@2023-01-31' = {
  name: '${namePrefix}-loki'
  location: location
}
resource apiFederation 'Microsoft.ManagedIdentity/userAssignedIdentities/federatedIdentityCredentials@2023-01-31' = {
  parent: apiIdentity
  name: 'kubernetes'
  properties: {
    audiences: ['api://AzureADTokenExchange']
    issuer: kubernetesOidcIssuer
    subject: 'system:serviceaccount:${kubernetesNamespace}:operations-api'
  }
}
resource workerFederation 'Microsoft.ManagedIdentity/userAssignedIdentities/federatedIdentityCredentials@2023-01-31' = {
  parent: workerIdentity
  name: 'kubernetes'
  properties: {
    audiences: ['api://AzureADTokenExchange']
    issuer: kubernetesOidcIssuer
    subject: 'system:serviceaccount:${kubernetesNamespace}:operations-worker'
  }
}
resource lokiFederation 'Microsoft.ManagedIdentity/userAssignedIdentities/federatedIdentityCredentials@2023-01-31' = {
  parent: lokiIdentity
  name: 'kubernetes'
  properties: {
    audiences: ['api://AzureADTokenExchange']
    issuer: kubernetesOidcIssuer
    subject: 'system:serviceaccount:${kubernetesNamespace}:operations-loki'
  }
}
resource operations 'Microsoft.Storage/storageAccounts@2023-05-01' = {
  name: '${namePrefix}ops${suffix}'
  location: location
  kind: 'StorageV2'
  sku: {name: operationalStorageSku}
  properties: {
    minimumTlsVersion: 'TLS1_2'
    supportsHttpsTrafficOnly: true
    allowBlobPublicAccess: false
    allowSharedKeyAccess: false
    publicNetworkAccess: 'Disabled'
    networkAcls: {defaultAction: 'Deny', bypass: 'None'}
  }
}
resource blobService 'Microsoft.Storage/storageAccounts/blobServices@2023-05-01' = {
  parent: operations
  name: 'default'
  properties: {
    isVersioningEnabled: true
    deleteRetentionPolicy: {enabled: true, days: evidenceSoftDeleteDays}
    containerDeleteRetentionPolicy: {enabled: true, days: evidenceSoftDeleteDays}
  }
}
resource evidence 'Microsoft.Storage/storageAccounts/blobServices/containers@2023-05-01' = {
  parent: blobService
  name: 'operational-evidence'
  properties: {publicAccess: 'None'}
}
resource tableService 'Microsoft.Storage/storageAccounts/tableServices@2023-05-01' = {
  parent: operations
  name: 'default'
  properties: {}
}
resource records 'Microsoft.Storage/storageAccounts/tableServices/tables@2023-05-01' = {
  parent: tableService
  name: 'OperationalRecords'
  properties: {}
}
resource queueService 'Microsoft.Storage/storageAccounts/queueServices@2023-05-01' = {
  parent: operations
  name: 'default'
  properties: {}
}
resource workQueue 'Microsoft.Storage/storageAccounts/queueServices/queues@2023-05-01' = {
  parent: queueService
  name: workQueueName
  properties: {}
}
resource logging 'Microsoft.Storage/storageAccounts@2023-05-01' = {
  name: '${namePrefix}log${suffix}'
  location: location
  kind: 'StorageV2'
  sku: {name: loggingStorageSku}
  properties: {
    minimumTlsVersion: 'TLS1_2'
    supportsHttpsTrafficOnly: true
    allowBlobPublicAccess: false
    allowSharedKeyAccess: false
    publicNetworkAccess: 'Disabled'
    networkAcls: {defaultAction: 'Deny', bypass: 'None'}
  }
}
resource loggingBlobService 'Microsoft.Storage/storageAccounts/blobServices@2023-05-01' = {
  parent: logging
  name: 'default'
  properties: {
    isVersioningEnabled: false
    deleteRetentionPolicy: {enabled: true, days: loggingSoftDeleteDays}
  }
}
resource logContainer 'Microsoft.Storage/storageAccounts/blobServices/containers@2023-05-01' = {
  parent: loggingBlobService
  name: 'loki'
  properties: {publicAccess: 'None'}
}
resource operationEndpoints 'Microsoft.Network/privateEndpoints@2023-09-01' = [for service in services: {
  name: '${namePrefix}-ops-${service}'
  location: location
  properties: {
    subnet: {id: privateEndpointSubnetId}
    privateLinkServiceConnections: [{
      name: service
      properties: {privateLinkServiceId: operations.id, groupIds: [service]}
    }]
  }
}]
resource operationDns 'Microsoft.Network/privateEndpoints/privateDnsZoneGroups@2023-09-01' = [for (service, index) in services: {
  parent: operationEndpoints[index]
  name: 'default'
  properties: {
    privateDnsZoneConfigs: [{
      name: service
      properties: {privateDnsZoneId: privateDnsZoneIds[service]}
    }]
  }
}]
resource loggingEndpoint 'Microsoft.Network/privateEndpoints@2023-09-01' = {
  name: '${namePrefix}-logs-blob'
  location: location
  properties: {
    subnet: {id: privateEndpointSubnetId}
    privateLinkServiceConnections: [{
      name: 'blob'
      properties: {privateLinkServiceId: logging.id, groupIds: ['blob']}
    }]
  }
}
resource loggingDns 'Microsoft.Network/privateEndpoints/privateDnsZoneGroups@2023-09-01' = {
  parent: loggingEndpoint
  name: 'default'
  properties: {
    privateDnsZoneConfigs: [{
      name: 'blob'
      properties: {privateDnsZoneId: privateDnsZoneIds.blob}
    }]
  }
}
resource apiTableRole 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(records.id, apiIdentity.id, dataContributorTable)
  scope: records
  properties: {
    principalId: apiIdentity.properties.principalId
    principalType: 'ServicePrincipal'
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', dataContributorTable)
  }
}
resource workerTableRole 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(records.id, workerIdentity.id, dataContributorTable)
  scope: records
  properties: {
    principalId: workerIdentity.properties.principalId
    principalType: 'ServicePrincipal'
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', dataContributorTable)
  }
}
resource apiBlobRole 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(evidence.id, apiIdentity.id, dataContributorBlob)
  scope: evidence
  properties: {
    principalId: apiIdentity.properties.principalId
    principalType: 'ServicePrincipal'
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', dataContributorBlob)
  }
}
resource workerQueueRole 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(workQueue.id, workerIdentity.id, dataContributorQueue)
  scope: workQueue
  properties: {
    principalId: workerIdentity.properties.principalId
    principalType: 'ServicePrincipal'
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', dataContributorQueue)
  }
}
resource lokiBlobRole 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(logContainer.id, lokiIdentity.id, dataContributorBlob)
  scope: logContainer
  properties: {
    principalId: lokiIdentity.properties.principalId
    principalType: 'ServicePrincipal'
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', dataContributorBlob)
  }
}
output operationalAccountName string = operations.name
output loggingAccountName string = logging.name
output tableEndpoint string = operations.properties.primaryEndpoints.table
output blobEndpoint string = operations.properties.primaryEndpoints.blob
output queueEndpoint string = operations.properties.primaryEndpoints.queue
output apiClientId string = apiIdentity.properties.clientId
output workerClientId string = workerIdentity.properties.clientId
output lokiClientId string = lokiIdentity.properties.clientId
