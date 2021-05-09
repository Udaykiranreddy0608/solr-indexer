package com.newton.solrindexer.model;

import java.time.OffsetDateTime;

public class TestModel {
  private OffsetDateTime requestedTime;

  public TestModel(OffsetDateTime requestedTime) {
    this.requestedTime = requestedTime;
  }

  public TestModel() {}

  @Override
  public String toString() {
    return "TestModel{" + "requestedTime='" + requestedTime + '\'' + '}';
  }

  public OffsetDateTime getRequestedTime() {
    return requestedTime;
  }

  public void setRequestedTime(OffsetDateTime requestedTime) {
    this.requestedTime = requestedTime;
  }
}
