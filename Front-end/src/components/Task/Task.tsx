import React, {Component} from 'react';
import taskImg from './task.svg';
import './Task.scss';
import Carousel from "react-bootstrap/Carousel";
import axios from "axios";
import {CONFIG} from "../../config";
import ITask from "../StartPage/Task/ITask";
import Participant from "./Participant/Participant";
import IParticipant from "./Participant/IParticipant";
import Auth from "../../utils/Auth/Auth";
import ParticipateModal from "./ParticipateModal/ParticipateModal";
import EditTask from "../EditTask/EditTask";
import DateFormat from "../../utils/DateFormat/DateFormat"

interface IState {
    openedModal: boolean,
    task: ITask | null,
    participants: IParticipant[],
    isTask: boolean,
    isShownModalParticipate: boolean,
    canUserParticipate: boolean
}

class Task extends Component<any, IState> {
    state = {
        openedModal: false,
        task: {
            id: 0,
            title: '',
            creator: {
                firstName: '',
                id: 0,
                lastName: ''
            },
            photos: [],
            description: '',
            approvedParticipants: 0,
            numberOfParticipants: 0,
            priority: '',
            endDate: '',
            category: {
                name: '',
            },
            creationDate: ''
        },
        isTask: true,
        participants: [],
        isShownModalParticipate: false,
        canUserParticipate: false
    };

    onSaveTask = (task: ITask) => {
        this.setState({
            task: task
        });
    }

    togglePopup = (openedModal: boolean) => {
        this.setState({openedModal: openedModal})
    };

    componentDidMount = () => {
        axios(CONFIG.apiServer + 'tasks/' + this.props.match.params.id).then(res => {
            this.setState({task: res.data});
            if (Auth.isLoggedIn) {
                // eslint-disable-next-line
                if (Auth.loggedUserId != res.data.creator.id) {
                    axios(CONFIG.apiServer + 'participants/user/' + Auth.loggedUserId + '/task/' + this.props.match.params.id).then(res1 => {
                        if (!res1.data.participant) {
                            this.setState({canUserParticipate: true});
                        }
                    });
                }
            }
            axios(CONFIG.apiServer + 'participants/' + this.props.match.params.id + "/users").then(res1 => {
                let participants = res1.data;
                participants.forEach((participant: IParticipant, index: number) => {
                    participants[index].participationDate = new DateFormat().getDefaultDateFormat(participants[index].participationDate);
                });
                this.setState({participants: participants});
            });

        }).catch(error => {
            this.setState({isTask: false});
        });
    };

    isCreator = () => {
        // eslint-disable-next-line
        return Auth.isLoggedIn && Auth.loggedUserId == this.state.task.creator.id;
    };

    changeApproveStatus = (approved: boolean) => {
        const task = {...this.state.task};
        task.approvedParticipants += approved ? 1 : -1;
        this.setState({task: task});
    };

    renderParticipants = () => {
        return this.state.participants.map((participant: IParticipant, index: number) => (
            <Participant key={index}
                         participant={participant}
                         isCreator={this.isCreator()}
                         onChangeApproveStatus={this.changeApproveStatus}
                         taskId={this.state.task.id}/>
        ));
    };

    onSuccessParticipate = (participant: IParticipant) => {
        const participants: IParticipant[] = [...this.state.participants];
        participant.participationDate = new DateFormat().getDefaultDateFormat(participant.participationDate);
        participants.push(participant);
        this.setState({
            participants: participants,
            canUserParticipate: false
        });
    };

    renderButtonParticipate = () => {
        if (this.state.canUserParticipate) {
            return (<button className="btn participate-btn"
                            onClick={() => {
                                this.setState({isShownModalParticipate: true})
                            }}>Participate</button>);

        }
        if (this.isCreator()) {
            return (<button className="btn participate-btn"
                    onClick={() => {
                        this.setState({openedModal: true})
                    }}>Edit task</button>);
        }else {
            return (<button className="btn participate-btn"
                            disabled={true}
                            onClick={() => {
                                this.setState({isShownModalParticipate: true})
                            }}>Participate</button>);
        }
    };


    renderTaskPhotos = () => {
        if (this.state.task.photos.length === 0) {
            return (
                <Carousel.Item>
                    <img className="d-block" src={taskImg} alt=""/>
                </Carousel.Item>
            );
        }
        return this.state.task.photos.map((photoUrl: String , index: number) => (
            <Carousel.Item key={index}>
                <img className="d-block" src={photoUrl as any} alt=""/>
            </Carousel.Item>
        ));
    }

    render () {
        if (this.state.task.id !== 0) {
            return (
                <div className="task-page">
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col-12 col-lg-5">
                                <div className="task-photos default-task-block">
                                    <Carousel nextIcon={(<i className="fas fa-chevron-right  task-carousel-arrow"/>)}
                                              prevIcon={(<i className="fas fa-chevron-left  task-carousel-arrow"/>)}
                                              controls={this.state.task.photos.length > 1}
                                              indicators={this.state.task.photos.length > 1}>
                                        {this.renderTaskPhotos()}
                                    </Carousel>
                                </div>
                            </div>
                            <div className="col-12 col-lg-7">
                                <div className="task-additional-info default-task-block">
                                    <h1 className="task-name">{this.state.task.title}</h1>
                                    <h3 className="task-participants">
                                        {this.state.task.approvedParticipants}/{this.state.task.numberOfParticipants} participants
                                    </h3>
                                    <h3 className="task-organizer">
                                        Organized by: <a
                                        href={"/user/" + this.state.task.creator.id}>{this.state.task.creator.firstName + " " + (this.state.task.creator.lastName ? this.state.task.creator.lastName : '')}</a>
                                    </h3>
                                    <h3>
                                        Category: {this.state.task.category.name}
                                    </h3>
                                    <p className="task-description">{this.state.task.description}</p>
                                    {this.renderButtonParticipate()}
                                    <ParticipateModal onSuccessParticipate={this.onSuccessParticipate}
                                                      taskId={this.state.task.id} onHide={() => {
                                        this.setState({isShownModalParticipate: false})
                                    }} isShown={this.state.isShownModalParticipate}/>
                                </div>


                                <div className="task-participants">
                                    {this.renderParticipants()}
                                </div>
                            </div>
                        </div>
                    </div>
                    <EditTask onSaveTask={this.onSaveTask} task={this.state.task} togglePopup={this.togglePopup} isPopupShown={this.state.openedModal}/>
                </div>
            );
        } else if (this.state.isTask) {
            return (
                <div className="container-fluid">
                    <h2 className="text-center">Loading...</h2>
                </div>
            )
        } else {
            return (
                <div className="container-fluid">
                    <h2 className="text-center">Not Found</h2>
                </div>
            )
        }
    }
}

export default Task;