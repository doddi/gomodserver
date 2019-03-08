package com.doddi.gomodserver.Providers.github;

public class GithubCommit
{
  private String sha;
  private String url;

  public String getSha() {
    return sha;
  }

  public void setSha(final String sha) {
    this.sha = sha;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }
}
