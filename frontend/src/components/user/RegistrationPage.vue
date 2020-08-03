<template>
    <div>
        <NavigationBar/>
        <div class="contentLayout">
            <h1>Registrierung</h1>
            <div class="form-group" :class="validateInput ? 'validate' : ''">
                <div>
                    <div class="names">

                        <label for="firstname">Vorname</label>
                        <input type="text" class="form-control" id="firstname" placeholder="Vorname" v-model="firstName"
                               required>
                        <small v-bind:style="{ display: firstName ? 'none' : validateInput ? '' : 'none' }">
                            Bitte Vornamen eingeben!</small>
                        <br/>

                        <label for="lastname">Nachname</label>
                        <input type="text" class="form-control" id="lastname" placeholder="Nachname" v-model="lastName"
                               required>
                        <small v-bind:style="{ display: lastName ? 'none' : validateInput ? '' : 'none' }">
                            Bitte Nachnamen eingeben!</small>
                        <br/>
                        <b-form-checkbox class="itemFloatLeft" v-model="lastNameVisible">
                            <small class="form-text text-muted">Nachname im Profil anzeigen</small>
                        </b-form-checkbox>
                    </div>
                    <div class="image">
                        <BaseImageInput class="imgProfile" id="profileImg"/>
                        <small class="form-text text-muted center">Profilbild hochladen</small>
                    </div>
                </div>
                <!--                <br/>-->

                <hr/>

                <label for="country">Land</label>
                <input type="text" class="form-control" id="country" placeholder="Land" v-model="country" required>
                <small v-bind:style="{ display: country ? 'none' : validateInput ? '' : 'none' }">
                    Bitte Land eingeben!</small>

                <div class="plzOrt">
                    <div class="plz">
                        <label for="plz">PLZ</label>
                        <input type="number" class="form-control" id="plz" placeholder="PLZ" v-model="plz" required>
                        <small v-bind:style="{ display: plz ? 'none' : validateInput ? '' : 'none' }">
                            Bitte PLZ eingeben!</small>
                    </div>
                    <div class="city">
                        <label for="city">Ort</label>
                        <input type="text" class="form-control" id="city" placeholder="Ort" v-model="city" required>
                        <small v-bind:style="{ display: city ? 'none' : validateInput ? '' : 'none' }">
                            Bitte Ort eingeben!</small>
                        <br/>
                    </div>
                </div>

                <label for="street">Straße</label>
                <input type="text" class="form-control" id="street" placeholder="Straße" v-model="street" required>
                <small v-bind:style="{ display: street ? 'none' : validateInput ? '' : 'none' }">
                    Bitte Straße eingeben!</small>
                <br/>
                <b-form-checkbox class="itemFloatLeft" v-model="streetVisible">
                    <small class="form-text text-muted">Straße im Profil anzeigen</small>
                </b-form-checkbox>
                <br/>

                <hr/>

                <label for="email">E-Mail-Adresse</label>
                <input type="email" class="form-control" id="email" placeholder="E-Mail-Adresse" v-model="email"
                       required>
                <small v-bind:style="{ display: email ? 'none' : validateInput ? '' : 'none' }">
                    Bitte Adresse eingeben!</small>
                <br/>

                <label for="passwordVisible">Passwort</label>
                <input v-if="passwordVisible" type="text" class="form-control" id="passwordVisible"
                       placeholder="Passwort" v-model="password" required>
                <input v-else type="password" class="form-control" id="passwordNotVisible" placeholder="Passwort"
                       v-model="password" required>
                <small v-bind:style="{ display: password ? 'none' : validateInput ? '' : 'none' }">
                    Bitte Passwort eingeben!</small>
                <br/>

                <b-form-checkbox class="itemFloatLeft" id="passwordVisibility" v-model="passwordVisible">
                    <small class="form-text text-muted">Passwort anzeigen</small>
                </b-form-checkbox>

                <b-button type="submit" class="nav-item register" v-on:click="register">
                    <a class="nav-link disabled" href="#">Registrieren</a>
                </b-button>
            </div>
        </div>
                <b-alert v-model="showLoginFailedAlert" variant="danger" dismissible class="dangerAlert">
                    Fehler bei der Registrierung!
                </b-alert>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import $ from "jquery";
    import BaseImageInput from "@/components/BaseImageInput.vue";
    import Article from "@/components/article/Article";
    import {Md5} from 'ts-md5/dist/md5';
    import LoginService from "@/components/services/LoginService";

    @Component
    ({
        components: {BaseImageInput, NavigationBar}
    })
    export default class RegistrationPage extends Vue {
        validateInput: boolean = false;
        passwordVisible: boolean = false;
        showLoginFailedAlert: boolean;

        firstName: string;
        lastName: string;
        lastNameVisible: boolean;
        country: string;
        plz: string;
        city: string;
        street: string;
        streetVisible: boolean;
        email: string;
        password: string;

        loginService: LoginService;

        constructor() {
            super();
            this.passwordVisible = false;
            this.validateInput = false;
            this.showLoginFailedAlert = false;

            this.firstName = "";
            this.lastName = "";
            this.lastNameVisible = true;
            this.country = "";
            this.plz = "";
            this.city = "";
            this.street = "";
            this.streetVisible = true;
            this.email = "";
            this.password = "";

            this.loginService = LoginService.getInstance();
        }

        async register(): Promise<void> {
            this.validateInput = true;

            if (!this.firstName || !this.lastName || !this.country || !this.plz ||
                !this.street || !this.city || !this.email || !this.password) {
                return;
            }

            await this.loginService.register(
                this.firstName,
                this.lastName,
                this.lastNameVisible,
                this.country,
                this.plz,
                this.city,
                this.street,
                this.streetVisible,
                null,
                this.email,
                this.password
            )
                .then((result) => {
                    console.log(result)
                    console.log(result.id)
                    //TODO: backend support for sessionCookie
                    this.$cookies.set("sessionCookie", result.id, {
                        // secure: true
                    });

                    this.$router.push('/home')
                })
                .catch((error) => {
                    this.showLoginFailedAlert = true;
                });

        }

        isStatusFailure(status: number): boolean {
            console.log("status ", status)
            return status < 200 || status > 300

        }
    }
