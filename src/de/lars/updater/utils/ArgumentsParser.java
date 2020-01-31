package de.lars.updater.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentsParser {
	
	protected Options options;
	protected HelpFormatter helpFormatter;
	protected CommandLineParser parser;
	
	public ArgumentsParser() {
		options = new Options();
		this.addToOptions(options);
		
		parser = new DefaultParser();
		helpFormatter = new HelpFormatter();
	}
	
	
	public CommandLine parse(String[] args) throws ParseException {
		return parser.parse(options, args);
	}
	
	
	public void printHelp(String cmdSyntax) {
		helpFormatter.printHelp(cmdSyntax, options, true);
	}
	
	
	protected void addToOptions(Options options) {
		Option url = new Option("u", "url", true, "api url");
		url.setRequired(true);
		options.addOption(url);
		
		Option output = new Option("o", "output", true, "output file path");
		output.setRequired(true);
		options.addOption(output);
		
		Option curVersion = new Option("cv", "currentversion", true, "current version tag");
		curVersion.setRequired(true);
		options.addOption(curVersion);
		
		Option wait = new Option("w", "wait", false, "wait until the file is no longer used by a process");
		wait.setRequired(false);
		options.addOption(wait);
	}

}
