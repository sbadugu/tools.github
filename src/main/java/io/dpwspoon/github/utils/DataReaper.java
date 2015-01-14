package io.dpwspoon.github.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;

public class DataReaper {

	public static List<GHRepository> getRepositoriesWithPomsOnMainBranch()
			throws IOException {
		List<GHRepository> repositories = new ArrayList<GHRepository>();

		RepoUtils.processRepositories("kaazing",
				r -> {
					try {
						for (GHContent content : r.getDirectoryContent("/")) {
							if ("pom.xml".equalsIgnoreCase(content.getName())) {
								return true;
							}
						}
						return false;
					} catch (FileNotFoundException e) {
						return false;
					} catch (IOException e) {
						throw new RuntimeException(
								"Could not determine if repo \"" + r.getName()
										+ "\" has a pom.xml", e);
					}
				}, r -> repositories.add(r));
		return repositories;
	}
	
	public static List<GHRepository> getRepositoriesWithFile(String path, String name)
			throws IOException {
		List<GHRepository> repositories = new ArrayList<GHRepository>();

		RepoUtils.processRepositories("kaazing",
				r -> {
					try {
						for (GHContent content : r.getDirectoryContent(path)) {
							if (name.equalsIgnoreCase(content.getName())) {
								return true;
							}
						}
						return false;
					} catch (FileNotFoundException e) {
						return false;
					} catch (IOException e) {
						throw new RuntimeException(
								"Could not determine if repo \"" + r.getName()
										+ "\" has a pom.xml", e);
					}
				}, r -> repositories.add(r));
		return repositories;
	}
}