package de.lars.updater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import de.lars.updater.sites.GitHubParser;
import de.lars.updater.sites.SiteParser;
import de.lars.updater.utils.ArgumentsParser;
import de.lars.updater.utils.FileDownloader;

public class Updater {

	public static void main(String[] args) throws IOException {
		new Updater(args);
	}
	
	
	public Updater(String[] args) {
		ArgumentsParser argsParser = new ArgumentsParser();
		CommandLine cmd = null;
		
		// parse command line arguments
		try {
			cmd = argsParser.parse(args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			argsParser.printHelp("updater");
			
			exit(1);
		}
		
		String url = cmd.getOptionValue("url");
		String output = cmd.getOptionValue("output");
		String curVersion = cmd.getOptionValue("currentversion");
		
		
		// get the right parser from URL
		SiteParser parser = getSiteParser(url, curVersion);
		
		if(parser == null) {
			System.out.println("URL not supported!");
			
			exit(1);
		}
		
		// check for new version
		try {
			parser.check();
		} catch (Exception e) {
			if(e instanceof IOException) {
				System.out.println("Error while checking for new version.");
			} else {
				System.out.println("Invalid version tag!");
			}
			e.printStackTrace();
			
			exit(1);
		}
		
		
		// print check result
		if(!parser.isNewVersionAvailable()) {
			System.out.println("No new version available.");
			System.out.println("Installed: " + curVersion + ", Latest: " + parser.getNewestVersionTag());
			exit(0);
		}
		
		System.out.println("A new version is available!");
		System.out.println("Installed: " + curVersion + ", New: " + parser.getNewestVersionTag());
		System.out.println("URL: " + parser.getNewestUrl());
		
		File file = new File(output);
		
		
		// wait until file is no longer used
		if(cmd.hasOption("wait") && file.exists()) {
			boolean fileOpen = false;
			while(true) {
				
				if(file.renameTo(new File(output))) {
					break;
				} else if(!fileOpen) {
					System.out.println("Target file is opened by another process. Waiting...");
				}
				fileOpen = true;
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
			}
		}
		
		
		// download new version
		System.out.println("Downloading " + parser.getNewestFileName() + " from " + parser.getNewestDownloadUrl() + " . . .");
		try {
			FileDownloader downloader = new FileDownloader(parser.getNewestDownloadUrl(), output);
			
			if(downloader.isDownloadSuccessful()) {
				System.out.println("Download finished!");
				System.out.println("File location: " + file.getAbsolutePath());
			}
			
		} catch (IOException e) {
			System.out.println("Error while downloading file.");
			e.printStackTrace();
			exit(1);
		}
	}
	
	
	private SiteParser getSiteParser(String url, String curVersion) {
		if(url.contains("api.github.com")) {
			return new GitHubParser(curVersion, url);
		}
		return null;
	}
	
	
	private void exit(int code) {
		System.exit(code);
	}

}
