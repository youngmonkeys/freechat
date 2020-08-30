import React from 'react';
import {Modal, ModalBody, ModalFooter} from 'reactstrap';
import Mvc from 'mvc-es6';
import SocketRequest from '../../socket/SocketRequest'

class SuggestedContactItemView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        this.onSelect = this.onSelect.bind(this);
    }

    onSelect(e) {
        var username = this.props.data.username;
        var checked = e.target.checked;
        this.parent.onSelect(username, checked);
    }

    render() {
        const {data} = this.props;
        return (
            <div className="suggested-contact">
                <div className="left-side">
                    <img src={require('../../images/avatar001.png')} alt="Avatar" className="avatar"/>
                    <div className="info">
                        <p>{data.username}</p>
                        <p>{data.fullName}</p>
                    </div>
                </div>
                <div className="right-side">
                    <div className="checkbox checkbox-primary">
                        <input id="checkbox2" type="checkbox" onChange={this.onSelect}/>
                    </div>
                </div>
            </div>
        );
    }
}

class SeachedContactListView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        let mvc = Mvc.getInstance();
        this.contactController = mvc.getController("contact");
        this.state = {
            searchedContacts: []
        };
    }

    onSelect(username, checked) {
        this.parent.onSelect(username, checked);
    }

    componentDidMount() {
        this.contactController.addDefaultView("searchContactsUsers", (contactsUsers) => {
            this.searchContactsUsers(contactsUsers);
        });
    }

    componentWillUnmount() {
        this.contactController.removeDefaultView("searchContactsUsers");
    }

    searchContactsUsers(contactsUsers) {
        this.setState({searchedContacts: contactsUsers});
    }

    render() {
        const {searchedContacts} = this.state;
        return (
            <div className="suggested-contacts searched-contacts">
                {searchedContacts.length > 0 &&
                <h3>searched</h3>
                }
                <div className="items">
                    {searchedContacts.length > 0 && searchedContacts.map((item, i) => (
                        <SuggestedContactItemView parent={this} key={i} data={item}/>
                        ))
                    }
                </div>
            </div>
        );
    }
}

class SuggestedContactListView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        let mvc = Mvc.getInstance();
        this.contactController = mvc.getController("contact");
        this.state = {suggestedContacts: []};
    }

    componentDidMount() {
        this.contactController.addDefaultView("suggestion", (users) => {
            this.setState({suggestedContacts: users});
        });
    }

    componentWillUnmount() {
        this.contactController.removeDefaultView("suggestion");
    }

    onSelect(username, checked) {
        this.parent.onSelect(username, checked);
    }

    render() {
        const {suggestedContacts} = this.state;
        return (
            <div className="suggested-contacts">
                <h3>suggested</h3>
                <div className="items">
                    {suggestedContacts.length > 0 && suggestedContacts.map((item, i) => (
                        <SuggestedContactItemView parent={this} key={i} data={item}/>
                        ))
                    }
                </div>
            </div>
        );
    }

}

class AddContactView extends React.Component {
    constructor(props) {
        super(props);
        this.parent = props.parent;
        this.state = {
            show: false,
            keyword: ""
        };
        this.selectedContacts = new Set();
        this.toggle = this.toggle.bind(this);
        this.onDone = this.onDone.bind(this);
        this.onSearchChange = this.onSearchChange.bind(this);
        this.parent.toggleAddContactView = this.toggle;
    }

    toggle() {
        const willShow = !this.state.show;
        if (willShow) {
            SocketRequest.requestSuggestionContacts();
        } else {
            this.selectedContacts = new Set();
        }
        this.setState({
            show: willShow
        });
    }

    onSelect(username, checked) {
        if (checked)
            this.selectedContacts.add(username);
        else
            this.selectedContacts.delete(username);
    }

    onDone() {
        SocketRequest.requestAddContacts(Array.from(this.selectedContacts));
        this.toggle();
    }

    onSearchChange(e) {
        var keyword = e.target.value;
        this.setState({keyword: keyword});
        SocketRequest.searchContactsUsers(keyword, 0, 30);
    }

    render() {
        const {show} = this.state;
        return (
            <Modal isOpen={show} toggle={this.toggle} id="addContactsModel">
                <div className="modal-header">
                    <button type="button" className="mclose" onClick={this.toggle}>&times;</button>
                    <h4 className="modal-title">Search contact</h4>
                    <button
                        className="btn btn-primary done"
                        onClick={this.onDone}>
                        Done
                    </button>
                </div>
                <ModalBody>
                    <div className="row no-gutters">
                        <div className="col">
                            <input className="form-control border-secondary border-right-0 border-left-0 rounded-0"
                                   type="search" placeholder="Search" id="example-search-input4"
                                   onChange={this.onSearchChange}/>
                        </div>
                        <div className="col-auto">
                            <button
                                className="btn btn-outline-secondary border-left-0 border-right-0 rounded-0 rounded-right"
                                type="button">
                                <i className="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </ModalBody>
                <ModalFooter>
                    <SeachedContactListView parent={this}/>
                    <SuggestedContactListView parent={this}/>
                </ModalFooter>
            </Modal>
        );
    }
}

export default AddContactView;