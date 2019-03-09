package com.doddi.gomodserver.controller.github.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubContent
{
  private String name;
  private String path;
  private String sha;
  private int size;
  private String content;
  private String encoding;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public String getSha() {
    return sha;
  }

  public void setSha(final String sha) {
    this.sha = sha;
  }

  public int getSize() {
    return size;
  }

  public void setSize(final int size) {
    this.size = size;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(final String encoding) {
    this.encoding = encoding;
  }
}
