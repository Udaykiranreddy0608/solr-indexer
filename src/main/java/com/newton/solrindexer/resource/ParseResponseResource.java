package com.newton.solrindexer.resource;

import java.time.OffsetDateTime;

public class ParseResponseResource extends ParseRequestResource {

  private String updated_time = OffsetDateTime.now().toString();

  public String getUpdated_time() {
    return updated_time;
  }

  public void setUpdated_time(String updated_time) {
    this.updated_time = updated_time;
  }

  public ParseResponseResource() {}

  public ParseResponseResource(String dirPath) {
    this.setDirPath(dirPath);
  }

  @Override
  public String toString() {
    return "ParseResponseResource{" + "updated_time='" + updated_time + '\'' + '}';
  }
}
