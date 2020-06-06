import React, {FormEvent} from 'react';
import './UserInfo.scss';
import axios from "axios";
import Input from "../../../utils/UI/Input/Input";
import Validation from "../../../utils/Validation/Validation";
import {CONFIG} from "../../../config";
import Auth from "../../../utils/Auth/Auth";

class UserInfo extends React.Component<any,any> {

    constructor(props:any) {
        super(props);

        this.state = {
            formControls: {
                firstName: {
                    type: 'text',
                    placeholder: 'Name',
                    value: props.user.firstName,
                    valid: true,
                    errorMessage: 'Enter valid name',
                    showValidate: false,
                    validation: {
                        required: true,
                        maxLength: 20
                    }
                },
                lastName: {
                    type: 'text',
                    placeholder: 'Surname',
                    value: props.user.lastName,
                    valid: true,
                    errorMessage: 'Enter valid surname',
                    showValidate: false,
                    validation: {
                        maxLength: 20
                    }
                },
                description: {
                    type: 'text',
                    placeholder: 'Description',
                    value: props.user.aboutUser,
                    valid: true,
                    errorMessage: '',
                    showValidate: false,
                    validation: {
                        maxLength: 255
                    }
                },
                photo: {
                    url: props.user.photo,
                    file: null,
                    error: '',
                    valid: true
                }

            },
            editMode: false,
            currentInfo: {
                firstName: props.user.firstName,
                lastName: props.user.lastName,
                description: props.user.aboutUser,
                photo: {
                    url: props.user.photo,
                    file: ''
                }
            }
        };
    }

    handleUserDataSaveButton(event: React.MouseEvent<HTMLButtonElement>) {
        event.preventDefault();

        let isValid = true;
        let formControls = {...this.state.formControls};

        Object.keys(formControls).forEach((controlName) => {

            let control = {...formControls[controlName]};

            control.showValidate = true;
            formControls[controlName] = control;

            if (!control.valid && isValid) {
                isValid = false;
            }
        });


        if (isValid) {

            let fieldsToSave = {
                firstName: this.state.formControls.firstName.value,
                lastName: this.state.formControls.lastName.value,
                aboutUser: this.state.formControls.description.value,
                photo: this.state.formControls.photo.file ? this.state.formControls.photo.url : null,
                apiKey: Auth.loggedApiKey,
            };

            axios.put(CONFIG.apiServer + 'users/update', fieldsToSave).then(res => {
                const currentInfo = {
                    firstName: res.data.firstName,
                    lastName: res.data.lastName,
                    description: res.data.aboutUser,
                    photo: {
                        url: res.data.photo ? res.data.photo : '/img/avatar.png'
                    }
                };
                this.setState({
                    currentInfo: currentInfo,
                    editMode: false
                });
            }).catch(error => {
                if (error.response.status === 400 && error.response.data.message === 'API key is invalid') {
                    Auth.logOut();
                } else {
                    alert('unknown error');
                }
            });
        }

        this.setState({
            formControls
        });

    }

    handleCancelButton = () => {
        const formControls = {...this.state.formControls};
        formControls.firstName.value = this.state.currentInfo.firstName;
        formControls.lastName.value = this.state.currentInfo.lastName;
        formControls.description.value = this.state.currentInfo.description;
        formControls.photo.url = this.state.currentInfo.photo.url;
        this.setState({
            formControls:formControls,
            editMode: false
        });
    };

    onFileChangeHandler = (files: any) => {
        let reader = new FileReader();
        let file = files[0];

        reader.onloadend = () => {
            const formControls = {...this.state.formControls};
            const control = {...formControls.photo};
            control.file = file;
            control.url = reader.result;
            formControls.photo = control;
            this.setState({
                formControls: formControls
            });
        };

        reader.readAsDataURL(file)
    };

    validateControl = (value: string, validation: any) => {
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

        return {isValid: isValid, errorMessage: errorMessage};
    };

