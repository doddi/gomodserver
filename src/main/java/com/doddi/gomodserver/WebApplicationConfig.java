package com.doddi.gomodserver;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebApplicationConfig extends Configuration
{
  @Valid
  @NotNull
  private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

  @JsonProperty("jerseyClient")
  public JerseyClientConfiguration getJerseyClientConfiguration() {
    return jerseyClient;
  }

  public String getAuthToken() {
    return null;
  }
}
