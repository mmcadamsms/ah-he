package com.ahhe.operations.repository;

public interface EvidenceStore {
  String putImmutable(String objectKey, byte[] bytes);

  byte[] read(String objectKey, String versionId);
}
