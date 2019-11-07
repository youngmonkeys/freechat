package vn.team.freechat.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tavandung12 on 10/7/18.
 */

public final class Mvc {

    private static final Mvc INSTANCE = new Mvc();
    private final IModel model;
    private final Map<String, IController> controllers;

    private Mvc() {
        this.model = new Model();
        this.controllers = new HashMap<>();
        this.addController("connection");
        this.addController("contact");
        this.addController("message");
    }

    public static Mvc getInstance() {
        return INSTANCE;
    }

    public IModel getModel() {
        return model;
    }

    public IController addController(String action) {
        Controller controller = new Controller();
        addController(action, controller);
        return controller;
    }

    public void addController(String action, IController controller) {
        controllers.put(action, controller);
    }

    public IController getController(String action) {
        IController controller = controllers.get(action);
        return controller;
    }

}
