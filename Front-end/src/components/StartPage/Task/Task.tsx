import React from 'react';
import defaultImage from './task.svg';
import './Task.scss';
import ITask from './ITask'
import {Link} from "react-router-dom";

export default (props: ITask) => {

    const taskImage = props.photos.length === 0 ? defaultImage : props.photos[0];

    return (
    <Link to={"/task/" + props.id} className="task-link">
        <div className="card mb-3 main-task-block">
            <div className={"priority " + props.priority.toString().toLowerCase()} />
            <div className="row no-gutters">
                <div className="col-md-4 justify-content-center align-items-center d-flex">
                    <div className="task-image">
                        <img src={taskImage as any} alt=""/>
                    </div>
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h2 className="card-title">
                            {props.title}
                        </h2>
                        <p className="card-text"><span className="text-muted">Created on {props.creationDate}</span> | <span className="text-muted">Organized by {props.creator}</span></p>
                        <h4>Category: {props.category}</h4>
                        <h4>Participants: {props.approvedParticipants} / {props.numberOfParticipants}</h4>
                        <p className="card-text task-description">{props.description}</p>
                    </div>
                </div>
            </div>
        </div>

    </Link>
)
}
