package edu.gcu.cst239.vatala.dominic.milestone_app;

/**
 * Provides the main entry point for the Milestone 1
 * storefront application.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class MilestoneApplication {
    /**
 * Creates the milestone application entry-point object.
 */
public MilestoneApplication() {
}


    /**
     * Starts the Milestone 1 application.
     *
     * @param args command-line arguments; not used by this application
     */
    public static void main(String[] args) {
        System.out.println("Milestone application starting...");

        StartupRunner startupRunner = new StartupRunner();
        startupRunner.run();
    }
}