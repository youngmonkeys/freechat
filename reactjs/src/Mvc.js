class Mvc {
    static instance = null;

    constructor() {
        this.controllers = {}
        this.models = {};
    }

    static getInstance() {
        if (!Mvc.instance)
            Mvc.instance = new Mvc();
        return Mvc.instance;
    }

    newController(name) {
        var controller = new Controller();
        this.addController(name, controller);
        return controller;
    }

    addController(name, controller) {
        this.controllers[name] = controller;
    }

    getController(name) {
        var controller = this.controllers[name];
        return controller;
    }
}

class Controller {

    constructor() {
        this.views = {};
    }

    addView(action, viewId, view) {
        var available = this.views[action];
        if (!available) {
            available = {};
        }
        available[viewId] = view;
        this.views[action] = available;
    }

    addDefaultView(action, view) {
        this.addView(action, "", view);
    }

    removeView(action, viewId) {
        var available = this.views[action];
        if (available) {
            delete available[viewId];
        }
    }

    removeDefaultView(action) {
        this.removeView(action, "");
    }

    removeViews(action) {
        var available = this.views[action];
        if (available) {
            for (var key in available)
                delete available[key];
        }
        delete this.views[action];
    }

    removeAllViews() {
        for(var action in this.views) {
            var available = this.views[action];
            for (var key in available)
                delete available[key];
            delete this.views[action];
        }
    }

    updateView(action, viewId, data) {
        var available = this.views[action];
        if (available) {
            var view = available[viewId];
            if(view)
                view(data);
        }
    }

    updateViews(action, data) {
        var available = this.views[action];
        if (available) {
            for (var key in available) {
                var view = available[key];
                view(data);
            }
        }
    }
}

export default Mvc