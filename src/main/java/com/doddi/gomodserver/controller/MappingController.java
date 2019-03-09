package com.doddi.gomodserver.controller;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.doddi.gomodserver.gomodule.VersionInfo;

public class MappingController
{
  private Client client;

  private final Map<String, Controller> controllers;

  public MappingController(final Client client,
                           final Map<String, Controller> controllers) {
    this.client = client;
    this.controllers = controllers;
  }

  public String getReleases(final String url) {
    List<ReleaseInfo> releases = getReleaseInfos(url);

    return releases.stream()
        .map(ReleaseInfo::getVersion)
        .collect(Collectors.joining("\n"));
  }

  public VersionInfo getLatestRelease(final String url) {
    List<ReleaseInfo> releases = getReleaseInfos(url);

    ReleaseInfo releaseInfo = releases.get(0);
    return new VersionInfo(releaseInfo.getVersion(), releaseInfo.getDate());
  }

  public ReleaseInfo getRelease(final String url, final String version) {
    URI uri;
    try {
      uri = new URI("https://" + url);
    }
    catch (URISyntaxException e) {
      throw new RuntimeException("Invalid url");
    }

    URI repositoryVcsUrl = findRepositoryVcsUrl(uri);
    Controller controller = controllers.get(repositoryVcsUrl.getHost());
    return controller.getRelease(repositoryVcsUrl, version);
  }

  public String getContent(final String url, final String version, final String filename) {
    URI uri;
    try {
      uri = new URI("https://" + url);
    }
    catch (URISyntaxException e) {
      throw new RuntimeException("Invalid url");
    }

    URI repositoryVcsUrl = findRepositoryVcsUrl(uri);
    Controller controller = controllers.get(repositoryVcsUrl.getHost());
    return controller.getContent(repositoryVcsUrl, version, filename);
  }

  public InputStream getZip(final String url, final String version) {
    URI uri;
    try {
      uri = new URI("https://" + url);
    }
    catch (URISyntaxException e) {
      throw new RuntimeException("Invalid url");
    }

    URI repositoryVcsUrl = findRepositoryVcsUrl(uri);
    Controller controller = controllers.get(repositoryVcsUrl.getHost());
    return controller.getZip(repositoryVcsUrl, version);
  }

  private List<ReleaseInfo> getReleaseInfos(final String url) {
    URI uri;
    try {
      uri = new URI("https://" + url);
    }
    catch (URISyntaxException e) {
      throw new RuntimeException("Invalid url");
    }

    URI repositoryVcsUrl = findRepositoryVcsUrl(uri);
    Controller controller = controllers.get(repositoryVcsUrl.getHost());
    return controller.getReleases(repositoryVcsUrl);
  }

  private URI discoverRepositoryVcs(final URI url) {
    WebTarget target = client.target(url.toString()).queryParam("go-get", "1");

    Response response = target.request().buildGet().invoke();
    String entity = response.readEntity(String.class);

    return parseGoGetResponse(entity);
  }

  private URI parseGoGetResponse(final String message) {
    String[] lines = message.split(System.getProperty("line.separator"));

    return Arrays.stream(lines)
        .filter(line -> line.startsWith("<meta"))
        .filter(line -> line.contains("go-import"))
        .map(this::parseGoImportLine)
        .map(url -> {
          try {
            return new URI(url);
          }
          catch (URISyntaxException e) {
            e.printStackTrace();
          }
          return null;
        })
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Unable to find vcs url"));
  }

  private String parseGoImportLine(final String line) {
    String[] content = line.split("content=\"");
    String[] trailingQuotesRemoved = content[1].split("\"");
    String[] tokens = trailingQuotesRemoved[0].split(" ");

    if (tokens.length == 3) {
      return tokens[2];
    }
    return null;
  }

  private URI findRepositoryVcsUrl(final URI url) {
    if (controllers.get(url.getHost()) == null) {
      return discoverRepositoryVcs(url);
    }
    return url;
  }
}
