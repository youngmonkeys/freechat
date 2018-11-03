package vn.team.freechat.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tavandung12 on 10/7/18.
 */

public final class Mvc {

    private static final Mvc INSTANCE = new Mvc();
    private final Map<String, Controller> controllers;

    private Mvc() {
        this.controllers = new HashMap<>();
        this.addController("connection");
        this.addController("contact");
        this.addController("message");
    }

    public static Mvc getInstance() {
        return INSTANCE;
    }

    public Controller addController(String action) {
        Controller controller = new Controller();
        addController(action, controller);
        return controller;
    }

    public void addController(String action, Controller controller) {
        controllers.put(action, controller);
    }

    public Controller getController(String action) {
        Controller controller = controllers.get(action);
        return controller;
    }

}
