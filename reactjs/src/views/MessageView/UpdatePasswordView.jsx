import React from 'react';
import {Modal, ModalBody} from 'reactstrap';
import Mvc from 'mvc-es6';
import SocketRequest from '../../socket/SocketRequest'

class UpdatePasswordView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        this.state = {
            show: false,
            oldPassword: "",
            newPassword: ""
        };
        let mvc = Mvc.getInstance();
        this.toggle = this.toggle.bind(this);
        this.parent.toggleUpdatePasswordView = this.toggle;
        this.updatePasswordController = mvc.getController("updatePassword")
    }

    componentDidMount() {
        this.updatePasswordController.addDefaultView("doneUpdatePassword", e => {
            this.setState({show: false});
        });
    }

    toggle() {
        const willShow = !this.state.show;

        this.setState({
            show: willShow
        });
    }

    onKeyDown(e) {
        if (e.key === 'Enter')
            this.onSubmitClick();

        if (e.key === 'Esc') {
            this.setState({show: false});
        }
    }

    onSubmitClick() {
        const {oldPassword, newPassword} = this.state;
        SocketRequest.requestUpdatePassword(oldPassword, newPassword);
    }

    onNewPasswordChange(e) {
        this.setState({newPassword: e.target.value})
    }

    onOldPasswordChange(e) {
        this.setState({oldPassword: e.target.value})
    }

    render() {
        const {show, oldPassword, newPassword} = this.state;
        return (
            <Modal isOpen={show} toggle={this.toggle} id="addContactsModel">
                <div className="modal-header">
                    <button type="button" className="mclose" onClick={this.toggle}>&times;</button>
                    <h4 className="modal-title">Update password</h4>
                </div>
                <ModalBody>
                    <form>
                        <div className="form-group">
                            <label htmlFor="oldPassword">Old password</label>
                            <input type="password" className="form-control" id="oldPassword"
                                   onChange={this.onOldPasswordChange.bind(this)}
                                   onKeyDown={this.onKeyDown.bind(this)}
                                   placeholder="Old password" value={oldPassword}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="newPassword">New password</label>
                            <input type="password" className="form-control" id="newPassword"
                                   onChange={this.onNewPasswordChange.bind(this)}
                                   onKeyDown={this.onKeyDown.bind(this)}
                                   placeholder="New password" value={newPassword}/>
                        </div>
                        <button type="button" className="btn btn-primary"
                                onClick={this.onSubmitClick.bind(this)}>Submit
                        </button>
                    </form>
                </ModalBody>
            </Modal>
        );
    }
}

export default UpdatePasswordView;
