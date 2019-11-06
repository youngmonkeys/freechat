// import Ezy from './lib/ezyfox-server-es6-client'
import Ezy from 'ezyfox-es6-client';
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Redirect } from "react-router-dom";
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
import './App.css';

import Mvc from './Mvc'
import SocketProxy from './socket/SocketProxy'

import LoginView from './views/LoginView'
import MessageView from './views/MessageView'

class App extends Component {
  constructor() {
    super(...arguments);
    this.authenticated = false;

    // setup ezyfox
    Ezy.Logger.debug = () => true;
    this.clients = Ezy.Clients.getInstance();

    // setup mvc
    this.mvc = Mvc.getInstance();
    this.mvc.newController("app");
    this.mvc.newController("contact");
    this.mvc.newController("disconnect");
    this.mvc.newController("login");
    this.mvc.newController("message");
    this.mvc.newController("myProfile");

    // setup socket
    this.socketProxy = SocketProxy.getInstance();
    this.socketProxy.setup();
  }

  componentWillMount() {
    var client = this.clients.getDefaultClient();
    if(client)
      this.authenticated = client.isConnected();
  }

  render() {
    return (
      <Router>
        <div>
          <Route exact path="/" component={LoginView} />
          <Route exact path="/login" component={LoginView} />
          {/*<Route exact path="/message" component={MessageView} />*/}
          <AuthRoute exact path="/message" component={MessageView} />
          <Redirect from="*" to="/login" />
        </div>
      </Router>
    );

  }
}

const AuthRoute = ({component: Component, ...rest}) => {
  const clients = Ezy.Clients.getInstance();
  const client = clients.getDefaultClient();
  const authenticated = client && client.isConnected();
  return (
    <Route
      {...rest}
      render = {props => authenticated === true 
        ? (<Component {...props} {...rest} />) 
        : (<Redirect to="/" />)
      }
    />
  );
}

export default App;
