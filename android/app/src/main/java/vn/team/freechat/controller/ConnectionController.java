package vn.team.freechat.controller;

import vn.team.freechat.view.ConnectionView;
import vn.team.freechat.view.LoadingView;

/**
 * Created by tavandung12 on 10/7/18.
 */

public class ConnectionController {

    private LoadingView loadingView;
    private ConnectionView connectionView;

    public void handleConnectSuccessfully() {
        loadingView.hideLoadingView();
    }

    public void handleAppAccessSuccessFully() {
        if(connectionView != null)
            connectionView.showContactView();
    }

    public void handleServerNotResponding() {
        loadingView.showLoadingView();
    }

    public void handleLostPing(int count) {
        if(connectionView != null)
            connectionView.showLostPing(count);
    }

    public void handleTryConnect(int count) {
        if(connectionView != null)
            connectionView.showTryConnect(count);
    }

    public void setLoadingView(LoadingView loadingView) {
        this.loadingView = loadingView;
    }

    public void setConnectionView(ConnectionView connectionView) {
        this.connectionView = connectionView;
    }
}
