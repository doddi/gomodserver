package com.doddi.gomodserver.controller;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.List;

public interface Controller
{
  String name();

  List<Tag> getTags(final URI url);

  ReleaseInfo getRelease(final URI url, final String version);

  String getContent(final URI url, final String ref, final String filename);

  ByteArrayInputStream getZip(final URI url, final String version);
}
