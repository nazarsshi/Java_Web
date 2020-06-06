import React from 'react';
import './Achievement.scss';
import achievementImage from './achievement.png';
import IAchievement from './IAchievement';
import {Link} from "react-router-dom";

class Achievement extends React.Component<IAchievement> {

    render() {
        return (
            <div className="achievement-wrapper">
                <Link to={"/task/" + this.props.id}>
                    <div className="achievement">
                        <div className="achievement-img">
                            <img src={achievementImage} alt=""/>
                        </div>
                        <div className="achievement-description">
                            <p>{this.props.title}</p>
                        </div>
                    </div>
                </Link>
            </div>
        );
    }
}

export default Achievement;
