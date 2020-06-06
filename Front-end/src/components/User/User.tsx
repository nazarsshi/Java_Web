import React from 'react';
import Tree from './Tree/Tree';
import IAchievement from './Tree/Achievement/IAchievement';
import UserInfo from './UserInfo/UserInfo';
import axios from "axios";
import {CONFIG} from "../../config";

interface IState {
    user: any,
    doneTasks: IAchievement[]
}

class User extends React.Component<any, any> {

    state = {
        user: null,
        doneTasks: [],
    };

    componentDidMount(): void {
        axios(CONFIG.apiServer + 'users/' + this.props.match.params.id).then(data => {
            if (!data.data.photo) {
                data.data.photo = '/img/avatar.png'
            }
            this.setState({user: data.data})
        });
        axios(CONFIG.apiServer + 'users/' + this.props.match.params.id + '/tasks/done').then(data => {
            let loadedAchievements: [IAchievement] = data.data;
            let achievementsToShow: IAchievement[] = [];
            for (let i = 0; i < ((loadedAchievements.length <= 10) ? loadedAchievements.length: 10);i++) {
                achievementsToShow.push(loadedAchievements[i]);
            }
            this.setState({doneTasks: achievementsToShow});
        });
    }

    render() {
        if (!this.state.user) {
            return (
                <div className="container-fluid">
                    <h2 className="text-center">Not Found</h2>
                </div>
            )
        }

        return (<div id="user_page" style={{width: "100%"}}>
            <div className="container-fluid">
                <div className="row">
                    <div className="col-lg-8 col-12 order-2 order-lg-1">
                        <Tree doneTasks={this.state.doneTasks}/>
                    </div>
                    <div className="col-lg-4 col-12 order-1 order-lg-2">
                        <UserInfo user={this.state.user}/>
                    </div>
                </div>
            </div>
        </div>)
    };
}

export default User;
