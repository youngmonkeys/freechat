import * as React from 'react';
import Mvc from '../Mvc';

class LoginView extends React.Component {
    connectToServerAndLogin() {
        var loginController = Mvc.getInstance().controller.loginController;
        var loginData = {
            username : this.refs.usernameInput.value,
            password : this.refs.passwordInput.value
        };
        loginController.updateView = () => {
            console.log("access app success, change view now");
            this.props.history.push("/message");
        };
        loginController.handleConnectAndLogin(loginData);
    }
    render() {
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
                            <input ref="usernameInput" type="text" defaultValue="dungtv" className="form-control" placeholder="username" aria-label="username" aria-describedby="basic-addon-username" />
                        </div>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text" id="basic-addon-password"><i className="icon-key"></i></span>
                            </div>
                            <input ref="passwordInput" type="password" defaultValue="123456" className="form-control" placeholder="password" aria-label="password" aria-describedby="basic-addon-password" />
                        </div>
                        <div className="login-options">
                            <div className="checkbox">
                                <label>
                                    <input type="checkbox" className="styled" defaultChecked="checked" /> Remember me
                                </label>
                            </div>
                        </div>
                        <button className="btn btn-info btn-block" onClick={this.connectToServerAndLogin.bind(this)}>Login</button>
                    </div>
                </div>
       );
    }
 }

 export default LoginView;