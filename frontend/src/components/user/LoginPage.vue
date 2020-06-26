<template>
    <div>
        <NavigationBar/>
        <div class="contentLayout">
            <h1>Login</h1>
            <div class="form-group" :class="validateInput ? 'validate' : ''">

                <label for="email">E-Mail-Adresse</label>
                <input type="email" class="form-control" id="email" placeholder="E-Mail-Adresse" v-model="email"
                       required>
                <small v-bind:style="{ display: email ? 'none' : validateInput ? '' : 'none' }">Bitte Adresse
                    eingeben!</small>
                <br/>

                <label for="passwordVisible">Passwort</label>
                <input v-if="passwordVisible" type="text" class="form-control" id="passwordVisible"
                       placeholder="Passwort" v-model="password" required>
                <input v-else type="password" class="form-control" id="passwordNotVisible" placeholder="Passwort"
                       v-model="password" required>
                <small v-bind:style="{ display: password ? 'none' : validateInput ? '' : 'none' }">Bitte Passwort
                    eingeben!</small>
                <br/>

                <b-form-checkbox class="itemFloatLeft" id="passwordVisibility" v-model="passwordVisible">
                    <small class="form-text text-muted">Passwort anzeigen</small>
                </b-form-checkbox>

                <b-button type="submit" class="nav-item register" v-on:click="login">
                    <a class="nav-link disabled" href="#">Login</a>
                </b-button>

                <p>
                    Noch kein Mitglied?
                    <RouterLink to="/registration">Jetzt registrieren!</RouterLink>
                </p>

            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import NavigationBar from "../NavigationBar.vue";
    import {Component, Vue} from 'vue-property-decorator';
    import {Md5} from 'ts-md5/dist/md5';
    import LoginService from "@/components/LoginService";
    import $ from "jquery"

    @Component({
        components: {NavigationBar}
    })

    export default class LoginPage extends Vue {
        passwordVisible: boolean = false;
        validateInput: boolean = false;
        email: string = "";
        password: string = "";
        loginService: LoginService;
        constructor() {
            super();
            this.passwordVisible = false;
            this.validateInput = false;
            this.email = "";
            this.password = "";
            this.loginService = LoginService.getInstance();
        }

        //TODO: handle success/ error
        login(): void {
            this.validateInput = true;
            if (!this.email || !this.password) {
                return;
            }

            this.loginService.login(this.email, this.password)
                .then((result) => {

                    this.$router.push('/home')
                })
        }
    }
</script>


<style scoped>
    .contentLayout {
        display: block;
        margin-top: 15vh;
    }

    .form-group {
        width: 30vw;
        margin: auto;
    }

    label {
        float: left;
    }

    .itemFloatLeft {
        float: left;
    }

    .register {
        width: 100%;
        padding: 0;
        color: #484848;
        background: #d0f2e1;
        border-color: #d0f2e1;
        border-radius: 3px;
        margin-bottom: 30px;
        margin-top: 20px;
    }

    .register:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
        border-radius: 3px;
    }

    a:hover {
        color: #d0f2e1;
    }

    hr {
        margin: 30px 5px 10px !important;
    }

    @media (max-width: 1200px) {
        .form-group {
            width: 50vw;
        }
    }


    /* Chrome */
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    /* Firefox */
    input[type=number] {
        -moz-appearance: textfield;
    }

    /* Show green borders when valid */
    /*.validate input:valid {*/
    /*    border-color: hsl(120, 76%, 50%);*/
    /*}*/

    /* Show red borders when invalid */
    .validate input:invalid {
        border-color: red;
    }

    small {
        color: red;
        float: left
    }
</style>