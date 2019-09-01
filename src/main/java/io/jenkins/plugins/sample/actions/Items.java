package io.jenkins.plugins.sample.actions;

import javax.annotation.CheckForNull;

import hudson.model.Action;

public class Items implements Action {

	private String name;

	public Items(String name) {
		this.name = name;
	}

	@CheckForNull
	@Override
	public String getIconFileName() {
		return "document.png";
	}

	@CheckForNull
	@Override
	public String getDisplayName() {
		return "items-" + name;
	}

	@CheckForNull
	@Override
	public String getUrlName() {
		return "hello";
	}
}
