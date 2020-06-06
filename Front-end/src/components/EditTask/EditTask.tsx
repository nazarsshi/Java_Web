import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/esm/Button";
import React, {Component} from "react";
import '../ManageTask/ManageTask.scss';
import {CONFIG} from "../../config";
import axios from "axios";
import Auth from "../../utils/Auth/Auth";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import NumericInput from 'react-numeric-input';
import Validation from "../../utils/Validation/Validation";
import ITask from "../StartPage/Task/ITask";

type Props = {
    task: ITask;
    togglePopup: Function;
    isPopupShown: any;
    onSaveTask: Function;
}

class EditTask extends Component <Props> {

    state: { [id: string]: any; } = {
        formControls: {
            title: {
                value: this.props.task.title,
                valid: false,
                errorMessage: 'Enter valid name',
                showValidate: false,
                validation: {
                    required: true,
                    minLength: 5,
                    maxLength: 100
                }
            },
            description: {
                value: this.props.task.description,
                valid: false,
                errorMessage: 'Enter valid description',
                showValidate: false,
                validation: {
                    required: true,
                    minLength: 10,
                    maxLength: 1500
                }
            },
            category: {
                value: this.props.task.category.id
            },
            endDate: {
                value: new Date(this.props.task.endDate),
                valid: false,
                errorMessage: 'Enter valid end date',
                showValidate: false,
                validation: {
                    minDate: new Date()
                }
            },
            possibleNumberOfParticipants: {
                value: this.props.task.numberOfParticipants,
                valid: false,
                errorMessage: 'Enter valid number of participants',
                showValidate: false,
                validation: {
                    minNumber: 2,
                    maxNumber: 100
                }
            },
            images: []
        },
        categories: [],
        errorMessage: ''
    };

    parseDate(date: any) {
        let year = date.getFullYear();
        let month = parseInt(date.getMonth()+1);
        let day = date.getDate();
        return year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
    }

    componentDidMount(): void {
        axios(CONFIG.apiServer + 'categories/').then(data => {
            this.setState({categories: data.data})
        });
    }

    onChangeHandler = (event: any, controlName: string) => {
        const formControls = {...this.state.formControls};
        const control = {...formControls[controlName]};

        control.value = (event.target as HTMLInputElement).value;
        let validateControlInfo = this.validateControl(control.value, control.validation);
        control.valid = validateControlInfo.isValid;
        control.showValidate = !control.valid;
        if (validateControlInfo.errorMessage !== '') {
            control.errorMessage = validateControlInfo.errorMessage;
        }

        formControls[controlName] = control;

        this.setState({
            formControls
        });
    };

    validateControl = (value: any, validation: any) => {
        if (!validation) {
            return {isValid: true, errorMessage: ''};
        }

        let validator = new Validation();

        let isValid = true;
        let errorMessage = '';

        if (validation.required && isValid) {
            isValid = !validator.isEmpty(value);
            if (!isValid) errorMessage = 'Field is required.';
        }

        if (validation.minLength && isValid) {
            isValid = validator.checkMinLength(value, validation.minLength);
            if (!isValid) errorMessage = 'You should put minimum ' + validation.minLength + ' chars.';
        }

        if (validation.maxLength && isValid) {
            isValid = validator.checkMaxLength(value, validation.maxLength);
            if (!isValid) errorMessage = 'You can put maximum ' + validation.maxLength + ' chars.';
        }

        if (validation.minDate && isValid) {
            isValid = validator.checkMinDate(value, validation.minDate) || false;
            if (!isValid) errorMessage = "You can put minimum date: " + validation.minDate + '.';
        }

        if (validation.minNumber && isValid) {
            isValid = validator.checkMinNumber(value, validation.minNumber);
            if (!isValid) errorMessage = 'You can put minimum ' + validation.minNumber + '.';
        }

        if (validation.maxNumber && isValid) {
            isValid = validator.checkMaxNumber(value, validation.maxNumber);
            if (!isValid) errorMessage = 'You can put maximum ' + validation.maxNumber + '.';
        }

        return {isValid: isValid, errorMessage: errorMessage};
    };

