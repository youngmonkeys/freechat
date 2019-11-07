package vn.team.freechat.mvc;

public interface IController {

    void addView(String action, IView view);

    void removeView(String action);

    void updateView(String action);

    void updateView(String action, Object data);

}
