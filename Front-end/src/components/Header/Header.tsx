import React, {Component} from 'react';
import './Header.scss';
import '../ManageTask/ManageTask'
import ManageTask from "../ManageTask/ManageTask";
import Auth from "../../utils/Auth/Auth";

class Header extends Component {
    state = {
        openedModal: false
    };

    togglePopup = (openedModal: boolean) => {
        this.setState({openedModal: openedModal})
    };

    renderNavbarButtons = () => {
        if (Auth.isLoggedIn) {
            return (
                <React.Fragment>
                    <li className="nav-item">
                        <span className="nav-link p-0" style={{"cursor": "pointer"}}
                              onClick={() => this.setState({openedModal: true})}>
                            <i className="fas fa-plus"/>
                        </span>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link p-0" href={"/user/" + Auth.loggedUserId}>
                            <i className="fas fa-user"/>
                        </a>
                    </li>
                    <li className="nav-item">
                        <span className="nav-link p-0" onClick={() => {Auth.logOut()}}>
                            <i className="fas fa-sign-out-alt"/>
                        </span>
                    </li>
                </React.Fragment>
            );
        } else {
            return (
                <React.Fragment>
                    <li className="nav-item">
                        <a className="nav-link p-0" href={"/login"}>
                            <i className="fas fa-sign-in-alt"/>
                        </a>
                    </li>
                </React.Fragment>
            );
        }
    };

    render() {
        return (
            <nav className="navbar main-navbar fixed-top">
                <a className="navbar-brand" href="/"><i className="fas fa-hands-helping"/> Unity</a>
                <div>
                    <ul className="navbar-nav navbar-expand ml-auto">
                        {this.renderNavbarButtons()}
                    </ul>
                </div>
                <ManageTask togglePopup={this.togglePopup} isPopupShown={this.state.openedModal}/>
            </nav>
        );
    }
}

export default Header;
