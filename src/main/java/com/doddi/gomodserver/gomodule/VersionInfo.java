package com.doddi.gomodserver.gomodule;

public class VersionInfo
{
  private String version;

  private String time;

  public VersionInfo(final String version, final String date) {
    this.version = version;
    this.time = date;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  public String getTime() {
    return time;
  }

  public void setTime(final String time) {
    this.time = time;
  }
}
