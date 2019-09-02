package io.jenkins.plugins.sample;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import antlr.ANTLRException;
import hudson.Extension;
import hudson.model.*;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;
import io.jenkins.plugins.sample.actions.Items;
import io.jenkins.plugins.sample.hello.HelloWorldAction;
import jenkins.model.ParameterizedJobMixIn;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class HelloWorldTrigger extends Trigger<ParameterizedJobMixIn.ParameterizedJob<?, ?>> {

    private ParameterizedJobMixIn.ParameterizedJob job;
    private String name;
    private boolean useFrench;


    @DataBoundConstructor
    public HelloWorldTrigger() throws ANTLRException {
        // 每分钟构建一次
        super("* * * * *");
    }

    public String getName() {
        return name;
    }

    @DataBoundSetter
    public HelloWorldTrigger setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public HelloWorldTrigger setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
        return this;
    }

    /**
     * "添加配置"保存时触发
     *
     * @param project
     * @param newInstance 是否为新的配置
     */
    @Override
    public void start(ParameterizedJobMixIn.ParameterizedJob<?, ?> project, boolean newInstance) {
        this.job = project;
        super.start(project, newInstance);
    }

    @Override
    public void run() {
        this.job.scheduleBuild(0, new HelloWorldTriggerCause(name));
    }

    // "去除配置"保存时触发
    @Override
    public void stop() {
        super.stop();
    }

    // Job主页面的侧边栏
    @Override
    public Collection<? extends Action> getProjectActions() {
        return Arrays.asList(new HelloWorldAction(name + "1"), new Items(name + "2"));
    }

    // 使用默认的即可
    // "保存/查看Job"的时候调用
    @Override
    public TriggerDescriptor getDescriptor() {
        System.out.println("getDescriptor");
        return super.getDescriptor();
    }


    @Extension
    @Symbol("hello")
    public static final class HelloWorldTriggerDescriptor extends TriggerDescriptor {

        public HelloWorldTriggerDescriptor() {
            load();
        }

        // 是否适用
        @Override
        public boolean isApplicable(Item item) {
            return true;
        }


        // Build Triggers 的选项
        @Nonnull
        @Override
        public String getDisplayName() {
            return "Hello World";
        }

    }

    public static class HelloWorldTriggerCause extends Cause {
        private String name;

        private transient Run run;

        public HelloWorldTriggerCause(String name) {
            this.name = name;
        }

        @Override
        public String getShortDescription() {
            return "hello desc";
        }

        @Override
        public void onAddedTo(@Nonnull Run build) {
            System.out.println("onAddedTo");
            this.run = build;
        }

        /**
         * Called when a build is loaded from disk.
         * Useful in case the cause needs to keep a build reference;
         */
        @Override
        public void onLoad(@Nonnull Run<?, ?> build) {
            System.out.println("onLoad");
            this.run = build;
        }

        @Override
        public void print(TaskListener listener) {
            System.out.println("print");
            listener.getLogger().println("hello " + name);
        }
    }
}
