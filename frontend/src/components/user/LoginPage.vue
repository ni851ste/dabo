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
            <b-alert v-model="showAlert" class="alert" variant="danger" dismissible>Login fehlgeschlagen.</b-alert>
            <b-alert v-model="this.showLoginFirstAlert" class="alert" variant="warning" dismissible>Sie müssen sich erst einloggen.</b-alert>
        </div>
    </div>
</template>

<script lang="ts">
    import NavigationBar from "../NavigationBar.vue";
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import LoginService from "@/components/services/LoginService";

    @Component({
        components: {NavigationBar}
    })

    export default class LoginPage extends Vue {
        @Prop() private showLoginFirstAlert!: boolean;

        passwordVisible: boolean;
        validateInput: boolean;
        email: string;
        password: string;
        loginService: LoginService;

        showAlert: boolean;

        constructor() {
            super();
            this.passwordVisible = false;
            this.validateInput = false;
            this.email = "";
            this.password = "";
            this.loginService = LoginService.getInstance();
            this.showAlert = false;
        }

        async login(): Promise<void> {
            this.validateInput = true;
            if (!this.email || !this.password) {
                return;
            }
            await this.loginService.login(this.email, this.password)
                .then((result: any) => {
                    this.$cookies.set("sessionCookie", result.userHash);
                    this.$router.push({name: 'home', params: {showAlert: true}});
                })
                .catch((error: any) => {
                    console.log("error LoginPage", error)
                    this.showAlert = true;
                });
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

        .alert {
            width: 50vw!important;
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

    .alert {
        margin: auto;
        text-align: left;
        width: 30vw;
        margin-top: 20px;
    }
</style>