package vn.team.freechat;

import java.util.HashMap;
import java.util.Map;

import vn.team.freechat.controller.ConnectionController;
import vn.team.freechat.controller.ContactController;
import vn.team.freechat.controller.MessageController;

/**
 * Created by tavandung12 on 10/7/18.
 */

public final class Mvc {

    private static final Mvc INSTANCE = new Mvc();
    private final Map<Object, Object> controllers;

    private Mvc() {
        this.controllers = new HashMap<>();
        this.controllers.put(ConnectionController.class, new ConnectionController());
        this.controllers.put(ContactController.class, new ContactController());
        this.controllers.put(MessageController.class, new MessageController());
    }

    public static Mvc getInstance() {
        return INSTANCE;
    }

    public <T> T getController(Class<T> key) {
        T controller = (T)controllers.get(key);
        return controller;
    }

    public ConnectionController getConnectionController() {
        return getController(ConnectionController.class);
    }

    public ContactController getContactController() {
        return getController(ContactController.class);
    }

    public MessageController getMessageController() {
        return getController(MessageController.class);
    }

}
