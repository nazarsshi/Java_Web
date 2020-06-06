import React from 'react';
import './Input.scss';

function isInvalid(props: any) {

    if (props.showValidate && !props.valid) {
        return true;
    }
    return false;
}

export default (props: { [id: string]: any;}) => {
    const inputType = props.type || 'text';
    const htmlFor = `${inputType}-${Math.random()}`;
    const cls = ['sign-form-input','form-control'];

    if (isInvalid(props)) {
        cls.push('error');
    }

    return (
        <div className="form-group">
            <div className="form-div">
                <label className="label-icon" htmlFor={htmlFor}>
                    <i className={props.iconClassName}/>
                </label>
                <input type={inputType}
                       className={cls.join(' ')}
                       id={htmlFor}
                       placeholder={props.placeholder}
                       onChange={props.onChange}
                       value={props.value || ''}/>

            </div>
            <p className="sign-form-error-text">
                {isInvalid(props) ? props.errorMessage : '' }
            </p>
        </div>
    );
}