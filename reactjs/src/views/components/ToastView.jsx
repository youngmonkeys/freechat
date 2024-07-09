import React from 'react';
import Mvc from 'mvc-es6';

class ToastView extends React.Component {

    constructor(props) {
        super(props);
        this.message = "";
        this.state = {
            visible: false
        };
        const mvc = Mvc.getInstance();
        this.loginController = mvc.getController("login");
        this.updatePasswordController = mvc.getController("updatePassword")
    }

    componentDidMount() {
        this.loginController.addDefaultView("loginError", e => {
            this.message = "Login error: " + e;
            this.showToast(true);
        });

        this.updatePasswordController.addDefaultView("updatePasswordError", e => {
            this.message = "Update password error: " + e;
            this.showToast(true);
        });

        this.updatePasswordController.addDefaultView("updatePasswordSuccess", () => {
            this.message = "Update password successfully!";
            this.showToast(false);
        });
    }

    componentWillUnmount() {
        this.loginController.removeAllViews();
    }

    showToast = (isError) => {
        this.setState({ visible: true }, () => {
            window.setTimeout(() => {
                this.setState({ visible: false })
            }, 2000);
        });
    }

    render() {
        let { visible } = this.state;
        return visible ? (
            <div className="toast">
                <div className="toast-body">
                    <span className="toast-message">
                        {this.message}
                    </span>
                </div>
            </div>
        ) : null;
    }
}

export default ToastView;
