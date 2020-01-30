package de.lars.updater.sites;

public abstract class SiteParser {
	
	public abstract String getNewestVersionTag();
	public abstract String getNewestUrl();
	public abstract String getNewestDownloadUrl();
	public abstract String getNewestFileName();
	public abstract boolean isNewVersionAvailable();

}
