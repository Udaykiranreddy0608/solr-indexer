package com.newton.solrindexer.resource;

public class ProductRequestResource {

  private String searchTerm;

  public ProductRequestResource(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  public String getSearchTerm() {
    return searchTerm;
  }

  public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  public ProductRequestResource() {
  }

  @Override
  public String toString() {
    return "ProductRequestResource{" +
            "searchTerm='" + searchTerm + '\'' +
            '}';
  }
}
