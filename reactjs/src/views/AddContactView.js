import React from 'react';
import { Modal, ModalBody, ModalFooter } from 'reactstrap';
import Mvc from '../Mvc';
import SocketRequest from '../socket/SocketRequest'

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
                <img src={require('../images/avatar001.png')} alt="Avatar" className="avatar" />
                <div className="info">
                    <p>{data.username}</p>
                    <p>{data.fullName}</p>
                </div>
            </div>
            <div className="right-side">
                <div className="checkbox checkbox-primary">
                    <input id="checkbox2" type="checkbox" onChange={this.onSelect} />
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
    }

    onSelect(username, checked) {
    }
  
    render() {
      const {data} = this.props;
      const items = data.items;
      return (
        <div className="suggested-contacts searched-contacts">
          <h3>searched</h3>
          <div className="items">
          {
            Object.keys(items).map((key) => (
                <SuggestedContactItemView parent={this} key={key} data={items[key]} />
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
    }

    onSelect(username, checked) {
        this.parent.onSelect(username, checked);
    }
  
    render() {
        const {data} = this.props;
        const items = data.items;
        return (
            <div className="suggested-contacts">
            <h3>suggested</h3>
            <div className="items">
            {
                Object.keys(items).map((key) => (
                    <SuggestedContactItemView parent={this} key={key} data={items[key]} />
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
            searchedContacts : {
            'dungtv' : {username : 'dungtv', fullName : 'Ta Van Dung'}, 
            'datdt': {username : 'datdt', fullName : 'Tien Dat'}, 
            'havm': {username : 'havm', fullName : 'Vu Manh Ha'}
            },
            suggestedContacts : {}
        };
        this.selectedContacts = new Set();
        this.toggle = this.toggle.bind(this);
        this.onDone = this.onDone.bind(this);
        this.parent.toggleAddContactView = this.toggle;

        let mvc = Mvc.getInstance();
        this.contactController = mvc.getController("contact");
    }

    componentDidMount() {
        this.contactController.addDefaultView("suggestion", (users) => {
            this.setState({suggestedContacts : users});
        });
    }

    componentWillUnmount() {
        this.contactController.removeDefaultView("suggestion");
    }
  
    toggle() {
        const willShow = !this.state.show;
        if(willShow) {
            SocketRequest.requestSuggestionContacts();
        }
        else {
            this.suggestedContacts = {};
        }
        this.setState({
            show: willShow
        });
    }

    onSelect(username, checked) {
        if(checked)
            this.selectedContacts.add(username);
        else 
            this.selectedContacts.delete(username);
    }

    onDone() {
        SocketRequest.requestAddContacts(Array.from(this.selectedContacts));
        this.toggle();
    }
  
    render() {
      const {show, searchedContacts, suggestedContacts} = this.state;
      const suggestedContactsData = {items: suggestedContacts};
      const searchedContactsData = {items: searchedContacts};
      return (
            <Modal isOpen={show} toggle={this.toggle} id="addContactsModel">
                <div className="modal-header">
                    <button type="button" className="mclose" onClick={this.toggle}>&times;</button>
                    <h4 className="modal-title">Search contact</h4>
                    <button className="btn btn-primary done" onClick={this.onDone}>Done</button>
                </div>
                <ModalBody>
                    <div className="row no-gutters">
                        <div className="col">
                            <input className="form-control border-secondary border-right-0 border-left-0 rounded-0" type="search" placeholder="Search" id="example-search-input4" />
                        </div>
                        <div className="col-auto">
                            <button className="btn btn-outline-secondary border-left-0 border-right-0 rounded-0 rounded-right" type="button">
                                <i className="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </ModalBody>
                <ModalFooter>
                    <div className="selected-contacts">
                        <h3>selected</h3>
                        <div className="selected-members">
                            <div className="selected-contact">
                                <img src={require('../images/avatar001.png')} alt="Avatar" className="avatar" />
                                <p>dungtv</p>
                            </div>
                        </div>
                    </div>
                    <SeachedContactListView parent={this} data={searchedContactsData} />
                    <SuggestedContactListView parent={this} data={suggestedContactsData} />
                </ModalFooter>
            </Modal>
      );
    }
  }
  
  export default AddContactView;