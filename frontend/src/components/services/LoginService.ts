import $ from "jquery"
import {Md5} from 'ts-md5/dist/md5';
import VueCookies from "vue-cookies-ts"
import User from "@/components/user/User";

export default class LoginService {
    static loginService: LoginService;

    loggedInUser: User | null;

    constructor() {
        this.loggedInUser = null;
        this.isLoggedIn = this.isLoggedIn.bind(this)
        this.register = this.register.bind(this)
        this.login = this.login.bind(this)
        this.logout = this.logout.bind(this)
    }

    static getInstance(): LoginService {
        if(!LoginService.loginService) {
            LoginService.loginService = new LoginService();
        }
        return LoginService.loginService;
    }

    async login(email: string, password: string) {

        let hashedPassword = Md5.hashStr(password);
        console.log("LoginService logging in ...")
        return $.ajax({
            url: "http://localhost:9000/user/login",
            type: "POST",
            data: JSON.stringify({
                email: email,
                password: hashedPassword
            }),
            dataType: "json",
            contentType: "application/json",
            success: result => {
                console.log("success ", result);
                this.getUserWithId(result.userHash)

            },
            error: error => {
                console.log("error ", error)
            }
        });
    }

    async register (
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
    ): Promise<any> {

        let hashedPassword = Md5.hashStr(password);

        return $.ajax({
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
            success: (result) => {
                console.log("registered ", result)
                this.loggedInUser = result
            },
            error: error => {
                console.log("error ", error)
            }
        });
    }

    getUserWithId = async (id: string): Promise<void> => {
        $.ajax({
            url: "http://localhost:9000/user/find/" + id,
            type: "GET",
            success: result => {
                this.loggedInUser = result;
                console.log("got user by id ", result);
            },
            error: error => {
                console.log("error getUserWithId", error)
            }
        })
    };

    async logout() {
        this.loggedInUser = null
        console.log("logged out successfully")
    }

    isLoggedIn(): boolean {
        return this.loggedInUser != null;
    }
}