// import Ezy from './lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';
import React, { Component } from 'react';
import './css/font-awesome-4.7.0/css/font-awesome.min.css';
import './css/bootstrap.css';
import './css/animate.css';
import './css/custom.css';
import './css/common.css';
import './css/main.css';
import './css/main-nav.css';
import './css/main-container.css';
import './css/footer-container.css';
import './css/login.css';
import './css/add-contact.css';
import './css/message-view.css';
import './App.css';

import Mvc from 'mvc-es6'
import SocketProxy from './socket/SocketProxy'

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

  componentWillMount() {
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

        <div>
            {view}
        </div>

    );
  }
}
export default App;
