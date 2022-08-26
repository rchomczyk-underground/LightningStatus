package com.github.shitzuu.lightningstatus;

public class ApplicationContextInitializer {

    private ApplicationContextInitializer() {

    }

    public static void main(String[] arguments) {
        ApplicationLifecycle applicationLifecycle = new ApplicationContext();
        applicationLifecycle.construct();

        // Adding a shutdown hook to the application context.
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(applicationLifecycle::dispatch));
    }
}
