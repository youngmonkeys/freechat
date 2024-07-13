import React from 'react';
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
                        <span>{data.username}</span>
                        <span>{data.fullName}</span>
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
        const mvc = Mvc.getInstance();
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
        const mvc = Mvc.getInstance();
        this.contactController = mvc.getController("contact");
    }

    componentDidMount() {
        this.contactController.addDefaultView("showAddContactsModal", () => {
            this.toggle();
        });
    }

    componentWillUnmount() {
        this.contactController.removeDefaultView("showAddContactsModal");
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
        const {show, keyword} = this.state;
        return show ? (
            <div className="modal fade show add-contacts-modal">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        <div className="modal-header">
                            <button type="button" className="btn-close" onClick={this.toggle}>&times;</button>
                            <h4 className="modal-title">Search contact</h4>
                            <button className="btn btn-primary done" onClick={this.onDone}>
                                Done
                            </button>
                        </div>
                        <div className="input-group contact-search">
                            <span className="input-group-text">
                                <i className="fa-solid fa-magnifying-glass"></i>
                            </span>
                            <input type="text" id="search-user-keyword"
                                className="form-control" value={keyword} 
                                onChange={this.onSearchChange.bind(this)}
                                placeholder="Search contacts..." />
                        </div>
                        <div>
                            <SeachedContactListView parent={this}/>
                            <SuggestedContactListView parent={this}/>
                        </div>
                    </div>
                </div>
            </div>
        ) : null;
    }
}

export default AddContactView;