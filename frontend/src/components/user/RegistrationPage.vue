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
                            <small class="form-text text-muted">Nachname für andere sichtbar</small>
                        </b-form-checkbox>
                    </div>
                    <div class="image">
                        <BaseImageInput class="imgProfile" id="profileImg" v-model="image"/>
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
                    <small class="form-text text-muted">Straße für andere sichtbar</small>
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
        image: File | string | undefined;

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

            if (typeof this.image === "undefined") {
                this.image = "data:image/jpeg;base64,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mpqampqampqampqampqampqan+X/AP/Z"
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
                this.image,
                this.email,
                this.password
            )
                .then((result: any) => {
                    //TODO: backend support for sessionCookie
                    this.$cookies.set("sessionCookie", result, {
                        // secure: true
                    });
                    this.$router.push({name: 'user', params: {user: result}});
                })
                .catch((error: any) => {
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