package io.jenkins.plugins.sample.hello;

import hudson.model.Run;
import jenkins.model.RunAction2;

import javax.annotation.CheckForNull;

public class HelloWorldAction implements RunAction2 {
    private transient Run run;
    private String name;

    public HelloWorldAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @CheckForNull
    @Override
    public String getIconFileName() {
        return "document.png";
    }

    @CheckForNull
    @Override
    public String getDisplayName() {
        return "Greeting";
    }

    @CheckForNull
    @Override
    public String getUrlName() {
        return "greeting";
    }

    @Override
    public void onAttached(Run<?, ?> run) {
        this.run = run;
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        this.run = run;
    }

    public Run getRun() {
        return run;
    }
}
