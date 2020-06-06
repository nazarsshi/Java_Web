import React, {Component} from 'react';
import './Layout.scss';
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

class Layout extends Component {
    render() {
        return (
            <div className="App">
                <Header/>
                <div className="content">
                    {this.props.children}
                </div>
                <Footer/>
            </div>
        );
    }
}

export default Layout;