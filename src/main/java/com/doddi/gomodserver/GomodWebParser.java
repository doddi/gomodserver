package com.doddi.gomodserver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GomodWebParser
{
  public static String parse(String[] args) {
    Options options = new Options();

    Option token = new Option("t", "token", true, "authorisation token");
    token.setRequired(false);
    options.addOption(token);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("utility-name", options);

      System.exit(1);
    }

    if (cmd.hasOption("token")) {
      return cmd.getOptionValue("token");
    }
    return null;
  }
}
