package com.doddi.gomodserver;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import com.doddi.gomodserver.Providers.github.GithubProvider;
import com.doddi.gomodserver.gomodule.GoModuleServices;

public class WebApplicationConfig extends Application
{
  private Set<Object> singletons = new HashSet<>();

  public WebApplicationConfig(@Context ServletContext context) {
    singletons.add(new GoModuleServices(new GithubProvider((String) context.getAttribute("authToken"))));
  }

  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }
}
