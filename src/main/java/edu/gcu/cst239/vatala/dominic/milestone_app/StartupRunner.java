package edu.gcu.cst239.vatala.dominic.milestone_app;

import edu.gcu.cst239.vatala.dominic.milestone_app.actions.StartingActions;
import edu.gcu.cst239.vatala.dominic.milestone_app.services.StoreFront;

/**
 * Creates the main application objects and launches the
 * storefront console workflow.
 *
 * @author Dominic Vatala
 * @version 1.0
 */
public class StartupRunner {
    /**
 * Creates a startup runner for the storefront application.
 */
public StartupRunner() {
}


    /**
     * Creates the shared StoreFront and starts the role-selection menu.
     */
    public void run() {
        StoreFront store = new StoreFront();

        StartingActions startingActions =
                new StartingActions(store);

        startingActions.start();
    }
}