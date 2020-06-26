import $ from "jquery"
import {Md5} from 'ts-md5/dist/md5';

export default class LoginService {
    static loginService: LoginService;

    loginId: string = "";
    sessionCookie: any = "";

    constructor() {
    }

    static getInstance(): LoginService {
        if(!LoginService.loginService) {
            LoginService.loginService = new LoginService();
        }
        return LoginService.loginService;
    }

    async login(email: string, password: string) {
        let hashedPassword = Md5.hashStr(password);

        $.ajax({
            url: "http://localhost:9000/user/login",
            type: "POST",
            data: {
                email: email,
                password: hashedPassword
            },
            dataType: "application/json",
            success: result => {
                console.log("success ", result);
                this.loginId = result.id;
                this.sessionCookie = result.cookie;
            },
            error: error => {
                console.log("error ", error)
            }
        });
    }
}