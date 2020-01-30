package de.lars.updater.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileDownloader {
	
	public FileDownloader(String url, String fileName) throws IOException {
		this(new URL(url), fileName);
	}
	
	public FileDownloader(URL url, String fileName) throws IOException {
		try (InputStream inputStream = url.openStream()) {
			ReadableByteChannel rbc = Channels.newChannel(inputStream);
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			System.out.println(fos.getFD());
			
			inputStream.close();
			rbc.close();
			fos.close();
		} finally {
			System.out.println("Download finished!");
		}
	}

}
