import $ from "jquery"
import {Md5} from 'ts-md5/dist/md5';
import VueCookies from "vue-cookies-ts"

export default class LoginService {
    static loginService: LoginService;

    loginId: string;

    constructor() {
        this.loginId = ""
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
            },
            error: error => {
                console.log("error ", error)
            }
        });
    }

    async register(
        firstName: string,
        lastName: string,
        lastNameVisible: boolean,
        country: string,
        plz: string,
        city: string,
        street: string,
        streetVisible: boolean,
        picture: any,
        email: string,
        password: string
    ) {

        let hashedPassword = Md5.hashStr(password);

        $.ajax({
            url: "http://localhost:9000/user/create",
            type: "POST",
            data: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                lastNameVisible: lastNameVisible,
                country: country,
                plz: plz,
                city: city,
                street: street,
                streetVisible: streetVisible,
                picture: null,
                email: email,
                password: hashedPassword,
            }),
            dataType: "json",
            contentType: "application/json",
            success: result => {
                this.loginId = result.id;
            },
            error: error => {
                console.log("error ", error)
            }
        });
    }
}