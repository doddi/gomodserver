package com.doddi.gomodserver;

import javax.ws.rs.client.Client;

import com.doddi.gomodserver.controller.MappingController;
import com.doddi.gomodserver.controller.github.GithubController;
import com.doddi.gomodserver.gomodule.GoModuleServices;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import static com.doddi.gomodserver.controller.github.GithubController.GITHUB;

public class WebApplication extends Application<WebApplicationConfig>
{
  public static void main(String[] args) throws Exception {
    new WebApplication().run(args);
  }

  @Override
  public void run(final WebApplicationConfig configuration, final Environment environment) throws Exception {
    final Client client = new JerseyClientBuilder(environment)
        .using(configuration.getJerseyClientConfiguration())
        .build(getName());

    MappingController controller = new MappingController(client,
        ImmutableMap.of(
            GITHUB, new GithubController(client, configuration.getAuthToken())
        ));

    // Server
    environment.jersey().register(new GoModuleServices(controller));
  }
}
