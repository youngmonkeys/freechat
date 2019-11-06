import * as React from 'react';
import Mvc from '../Mvc';
import SocketProxy from '../socket/SocketProxy'

class LoginView extends React.Component {
    constructor(props) {
        super(props);
        this.onLogin = this.onLogin.bind(this);
        this.onUsernameChange = this.onUsernameChange.bind(this);
        this.onPasswordChange = this.onPasswordChange.bind(this);
        let mvc = Mvc.getInstance();
        let models = mvc.models;
        this.loginController = mvc.getController("login");
        this.connection = models.connection;
        if(!this.connection) {
            this.connection = {
                username : "dungtv",
                password : "123456"
            };
            models.connection = this.connection;
        }
        this.state = this.connection;
    }

    componentDidMount() {
        this.loginController.addDefaultView('connect', () => {
            this.props.history.push("/message");
        })
    }

    componentWillUnmount() {
        this.loginController.removeViews('connect');
    }

    onLogin() {
        this.connection.url = this.state.url;
        this.connection.username = this.state.username;
        this.connection.password = this.state.password;
        let socketProxy = SocketProxy.getInstance();
        socketProxy.connect();
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
                <div className="login-div">
                    <div className="login-title">
                        <h1 className="text-light">Login to your account </h1>
                    </div>
                    <div>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text" id="basic-addon-username"><i className="icon-user"></i></span>
                            </div>
                            <input type="text" className="form-control" placeholder="username" aria-label="username" aria-describedby="basic-addon-username"
                                value={username} onChange={this.onUsernameChange} />
                        </div>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text" id="basic-addon-password"><i className="icon-key"></i></span>
                            </div>
                            <input type="password" className="form-control" placeholder="password" aria-label="password" aria-describedby="basic-addon-password" 
                                value={password} onChange={this.onPasswordChange} />
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
       );
    }
 }

 export default LoginView;