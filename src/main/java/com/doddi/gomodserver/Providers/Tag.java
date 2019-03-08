package com.doddi.gomodserver.Providers;

public class Tag
{
  private String name;
  private String sha;

  public Tag(final String name, final String sha) {
    this.name = name;
    this.sha = sha;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getSha() {
    return sha;
  }

  public void setSha(final String sha) {
    this.sha = sha;
  }
}
