package com.newton.solrindexer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "product")
public class Product {

  @Id
  @Indexed(name = "id", type = "string")
  private String id;

  @Indexed(name = "fileName", type = "string")
  private String fileName;

  @Indexed(name = "creationDate", type = "string")
  private String creationDate;

  @Indexed(name = "modifiedDate", type = "string")
  private String modifiedDate;

  @Indexed(name = "creatorTool", type = "string")
  private String creatorTool;

  @Indexed(name = "format", type = "string")
  private String format;

  @Indexed(name = "documentID", type = "string")
  private String documentID;

  @Indexed(name = "encrypted", type = "string")
  private String encrypted;

  @Indexed(name = "title", type = "string")
  private String title;

  @Indexed(name = "subject", type = "string")
  private String subject;

  @Indexed(name = "author", type = "string")
  private String author;

  @Indexed(name = "content", type = "string")
  private String content;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public String getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(String modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public String getCreatorTool() {
    return creatorTool;
  }

  public void setCreatorTool(String creatorTool) {
    this.creatorTool = creatorTool;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getDocumentID() {
    return documentID;
  }

  public void setDocumentID(String documentID) {
    this.documentID = documentID;
  }

  public String getEncrypted() {
    return encrypted;
  }

  public void setEncrypted(String encrypted) {
    this.encrypted = encrypted;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Product{"
        + "id='"
        + id
        + '\''
        + ", fileName='"
        + fileName
        + '\''
        + ", creationDate='"
        + creationDate
        + '\''
        + ", modifiedDate='"
        + modifiedDate
        + '\''
        + ", creatorTool='"
        + creatorTool
        + '\''
        + ", format='"
        + format
        + '\''
        + ", documentID='"
        + documentID
        + '\''
        + ", encrypted='"
        + encrypted
        + '\''
        + ", title='"
        + title
        + '\''
        + ", subject='"
        + subject
        + '\''
        + ", author='"
        + author
        + '\''
        + ", content='"
        + content
        + '\''
        + '}';
  }
}
