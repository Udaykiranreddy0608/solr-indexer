package com.newton.solrindexer.resource;

public class ParseRequestResource {
  public ParseRequestResource() {
  }

  private String dirPath;

  public String getDirPath() {
    return dirPath;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public ParseRequestResource(String dirPath) {
    this.dirPath = dirPath;
  }

  @Override
  public String toString() {
    return "ParseRequestResource{" + "dirPath='" + dirPath + '\'' + '}';
  }
}
