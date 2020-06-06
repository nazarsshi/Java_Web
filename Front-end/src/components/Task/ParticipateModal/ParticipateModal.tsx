import React, {Component} from "react";
import {Button, Modal} from "react-bootstrap";
import axios from "axios";
import {CONFIG} from "../../../config";
import Auth from "../../../utils/Auth/Auth";

interface IProps {
    isShown: boolean,
    onHide: any,
    onSuccessParticipate: any,
    taskId: number
}

interface IState {
    comment: String,
    errorMessage: String
}

class ParticipateModal extends Component<IProps, IState> {
    state = {
        comment: '',
        errorMessage: '',
    }

    close = () => {
        this.setState({comment: ''});
        this.props.onHide();
    }

    onSubmit = () => {
        axios.post(CONFIG.apiServer + 'participants/' + Auth.loggedUserId + '/tasks/' + this.props.taskId,{
            apiKey: Auth.loggedApiKey,
            comment: this.state.comment
        }).then(res => {
            this.setState({errorMessage: ''});
            this.props.onSuccessParticipate(res.data);
            this.close();
        }).catch(error => {
            if (error.response.status === 401) {
                Auth.logOut();
            } else if (error.response.data.message && error.response.data.message === 'User is already participant.') {
                this.setState({errorMessage: 'User is already participant.'});
            } else {
                this.setState({errorMessage: 'Something went wrong!'});
            }
        });
    }

    render() {
        return (
            <Modal show={this.props.isShown} onHide={this.close}>
                <Modal.Header closeButton>
                    <Modal.Title>About your experience(optional)</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="form-group">
                        <textarea className="form-control"
                                  placeholder="Comment..."
                                  onChange={(e) => {this.setState({comment: e.target.value})}}
                                  value={this.state.comment}>
                        </textarea>
                        { (this.state.errorMessage !== '') ? (<div className="alert alert-danger">{this.state.errorMessage}</div>) : '' }
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.close}>Close</Button>
                    <Button variant="success" onClick={this.onSubmit}>Participate</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default ParticipateModal;
