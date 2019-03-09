package com.doddi.gomodserver.controller.github.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubReleaseInfo
{
  private String tag_name;
  private String published_at;

  public String getTag_name() {
    return tag_name;
  }

  public void setTag_name(final String tag_name) {
    this.tag_name = tag_name;
  }

  public String getPublished_at() {
    return published_at;
  }

  public void setPublished_at(final String published_at) {
    this.published_at = published_at;
  }
}
