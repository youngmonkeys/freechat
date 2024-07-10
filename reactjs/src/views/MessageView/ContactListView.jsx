import * as React from "react";
import Mvc from "mvc-es6";
import ContactView from "./ContactView";

class ContactListView extends React.Component {
    constructor(props) {
        super(props);
        this.mvc = Mvc.getInstance();
        this.state = {
            keyword: ""
        };
    }

    onSearchChange(e) {
        var keyword = e.target.value;
        this.setState({keyword: keyword});
        SocketRequest.searchContacts(keyword, 0, 30);
    }

    componentWillUnmount() {
        this.mvc.models.chat.currentContactView = null;
    }

    render() {
        const {contacts} = this.props;
        const {keyword} = this.state;
        return (
            <React.Fragment>
                <div className="input-group contact-search">
                    <span className="input-group-text">
                        <i className="fa-solid fa-magnifying-glass"></i>
                    </span>
                    <input type="text" id="search-user-keyword"
                        className="form-control" value={keyword} 
                        onChange={this.onSearchChange.bind(this)}
                        placeholder="Search contacts..." />
                </div>
                <div className="contacts-wrapper">
                    <div className="contacts" id="contacts">
                        <ul className="contact-list">
                        {
                            contacts.map((contact, i) => (
                                <ContactView key={i} data={contact} />
                            ))
                        }
                        </ul>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default ContactListView;