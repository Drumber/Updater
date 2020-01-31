package de.lars.updater.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileDownloader {
	
	private boolean downloadSuccessful;
	
	public FileDownloader(String url, String fileName) throws IOException {
		this(new URL(url), fileName);
	}
	
	public FileDownloader(URL url, String fileName) throws IOException {
		try (InputStream inputStream = url.openStream()) {
			ReadableByteChannel rbc = Channels.newChannel(inputStream);
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			inputStream.close();
			rbc.close();
			fos.close();
		} finally {
			downloadSuccessful = true;
		}
	}
	
	
	public boolean isDownloadSuccessful() {
		return downloadSuccessful;
	}

}
