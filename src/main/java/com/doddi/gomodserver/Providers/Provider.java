package com.doddi.gomodserver.Providers;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface Provider
{
  List<Tag> getTags(final String repository, final String owner);

  ReleaseInfo getRelease(final String repository, final String owner, final String version);

  String getContent(final String repository, final String owner, final String ref, final String filename);

  ByteArrayInputStream getZip(final String repository, final String owner, final String version);
}
