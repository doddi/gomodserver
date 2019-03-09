package com.doddi.gomodserver.controller.github.entity;

public class GithubTag
{
  private String name;
  private String zipball_url;
  private String tarball_url;
  private GithubCommit commit;
  private String node_id;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getZipball_url() {
    return zipball_url;
  }

  public void setZipball_url(final String zipball_url) {
    this.zipball_url = zipball_url;
  }

  public String getTarball_url() {
    return tarball_url;
  }

  public void setTarball_url(final String tarball_url) {
    this.tarball_url = tarball_url;
  }

  public GithubCommit getCommit() {
    return commit;
  }

  public void setCommit(final GithubCommit commit) {
    this.commit = commit;
  }

  public String getNode_id() {
    return node_id;
  }

  public void setNode_id(final String node_id) {
    this.node_id = node_id;
  }
}
