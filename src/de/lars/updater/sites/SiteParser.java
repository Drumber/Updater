package de.lars.updater.sites;

import java.io.IOException;

public abstract class SiteParser {
	
	public abstract String getNewestVersionTag();
	public abstract String getNewestUrl();
	public abstract String getNewestDownloadUrl();
	public abstract String getNewestFileName();
	public abstract boolean isNewVersionAvailable();

	public void check() throws IOException, Exception {}
}
