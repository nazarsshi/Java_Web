import cookies  from 'react-cookies'

export default class Auth {
    static isLoggedIn:boolean = (cookies.load('apiKey') && cookies.load('userId'));
    static loggedUserId:any = cookies.load('userId');
    static loggedApiKey:any = cookies.load('apiKey');
    static remember:boolean = cookies.load('remember') === 'true';

    static signIn = (apiKey: string, userId: string, firstName: string, remember: string) => {
        if (remember) {
            cookies.save('apiKey', apiKey, {maxAge: 20 * 24 * 60 *60});
            cookies.save('userFirstName', firstName, {maxAge: 20 * 24 * 60 *60});
            cookies.save('userId', userId, {maxAge: 20 * 24 * 60 *60});
            cookies.save('remember', remember, {maxAge: 20 * 24 * 60 *60});
        } else {
            cookies.save('apiKey', apiKey, {});
            cookies.save('userFirstName', firstName, {});
            cookies.save('userId', userId, {});
            cookies.save('remember', remember, {});
        }

        window.location.href = "/user/" + userId;
    };

    static logOut = () => {
        cookies.remove('apiKey', {path:'/'});
        cookies.remove('userFirstName', {path:'/'});
        cookies.remove('userId', {path:'/'});
        cookies.remove('remember', {path:'/'});
        window.location.href = "/login";
    }
}
