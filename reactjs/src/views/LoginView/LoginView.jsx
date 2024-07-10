import * as React from 'react';
import Mvc from 'mvc-es6';
import SocketProxy from '../../socket/SocketProxy'

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
            <div className="page-login">
                <div className="login-form-wrapper">
                    <div className="login-form">
                        <h1 className="login-form-title">Login to your account </h1>
                        <div className="login-form-body">
                            <div className="input-group mb-3">
                                <span className="input-group-text">
                                    <i className="fa-solid fa-user"></i>
                                </span>
                                <input type="text" className="form-control" placeholder="username"
                                    value={username} onChange={this.onUsernameChange} onKeyDown={this.onKeyDown} />
                            </div>
                            <div className="input-group mb-3">
                                <span className="input-group-text">
                                    <i className="fa-solid fa-lock"></i>
                                </span>
                                <input type="password" className="form-control" placeholder="password"
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
                            <div className="text-small">
                                Just login, register automatically!
                            </div>
                        </div>
                    </div>
                </div>
            </div>
       );
    }
 }

 export default LoginView;