    onChangeHandler = (event: React.FormEvent<HTMLInputElement>, controlName: string) => {
        const formControls = {...this.state.formControls};
        const control = {...formControls[controlName]};

        control.value = (event.target as HTMLInputElement).value;
        let validateControlInfo = this.validateControl(control.value, control.validation);

        control.valid = validateControlInfo.isValid;
        if (validateControlInfo.errorMessage !== '') {
            control.errorMessage = validateControlInfo.errorMessage;
        }

        formControls[controlName] = control;

        this.setState({
            formControls
        });
    };

    renderEditForm = () => {
        return (
            <div className={"user-info-block "}>
                <div className="avatar-upload">
                    <div className="avatar-edit">
                        <input type="file" id="imageUpload"
                               onChange={(event: FormEvent<HTMLInputElement>) => this.onFileChangeHandler((event.target as HTMLInputElement).files)}/>
                        <label htmlFor="imageUpload">
                            <i className="fas fa-pen"/>
                        </label>
                    </div>
                    <div className="avatar-preview">
                        <img src={this.state.formControls.photo.url} className="avatar-preview" alt="Avatar"/>
                    </div>
                </div>
                <div className={"row"} style={{paddingTop: "15px"}}>
                    <div className={"col-md-6"}>
                        <Input
                            type={this.state.formControls.firstName.type}
                            placeholder={this.state.formControls.firstName.placeholder}
                            valid={this.state.formControls.firstName.valid}
                            iconClassName={this.state.formControls.firstName.iconClassName}
                            showValidate={this.state.formControls.firstName.showValidate}
                            errorMessage={this.state.formControls.firstName.errorMessage}
                            onChange={(e: FormEvent<HTMLInputElement>) => this.onChangeHandler(e, "firstName")}
                            value={this.state.formControls.firstName.value}/>
                    </div>
                    <div className={"col-md-6"}>
                        <Input
                            type={this.state.formControls.lastName.type}
                            placeholder={this.state.formControls.lastName.placeholder}
                            valid={this.state.formControls.lastName.valid}
                            iconClassName={this.state.formControls.lastName.iconClassName}
                            showValidate={this.state.formControls.lastName.showValidate}
                            errorMessage={this.state.formControls.lastName.errorMessage}
                            onChange={(e: FormEvent<HTMLInputElement>) => this.onChangeHandler(e, "lastName")}
                            value={this.state.formControls.lastName.value}/>
                    </div>
                </div>
                <Input
                    type={this.state.formControls.description.type}
                    placeholder={this.state.formControls.description.placeholder}
                    valid={this.state.formControls.description.valid}
                    iconClassName={this.state.formControls.description.iconClassName}
                    showValidate={this.state.formControls.description.showValidate}
                    errorMessage={this.state.formControls.description.errorMessage}
                    onChange={(e: FormEvent<HTMLInputElement>) => this.onChangeHandler(e, "description")}
                    value={this.state.formControls.description.value}/>
                <div className={'action-panel'} style={{paddingTop: "15px"}}>
                    <button className={"btn btn-primary"} style={{marginRight: "15px"}}
                            onClick={this.handleCancelButton}>Cancel
                    </button>
                    <button className={"btn btn-success"}
                            onClick={e => this.handleUserDataSaveButton(e)}>Save
                    </button>
                </div>
            </div>
        );
    };

    renderUserBlock = () => {
        // eslint-disable-next-line
        const userButton = Auth.isLoggedIn && this.props.user.id == Auth.loggedUserId ? (
            <div className={'action-panel'}>
            <button type={"button"} className={"btn btn-danger"} onClick={e => this.setState({editMode: true})}>Edit</button>
        </div>) : '';

        return (
            <div>
                <div className={"user-info-block "}>
                    <div className="avatar-upload">
                        <div className="avatar-preview">
                            <img src={this.state.currentInfo.photo.url} className="avatar-preview" alt="Avatar"/>
                        </div>
                    </div>
                    <h3 className="user-name">{this.state.currentInfo.firstName} {this.state.currentInfo.lastName}</h3>
                    <p className="user-description">
                        {this.state.currentInfo.description}
                    </p>
                    {userButton}
                </div>

            </div>
        );
    };

    render() {
        if (!this.state.editMode) {
            return this.renderUserBlock();
        } else {
            return this.renderEditForm();
        }
    }
}

export default UserInfo;
