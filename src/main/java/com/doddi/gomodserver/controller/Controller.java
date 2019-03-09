package com.doddi.gomodserver.controller;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

public interface Controller
{
  String name();

  List<ReleaseInfo> getReleases(final URI url);

  ReleaseInfo getRelease(final URI url, final String version);

  String getContent(final URI url, final String ref, final String filename);

  InputStream getZip(final URI url, final String version);
}
