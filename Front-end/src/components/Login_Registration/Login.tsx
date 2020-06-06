import React, {Component, FormEvent} from 'react';
import photo from './signin-image.jpg';
import './LoginRegister.scss';
import Input from "../../utils/UI/Input/Input";
import Validation from "../../utils/Validation/Validation";
import axios from "axios";
import {CONFIG} from "../../config";
import Auth from "../../utils/Auth/Auth";
import {Link} from "react-router-dom";

class Login extends Component {

    state: { [id: string]: any; } = {
        formControls: {
            email: {
                type: 'email',
                placeholder: 'Email',
                value: '',
                valid: false,
                errorMessage: 'Enter valid email',
                iconClassName: 'fas fa-envelope',
                showValidate: false,
                validation: {
                    required: true,
                    email: true
                }
            },
            password: {
                type: 'password',
                placeholder: 'Password',
                value: '',
                valid: false,
                errorMessage: 'Enter valid password',
                iconClassName: 'fas fa-lock',
                showValidate: false,
                validation: {
                    required: true,
                }
            }
        },
        remember: false
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

        if (validation.email && isValid) {
            isValid = validator.checkEmail(value);
            if (!isValid) errorMessage = 'Invalid email.';
        }

        return {isValid: isValid, errorMessage: errorMessage};
    };

    onChangeHandler = (event: FormEvent<HTMLInputElement>, controlName: any) => {
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

    renderInputs = () => {
        const inputs = Object.keys(this.state.formControls).map((controlName, index) => {
            const control = this.state.formControls[controlName];
            return (
                <Input
                    key={controlName + index}
                    type={control.type}
                    placeholder={control.placeholder}
                    valid={control.valid}
                    iconClassName={control.iconClassName}
                    showValidate={control.showValidate}
                    errorMessage={control.errorMessage}
                    onChange={(e: FormEvent<HTMLInputElement>) => this.onChangeHandler(e, controlName)}
                    value={control.value}
                />
            );
        });

        return inputs;
    };

    handleSubmit = (event: FormEvent<HTMLFormElement>): void => {
        event.preventDefault();

        let isValid = true;
        const formControls = {...this.state.formControls};

        Object.keys(formControls).forEach((controlName) => {

            let control = {...formControls[controlName]};

            control.showValidate = true;
            formControls[controlName] = control;

            if (!control.valid && isValid) {
                isValid = false;
            }
        });

        if (isValid) {
            axios.post(CONFIG.apiServer + "login/", {
                email: formControls.email.value,
                password: formControls.password.value,
            }).then((res) => {
                if (res.status === 200) {
                    Auth.signIn(res.data.apiKey, res.data.id, res.data.firstName, this.state.remember);
                }
            }).catch((error) => {
                let control = formControls.email;
                control.valid = false;
                control.showValidate = true;
                control.errorMessage = 'Invalid email or password.';
                formControls.email = control;
                this.setState({
                    formControls
                });
            });
        } else {
            this.setState({
                formControls
            });
        }
    };

    render() {
        return (
            <div className="main-sign-wrapper">
                <div className="container sign-main-block">
                    <div className="row">
                        <div className=" col-md-6 col-xs-12">
                            <div className="text-center sign-form">
                                <h2 className="form-title">Login</h2>
                                <form className="register-form" id="login-form" noValidate={true}
                                      onSubmit={(event: FormEvent<HTMLFormElement>) => {this.handleSubmit(event)}}>
                                    {this.renderInputs()}
                                    <div className="form-check form-group text-left">
                                        <input type="checkbox" className="form-check-input mt-2"
                                               onChange={(event) => {this.setState({remember: event.target.checked})}}/>
                                        <label className="remember-label">Remember me</label>
                                    </div>
                                    <div className="form-group form-div form-button">
                                        <input type="submit" id="signin" className="form-submit" value="Log in"/>
                                    </div>
                                </form>
                                <span className="login-label">Not a member?</span>
                                <Link to="/registration" className="signin-image-link">Sign Up</Link>
                            </div>
                        </div>
                        <div className="signin-image col-md-6 col-xs-12">
                            <figure><img src={photo} className="image" alt="sing in"/></figure>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;
