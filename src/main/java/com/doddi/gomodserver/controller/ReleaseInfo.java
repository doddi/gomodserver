package com.doddi.gomodserver.controller;

public class ReleaseInfo
{
  private String version;
  private String date;

  public ReleaseInfo(final String tag_name, final String published_at) {
    this.version = tag_name;
    this.date = published_at;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  public String getDate() {
    return date;
  }

  public void setDate(final String date) {
    this.date = date;
  }
}
