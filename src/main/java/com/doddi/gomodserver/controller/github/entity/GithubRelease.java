package com.doddi.gomodserver.controller.github.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRelease
{
  private String tag_name;
  private String zipball_url;
  private String node_id;
  private String published_at;

  public String getTag_name() {
    return tag_name;
  }

  public void setTag_name(final String tag_name) {
    this.tag_name = tag_name;
  }

  public String getZipball_url() {
    return zipball_url;
  }

  public void setZipball_url(final String zipball_url) {
    this.zipball_url = zipball_url;
  }

  public String getNode_id() {
    return node_id;
  }

  public void setNode_id(final String node_id) {
    this.node_id = node_id;
  }

  public String getPublished_at() {
    return published_at;
  }

  public void setPublished_at(final String published_at) {
    this.published_at = published_at;
  }
}
