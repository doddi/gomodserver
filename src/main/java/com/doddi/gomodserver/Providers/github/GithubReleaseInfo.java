package com.doddi.gomodserver.Providers.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubReleaseInfo
{
  private String zipball_url;

  public String getZipball_url() {
    return zipball_url;
  }

  public void setZipball_url(final String zipball_url) {
    this.zipball_url = zipball_url;
  }
}
