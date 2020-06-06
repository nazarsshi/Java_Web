import React, {Component} from "react";
import './Participant.scss';
import IParticipant from "./IParticipant";
import {Link} from "react-router-dom";
import axios from "axios";
import {CONFIG} from "../../../config";
import Auth from "../../../utils/Auth/Auth";

interface IProps {
    participant: IParticipant,
    isCreator: boolean,
    taskId: number,
    onChangeApproveStatus: any
}

interface IState {
    isApproved: boolean
}

class Participant extends Component<IProps, IState> {

    constructor(props: IProps) {
        super(props);
        this.state = {
            isApproved: this.props.participant.approved
        }
    }

    generateApproveButton = () => {
        if (this.props.isCreator) {
            if (this.state.isApproved) {
                return (<button className="btn btn-secondary" onClick={() => {this.changeApproveStatus(false)}}><i className="fas fa-times"/> Disapprove</button>);
            }
            return (<button className="btn btn-success" onClick={() => {this.changeApproveStatus(true)}}><i className="fas fa-check"/> Approve</button>);
        }
    }

    changeApproveStatus = (isApproved: boolean) => {
        let url = CONFIG.apiServer + 'participants/approve/' + this.props.participant.user.id + '/tasks/' + this.props.taskId + '?approved=' + isApproved;
        axios.patch(url,{
            apiKey: Auth.loggedApiKey
        }).then(res => {
            this.setState({isApproved: res.data.approved})
            this.props.onChangeApproveStatus(isApproved);
        }).catch(error => {

        });
    }

    renderCommentBlock = () => {
        if (this.props.participant.comment) {
            return (
                <div className="col-12">
                    <div className="participant-comment-block">
                        {this.props.participant.comment}
                    </div>
                </div>
            );
        }
    }

    render() {
        const avatar = this.props.participant.user.photo ? this.props.participant.user.photo : "/img/avatar.png";

        return (
            <div className="card mb-3 default-task-block task-participant-block">
                <div className="row">
                    <div className="col-3 col-xl-2 justify-content-center align-items-center d-flex">
                        <div className="task-participant-img-wrapper">
                            <Link className="task-participant-link" to={"/user/" + this.props.participant.user.id}>
                                <img src={avatar} alt=""/>
                            </Link>
                        </div>
                    </div>
                    <div className="col-7 col-xl-8">
                        <div className="card-body">
                            <h4 className="card-title">
                                <Link className="task-participant-link" to={"/user/" + this.props.participant.user.id}>
                                    {this.props.participant.user.firstName} {this.props.participant.user.lastName}
                                </Link>
                                {this.state.isApproved ? (<span className="ml-1 badge badge-pill badge-success">Approved</span>) : ''}
                            </h4>
                            <p className="card-text"><small className="text-muted">{this.props.participant.participationDate}</small></p>
                        </div>
                    </div>
                    <div className="col-2 d-flex align-items-center justify-content-center">
                        {this.generateApproveButton()}
                    </div>
                    {this.renderCommentBlock()}
                </div>
            </div>
        );
    }
}

export default Participant;
