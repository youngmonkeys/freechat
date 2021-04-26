import * as React from 'react';
import Mvc from 'mvc-es6';
import SocketProxy from '../../socket/SocketProxy'
import Component from '../components'

class LoginView extends React.Component {
    constructor(props) {
        super(props);
        this.onLogin = this.onLogin.bind(this);
        this.onKeyDown = this.onKeyDown.bind(this);
        this.onUsernameChange = this.onUsernameChange.bind(this);
        this.onPasswordChange = this.onPasswordChange.bind(this);
        let mvc = Mvc.getInstance();
        let models = mvc.models;
        this.connection = models.connection;
        if(!this.connection) {
            this.connection = {
                username : "",
                password : ""
            };
            models.connection = this.connection;
        }
        this.state = {
            username : this.connection.username,
            password : this.connection.password
        }
    }

    onLogin() {
        this.connection.url = this.state.url;
        this.connection.username = this.state.username;
        this.connection.password = this.state.password;
        let socketProxy = SocketProxy.getInstance();
        socketProxy.connect();
    }

    onKeyDown(e) {
        if (e.key === 'Enter')
            this.onLogin();
    }

    onUsernameChange(e) {
        this.setState({username: e.target.value});
    }

    onPasswordChange(e) {
        this.setState({password: e.target.value});
    }

    render() {
        const {username, password} = this.state;
        return (
            <div className="login-div-wrap">
                <div className="login-div">
                    <Component.ToastView />
                    <div className="login-title">
                        <h1 className="text-light">Login to your account </h1>
                        <div className="text-small">
                            Just login, register automatically
                        </div>
                    </div>
                    <div>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text" id="basic-addon-username"><i className="icon-user"></i></span>
                            </div>
                            <input type="text" className="form-control" placeholder="username" aria-label="username" aria-describedby="basic-addon-username"
                                value={username} onChange={this.onUsernameChange} onKeyDown={this.onKeyDown} />
                        </div>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text" id="basic-addon-password"><i className="icon-key"></i></span>
                            </div>
                            <input type="password" className="form-control" placeholder="password" aria-label="password" aria-describedby="basic-addon-password"
                                value={password} onChange={this.onPasswordChange} onKeyDown={this.onKeyDown} />
                        </div>
                        <div className="login-options">
                            <div className="checkbox">
                                <label>
                                    <input type="checkbox" className="styled" defaultChecked="checked" /> Remember me
                                </label>
                            </div>
                        </div>
                        <button className="btn btn-info btn-block" onClick={this.onLogin}>Login</button>
                    </div>
                </div>
            </div>
       );
    }
 }

 export default LoginView;
