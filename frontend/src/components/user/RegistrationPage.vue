<template>
    <div>
        <NavigationBar/>
        <div class="contentLayout">
            <h1>Registrierung</h1>
            <div class="form-group" :class="validateInput ? 'validate' : ''">

                <label for="firstname">Vorname</label>
                <input type="text" class="form-control" id="firstname" placeholder="Vorname" v-model="firstName" required

                >

                <label for="surname">Nachname</label>
                <input type="text" class="form-control" id="surname" placeholder="Nachname" v-model="surName" required>

                <hr/>

                <label for="country">Land</label>
                <input type="text" class="form-control" id="country" placeholder="Land" v-model="country" required>

                <div class="plzOrt">
                    <div class="plz">
                        <label for="plz">PLZ</label>
                        <input type="number" class="form-control" id="plz" placeholder="PLZ" v-model="plz" required>
                    </div>
                    <div class="city">
                        <label for="city">Ort</label>
                        <input type="text" class="form-control" id="city" placeholder="Ort" v-model="city" required>
                    </div>
                </div>

                <label for="street">Straße</label>
                <input type="text" class="form-control" id="street" placeholder="Straße" v-model="street" required>

                <hr/>

                <label for="email">E-Mail-Adresse</label>
                <input type="email" class="form-control" id="email" placeholder="E-Mail-Adresse" v-model="email" required>

                <label for="passwordVisible">Passwort</label>
                <input v-if="passwordVisible" type="text" class="form-control" id="passwordVisible" placeholder="Passwort" v-model="password" required>
                <input v-else type="password" class="form-control" id="passwordNotVisible" placeholder="Passwort" v-model="password" required>
                <b-form-checkbox class="itemFloatLeft" id="passwordVisibility" v-model="passwordVisible">
                    <small class="form-text text-muted">Passwort anzeigen</small>
                </b-form-checkbox>

                <b-button class="nav-item register" v-on:click="register">
                    <a class="nav-link disabled" href="#">Registrieren</a>
                </b-button>

                <!--                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>-->
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";

    @Component({
        components: {NavigationBar}
    })
    export default class RegistrationPage extends Vue {
        passwordVisible: boolean = false;
        validateInput: boolean = false;
        firstName: string = "";
        surName: string = "";
        country: string = "";
        plz: string = "";
        city: string = "";
        street: string = "";
        email: string = "";
        password: string = "";

        register(): void {
            this.validateInput = true;

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

    .form-group > label {
        margin-top: 20px;
    }

    label {
        float: left;
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

    hr{
        margin: 30px 5px 10px !important;
    }

    @media (max-width: 1200px) {
        .form-group {
            width: 50vw;
        }

        .plz {
            width: 20vw;
        }

        .ort {
            width: 39vw;
            margin-left: 1vw;
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
    .validate input:valid {
        border-color: hsl(120, 76%, 50%);
    }

    /* Show red borders when invalid */
    .validate input:invalid {
        border-color: red;
    }
</style>