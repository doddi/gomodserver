package com.doddi.gomodserver;


import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebApplication
{
  public static void main(String[] args) throws Exception {
    String token = GomodWebParser.parse(args);

    Server server = new Server(8080);

    WebAppContext context = new WebAppContext();

    context.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
    context.setResourceBase("./src/main/webapp");
    context.setContextPath("/");
    if (token != null) {
      context.setAttribute("authToken", "Bearer " + token);
    }

    context.setParentLoaderPriority(true);

    server.setHandler(context);

    server.start();
    server.join();
  }
}
