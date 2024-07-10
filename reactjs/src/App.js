import Ezy from "ezyfox-es6-client";
import React, { Component } from "react";
import "./scss/main.scss";

import Mvc from 'mvc-es6'
import SocketProxy from './socket/SocketProxy'

import { ToastView } from './views/components';
import LoginView from './views/LoginView'
import MessageView from './views/MessageView'

class App extends Component {
  constructor() {
    super(...arguments);
    this.authenticated = false;
    this.state = {currentViewURI: ""};

    // setup ezyfox
    Ezy.Logger.debug = () => true;
    this.clients = Ezy.Clients.getInstance();

    // setup mvc
    this.mvc = Mvc.getInstance();
    this.mvc.newController("app");
    this.mvc.newController("contact");
    this.mvc.newController("message");
    this.mvc.newController("chat");
    this.mvc.newController("router");
    this.mvc.newController("login");
    this.mvc.newController("updatePassword")
    this.mvc.models.chat = {};

    // setup socket
    this.socketProxy = SocketProxy.getInstance();
    this.socketProxy.setup();
  }

  componentDidMount() {
    let routerController = this.mvc.getController("router");
    routerController.addDefaultView("change", viewURI => {
      this.setState({currentViewURI : viewURI});
    });
  }

  isAuthenticated() {
    let clients = Ezy.Clients.getInstance();
    let client = clients.getDefaultClient();
    return client && client.isConnected();
  }

  render() {
    let {currentViewURI} = this.state;
    let view;
    switch(currentViewURI) {
      case "/login":
        view = <LoginView />;
        break;
      case "/message":
          view = <MessageView />;
          break;
      default:
        view = <LoginView />;
        currentViewURI = "/login";
        break;
    };
    window.history.pushState('', '', currentViewURI);
    return (
        <React.Fragment>
            <ToastView />
            {view}
        </React.Fragment>

    );
  }
}
export default App;
