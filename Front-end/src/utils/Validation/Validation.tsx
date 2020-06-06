class Validation {
    checkEmail = (email: string) : boolean => {
        // eslint-disable-next-line
        let validEmailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return validEmailRegex.test(email);
    };

    isEmpty = (str: string) : boolean => {
        return str.trim() === '';
    };

    checkMaxLength = (str: string, max: number) : boolean => {
        return str.length <= max;
    };

    checkMinLength = (str: string, min: number) : boolean => {
        return str.length >= min;
    };

    checkPasswordSymbols = (str: string) => {
        let regex = /^[\w!#$%&*+?@^]+$/;
        return regex.test(str);
    };

    checkMinCountUpperCase = (str: string, minCount: number) => {
        let regex = new RegExp('[A-Z]{' + minCount + ',}');
        return regex.test(str);
    };

    checkMinCountLowerCase = (str: string, minCount: number) => {
        let regex = new RegExp('[a-z]{' + minCount + ',}');
        return regex.test(str);
    };

    checkMinCountDigits = (str: string, minCount: number) => {
        let regex = new RegExp('\\d{' + minCount + ',}');
        return regex.test(str);
    };

    checkMinDate = (date: Date, min: Date) => {
        if (date.getFullYear() <= min.getFullYear() && date.getMonth() <= min.getMonth() && date.getDay() < min.getDay())
            return false;
        return true;
    };

    checkMinNumber = (number: number, min: number) => {
        if (number < min)
            return false;
        return true;
    };

    checkMaxNumber = (number: number, max: number) => {
        if (number > max)
            return false;
        return true;
    };
}

export default Validation;
