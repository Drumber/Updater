package de.lars.updater.sites;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.lars.updater.utils.VersionTagUtil;

public class GitHubParser extends SiteParser {
	
	private String apiUrl;
	private String curTag;
	private boolean newVersionAvailable;
	private JsonObject jsonNewest;
	
	public GitHubParser(String currentTag, String author, String repository) throws IOException {
		this(currentTag, String.format("https://api.github.com/repos/%s/%s/releases", author, repository));
	}
	
	public GitHubParser(String currentTag, String githubApiUrl) {
		apiUrl = githubApiUrl;
		curTag = currentTag;
	}
	
	
	@Override
	public void check() throws IOException {
		URL requestUrl = new URL(apiUrl);
		Scanner scanner = new Scanner(requestUrl.openStream());
		String response = scanner.useDelimiter("\\Z").next();
		JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
		JsonObject jsonObj = jsonArray.get(0).getAsJsonObject();
		jsonNewest = jsonObj;
		
		String curVersionNumber = VersionTagUtil.getVersionNumber(curTag);
		String newVersionNumber = VersionTagUtil.getVersionNumber(getNewestVersionTag());
		newVersionAvailable = VersionTagUtil.compareVersionNumber(curVersionNumber, newVersionNumber);
		
		scanner.close();
	}
	
	
	public JsonObject getNewest() {
		return jsonNewest;
	}

	@Override
	public String getNewestVersionTag() {
		return jsonNewest.get("tag_name").getAsString();
	}

	@Override
	public String getNewestUrl() {
		return jsonNewest.get("html_url").getAsString();
	}

	@Override
	public String getNewestDownloadUrl() {
		JsonObject jsonAsset = ((JsonArray) jsonNewest.get("assets")).get(0).getAsJsonObject();
		return jsonAsset.get("browser_download_url").getAsString();
	}
	
	@Override
	public String getNewestFileName() {
		JsonObject jsonAsset = ((JsonArray) jsonNewest.get("assets")).get(0).getAsJsonObject();
		return jsonAsset.get("name").getAsString();
	}

	@Override
	public boolean isNewVersionAvailable() {
		return newVersionAvailable;
	}

}
