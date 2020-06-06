import React from 'react';
import {Route, Redirect, BrowserRouter, Switch} from 'react-router-dom';
import User from './components/User/User';
import Login from './components/Login_Registration/Login';
import Registration from './components/Login_Registration/Registration';
import Main from './components/StartPage/Main';
import Task from './components/Task/Task';
import Layout from './hoc/Layout/Layout';
import './App.scss';
import Auth from "./utils/Auth/Auth";
import axios from "axios";
import {CONFIG} from "./config";

function App() {
    if (Auth.isLoggedIn) {
        axios.post(CONFIG.apiServer + "check/apiKey/user/" + Auth.loggedUserId, {
            apiKey: Auth.loggedApiKey
        }).catch((error) => {
            Auth.logOut();
        });
    }

    let curTime = Math.round(new Date().getTime()/1000);
    let expireTime = 3600;
    if (Auth.isLoggedIn && !Auth.remember) {
        if (parseInt(localStorage.getItem('lastActivity') as string) < curTime - expireTime) {
            Auth.logOut();
        }
        setTimeout(() => {
            Auth.logOut();
        }, 1000 * expireTime);
    }
    localStorage.setItem('lastActivity', curTime.toString());

    return (
        <BrowserRouter>
            <Layout>
                <Switch>
                    <Route path="/user/:id" exact component={User}/>
                    <Route path="/" exact component={Main}/>
                    <Route path="/task/:id" exact component={Task}/>
                    <Route path="/login" render={() => (!Auth.isLoggedIn ? <Login/> : <Redirect to="/"/>)}/>
                    <Route path="/registration"
                           render={() => (!Auth.isLoggedIn ? <Registration/> : <Redirect to="/"/>)}/>
                </Switch>
            </Layout>
        </BrowserRouter>
    );
}

export default App;
