package de.lars.updater;

import java.io.IOException;

import de.lars.updater.sites.GitHubParser;
import de.lars.updater.utils.FileDownloader;

public class Updater {

	public static void main(String[] args) throws IOException {
		GitHubParser parser = new GitHubParser("0.2.0.7", "Drumber", "remoteLight");
		
		FileDownloader fd = new FileDownloader(parser.getNewestDownloadUrl(), parser.getNewestFileName());
		

	}

}