    onSubmitHandler = () => {
        let isValid = true;
        const formControls = {...this.state.formControls};

        Object.keys(formControls).forEach((controlName) => {
            if (controlName === "images" || controlName === "category") {
                return;
            }
            const control = {...formControls[controlName]};
            let validateControlInfo = this.validateControl(control.value, control.validation);

            control.valid = validateControlInfo.isValid;
            control.showValidate = !control.valid;
            if (validateControlInfo.errorMessage !== '') {
                control.errorMessage = validateControlInfo.errorMessage;
            }

            formControls[controlName] = control;

            if (!control.valid && isValid) {
                isValid = false;
            }
        });
        if (!isValid) {
            this.setState({
                formControls: formControls
            });
        }
        let photos:any[] = [];
        this.state.formControls.images.forEach((photo: { url: any; }) => {
            photos.push(photo.url);
        });

        let data = {
            apiKey: Auth.loggedApiKey,
            title: this.state.formControls.title.value,
            description: this.state.formControls.description.value,
            category: this.state.formControls.category.value,
            photos: photos,
            possibleNumberOfParticipants: this.state.formControls.possibleNumberOfParticipants.value,
            endDate: this.parseDate(this.state.formControls.endDate.value)
        };
        if (isValid) {
            axios({
                url: CONFIG.apiServer + 'tasks/'+this.props.task.id.toString(),
                data: data,
                method: 'put',
                params: {
                    userId: Auth.loggedUserId
                }
            }).then((res) => {
                this.props.onSaveTask(res.data);
                this.props.togglePopup(false);
            }).catch();
        }
    };

    onFileChangeHandler = (files: any) => {
        let reader = new FileReader();
        let file = files[0];

        reader.onloadend = () => {
            let formControls = {...this.state.formControls};
            let control = {
                file: file,
                url: reader.result
            };
            if (formControls.images.length < 6) {
                formControls.images.push(control);
                this.setState({
                    formControls: formControls
                });
            }
        };

        reader.readAsDataURL(file)
    };

    renderPhoto = (photo: any, index:number): any => {
        return (<div key={index} className={"col-lg-2"}>
            <img key={index} style={{width: "inherit"}} src={photo} alt={""}/>
        </div>)
    };

    renderPhotos = () => {
        let photos = this.state.formControls.images;
        let HTML:any = [];
        photos.forEach((photo:any, index:number) => {
            HTML.push(this.renderPhoto(photo.url, index));
        });
        return (                    <div className={"row"}>
            {HTML} </div>);
    };

    handleDateChange(date:any, controlName: string) {
        const formControls = {...this.state.formControls};
        const control = {...formControls[controlName]};
        control.value = date;
        let validateControlInfo = this.validateControl(control.value, control.validation);
        control.valid = validateControlInfo.isValid;
        control.showValidate = !control.valid;
        if (validateControlInfo.errorMessage !== '') {
            control.errorMessage = validateControlInfo.errorMessage;
        }

        formControls[controlName] = control;

        this.setState({
            formControls
        });
    }

    handleNumericChange(int: any, controlName:string) {
        const formControls = {...this.state.formControls};
        const control = {...formControls[controlName]};

        control.value = int;
        let validateControlInfo = this.validateControl(control.value, control.validation);

        control.valid = validateControlInfo.isValid;
        control.showValidate = !control.valid;
        if (validateControlInfo.errorMessage !== '') {
            control.errorMessage = validateControlInfo.errorMessage;
        }

        formControls[controlName] = control;

        this.setState({
            formControls
        });
    }

    renderOptionsForSelect() {
        let options:any = [];
        let categories = this.state.categories;
        categories.forEach((category:any, index: number) => {
            if(this.props.task.category.id === category.id)
            options.push((<option selected value={category.id} key={index}>{category.name}</option> ));
            else{
                options.push((<option  value={category.id} key={index}>{category.name}</option> ));
            }
        });
        return options;
    }

