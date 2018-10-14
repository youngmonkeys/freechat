import React from 'react';
import EzyClients from './lib/ezy-clients'
import { Route, Redirect } from "react-router-dom";

function AuthRoute({component: Component, authenticated, ...rest}) {
    const clients = EzyClients.getInstance();
    const authenticated = clients.isConnectedDefaultClient();
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

  export default AuthRoute