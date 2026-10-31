plugins {
    `java-library`
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:3.3.0"))
    implementation(platform("com.azure:azure-sdk-bom:1.3.9"))
    api("com.fasterxml.jackson.core:jackson-databind")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.azure:azure-data-tables")
    implementation("com.azure:azure-storage-blob")
    implementation("com.azure:azure-storage-queue")
    implementation("com.azure:azure-identity")
    implementation("org.slf4j:slf4j-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
