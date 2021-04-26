import React from 'react';
import Mvc from 'mvc-es6';
import {Toast, ToastBody, ToastHeader} from 'reactstrap';

class ToastView extends React.Component {

    constructor(props) {
        super(props);
        this.message = "";
        this.state = {
            visible: false,
            isError: true
        };
        let mvc = Mvc.getInstance();
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

        this.updatePasswordController.addDefaultView("updatePasswordSuccess", e => {
            this.message = "Update password successfully!";
            this.showToast(false);
        });

    }

    showToast = (isError) => {
        this.setState({isError});
        this.setState({visible: true,}, () => {
            window.setTimeout(() => {
                this.setState({visible: false})
            }, 5000);
        });
    }

    componentWillUnmount() {
        this.loginController.removeAllViews();
    }

    render() {
        let {visible, isError} = this.state;
        return (
            <div>
                <Toast
                    isOpen={visible}
                    className={
                        isError ? "p-3 bg-danger my-2 rounded x-toast"
                            : "p-3 bg-success my-2 rounded x-toast"
                    }>
                    <ToastHeader>
                        Notification
                    </ToastHeader>
                    <ToastBody>
                        {this.message}
                    </ToastBody>
                </Toast>
            </div>
        )
    }

}

export default ToastView;
