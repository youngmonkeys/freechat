package vn.team.freechat.mvc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tavandung12 on 11/3/18.
 */

public class Controller {

    private Map<String, IView> views = new HashMap<>();

    public void addView(String action, IView view) {
        views.put(action, view);
    }

    public void removeView(String action) {
        views.remove(action);
    }

    public void updateView(String action) {
        updateView(action, null);
    }

    public void updateView(String action, Object data) {
        IView view = views.get(action);
        if(view != null)
            view.update(data);
    }
}