    render() {
        return (<Modal size="lg" show={this.props.isPopupShown} onHide={() => this.props.togglePopup(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit task</Modal.Title>
                    <p>{this.state.errorMessage}</p>
                </Modal.Header>
                <Modal.Body>
                    <div className={"row"}>
                        <div className={"col-lg-10"}>
                            <div className="form-group">
                                <label>Task name</label>
                                <input className={"form-control " + (this.state.formControls.title.showValidate ? 'validation' : '')} value={this.state.formControls.title.value}
                                       onChange={(event: any) => this.onChangeHandler(event, "title")}/>
                                <p style={{display: (this.state.formControls.title.showValidate ? 'block' : 'none'), color: "red"}}>{this.state.formControls.title.errorMessage}</p>
                            </div>
                        </div>
                        <div className={"col-lg-2"}>
                            <div className="avatar-edit">
                                <input type="file" id="taskImageUpload" disabled={this.state.formControls.images.length === 6}
                                       onChange={(event: any) => this.onFileChangeHandler((event.target as HTMLInputElement).files)}
                                       />
                                <label htmlFor="taskImageUpload" style={{opacity: (this.state.formControls.images.length === 6 ? "0.2" : "1")}}>
                                    <i style={{marginTop: "10px"}} className={"fas fa-camera"}/>
                                </label>
                            </div>
                        </div>
                        <div className={"col-lg-4"}>
                            <div className="form-group">
                                <label>Category</label>
                                <select className="form-control" name="priority" onChange={(event: any) => this.onChangeHandler(event, "category")}>
                                    {this.renderOptionsForSelect()}
                                </select>
                            </div>
                        </div>
                        <div className={"col-lg-4"}>
                            <div className="form-group">
                                <label style={{display: 'block'}}>End date</label>
                                <DatePicker className={"form-control " + (this.state.formControls.endDate.showValidate ? 'validation' : '')} selected={this.state.formControls.endDate.value} onChange={(date: any) => this.handleDateChange(date, 'endDate')}
                                            dateFormat="yyyy-MM-dd"
                                />
                                <p style={{display: (this.state.formControls.endDate.showValidate ? 'block' : 'none'), color: "red"}}>{this.state.formControls.endDate.errorMessage}</p>

                            </div>
                        </div>
                        <div className={"col-lg-4"}>
                            <div className="form-group">
                                <label>Participants</label>
                                <NumericInput className={"form-control " + (this.state.formControls.possibleNumberOfParticipants.showValidate ? 'validation' : '')} onChange={(numeric: any) => this.handleNumericChange(numeric, 'possibleNumberOfParticipants')}
                                              value={this.props.task.numberOfParticipants.toString()}/>
                                <p style={{display: (this.state.formControls.possibleNumberOfParticipants.showValidate ? 'block' : 'none'), color: "red"}}>{this.state.formControls.possibleNumberOfParticipants.errorMessage}</p>

                            </div>
                        </div>
                        <div className={"col-lg-12"}>
                            <div className="form-group">
                                <label>Task description</label>
                                <textarea style={{"height": "150px"}} className={"form-control " + (this.state.formControls.description.showValidate ? 'validation' : '')}
                                          value={this.state.formControls.description.value}
                                          onChange={(event: any) => this.onChangeHandler(event, "description")}/>
                                <p style={{display: (this.state.formControls.description.showValidate ? 'block' : 'none'), color: "red"}}>{this.state.formControls.description.errorMessage}</p>

                            </div>
                        </div>
                    </div>
                    {this.renderPhotos()}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={() => this.props.togglePopup(false)}>
                        Close
                    </Button>
                    <Button variant="success" onClick={() => this.onSubmitHandler()}>
                        Save
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

}

export default EditTask;