</script>

<style scoped>
    .contentLayout {
        display: block;
    }

    .form-group {
        width: 30vw;
        margin: auto;
    }

    .form-control {
        border: 1px solid black;
    }

    label {
        float: left;
        color: black;
    }

    .itemFloatLeft {
        float: left;
    }

    .plzOrt {
        display: flex;
        margin-top: 20px;
    }

    .plz {
        width: 10vw;
        float: left;
    }

    .city {
        width: 19vw;
        margin-left: 1vw;
    }

    .names {
        float: left;
        width: 17vw;
        display: inline;
    }

    .image {
        margin-left: 20vw;
        padding-top: 20px;
    }

    .center {
        float: none;
        padding-top: 20px;
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

    .base-image-input {
        height: 150px;
        width: 150px;
        border-radius: 150px;
        padding: 21px;
        margin: auto;
    }

    hr {
        margin: 30px 5px 30px !important;
        color: #484848;
    }

    @media (max-width: 1200px) {
        .form-group {
            width: 50vw;
        }

        .plz {
            width: 20vw;
        }

        .city {
            width: 39vw;
        }

        .names {
            width: 30vw;
        }

        .image {
            padding-top: 20px;
            margin-left: 33vw;
            width: 150px;
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

    /* Show red borders when invalid */
    .validate input:invalid {
        border-color: red;
    }

    small {
        color: red;
        float: left
    }

    .dangerAlert {
        right: 20px !important;
        position: fixed;
        top: 80px !important;
    }

</style>