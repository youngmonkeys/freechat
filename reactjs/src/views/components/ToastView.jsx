import React from 'react';
import Mvc from 'mvc-es6';
import { Toast, ToastBody, ToastHeader } from 'reactstrap';

class ToastView extends React.Component {
    
    constructor(props) {
        super(props);
        this.message = "";
        this.state = {visible : false};
        let mvc = Mvc.getInstance();
        this.loginController = mvc.getController("login");
    }

    componentDidMount() {
        this.loginController.addDefaultView("loginError", e => {
            this.message = "Login error: " + e;
            this.setState({visible: true,}, () => {
                window.setTimeout(() => {
                    this.setState({visible : false})
                }, 5000);
            });
        });
    }

    componentWillUnmount() {
        this.loginController.removeAllViews();
    }

    render() {
        let {visible} = this.state;
        return (
            <div>
                <Toast
                    isOpen={visible}
                    className="p-3 bg-danger my-2 rounded x-toast">
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