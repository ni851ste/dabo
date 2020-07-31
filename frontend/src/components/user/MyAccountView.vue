<template>
    <div>
        <NavigationBar/>

        <button type="button" class="btn btn-primary tmpButton" data-toggle="modal" data-target="#editArticleModal">
            Launch demo modal
        </button>

        <div class="modal fade" id="editArticleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editArticleLabel">Account bearbeiten</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="contentLayout">
                            <div class="form-group" :class="validateInput ? 'validate' : ''">
                                <div>
                                    <div class="names">
                                        <label for="firstname">Vorname</label>
                                        <input type="text" class="form-control" id="firstname" placeholder="Vorname"
                                               value="test"
                                               v-model="firstName"
                                               required>
                                        <small v-bind:style="{ display: firstName ? 'none' : validateInput ? '' : 'none' }">
                                            Bitte Vornamen eingeben!</small>
                                        <br/>

                                        <label for="surname">Nachname</label>
                                        <input type="text" class="form-control" id="surname" placeholder="Nachname"
                                               v-model="surName"
                                               required>
                                        <small v-bind:style="{ display: surName ? 'none' : validateInput ? '' : 'none' }">
                                            Bitte Nachnamen eingeben!</small>
                                        <br/>
                                        <b-form-checkbox class="itemFloatLeft" v-model="surNameVisible">
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
                                <input type="text" class="form-control" id="country" placeholder="Land"
                                       v-model="country" required>
                                <small v-bind:style="{ display: country ? 'none' : validateInput ? '' : 'none' }">
                                    Bitte Land eingeben!</small>

                                <div class="plzOrt">
                                    <div class="plz">
                                        <label for="plz">PLZ</label>
                                        <input type="number" class="form-control" id="plz" placeholder="PLZ"
                                               v-model="plz" required>
                                        <small v-bind:style="{ display: plz ? 'none' : validateInput ? '' : 'none' }">
                                            Bitte PLZ eingeben!</small>
                                    </div>
                                    <div class="city">
                                        <label for="city">Ort</label>
                                        <input type="text" class="form-control" id="city" placeholder="Ort"
                                               v-model="city" required>
                                        <small v-bind:style="{ display: city ? 'none' : validateInput ? '' : 'none' }">
                                            Bitte Ort eingeben!</small>
                                        <br/>
                                    </div>
                                </div>

                                <label for="street">Straße</label>
                                <input type="text" class="form-control" id="street" placeholder="Straße"
                                       v-model="street" required>
                                <small v-bind:style="{ display: street ? 'none' : validateInput ? '' : 'none' }">
                                    Bitte Straße eingeben!</small>
                                <br/>
                                <b-form-checkbox class="itemFloatLeft" v-model="streetVisible">
                                    <small class="form-text text-muted">Straße im Profil anzeigen</small>
                                </b-form-checkbox>
                                <br/>

                                <!--                <hr/>-->

                                <!--                <label for="email">E-Mail-Adresse</label>-->
                                <!--                <input type="email" class="form-control" id="email" placeholder="E-Mail-Adresse" v-model="email"-->
                                <!--                       required>-->
                                <!--                <small v-bind:style="{ display: email ? 'none' : validateInput ? '' : 'none' }">-->
                                <!--                    Bitte Adresse eingeben!</small>-->
                                <!--                <br/>-->

                                <!--                <label for="passwordVisible">Passwort</label>-->
                                <!--                <input v-if="passwordVisible" type="text" class="form-control" id="passwordVisible"-->
                                <!--                       placeholder="Passwort" v-model="password" required>-->
                                <!--                <input v-else type="password" class="form-control" id="passwordNotVisible" placeholder="Passwort"-->
                                <!--                       v-model="password" required>-->
                                <!--                <small v-bind:style="{ display: password ? 'none' : validateInput ? '' : 'none' }">-->
                                <!--                    Bitte Passwort eingeben!</small>-->
                                <!--                <br/>-->

                                <!--                <b-form-checkbox class="itemFloatLeft" id="passwordVisibility" v-model="passwordVisible">-->
                                <!--                    <small class="form-text text-muted">Passwort anzeigen</small>-->
                                <!--                </b-form-checkbox>-->

                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>


    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import $ from "jquery";
    import BaseImageInput from "@/components/BaseImageInput.vue";
    import User from "@/components/user/User";
    import Address from "@/components/user/Address";
    import Rating from "@/components/rating/Rating";

    @Component
    ({
        components: {BaseImageInput, NavigationBar},
    })
    export default class RegistrationPage extends Vue {
        user: User = new User(1, "a.b@c.de", "ASDFG", "a", "b", "",
            true, new Address("Musterstraße", "12345", "Musterstadt", "Deutschland",
                true), [], [], [], [], []);

        passwordVisible: boolean = false;
        validateInput: boolean = false;
        firstName: string = "";
        surName: string = "";
        surNameVisible: boolean = true;
        country: string = "";
        plz: string = "";
        city: string = "";
        street: string = "";
        streetVisible: boolean = true;
        email: string = "";
        password: string = "";

    }
</script>

<style scoped>
    .contentLayout {
        border: #d0f2e1 solid 2px;
        width: 100%;
        margin-top: 15px;
        margin-left: auto;
        margin-right: auto;
        border-radius: 3px;
        padding: 20px;
    }

    .tmpButton {
        margin-top: 30px;
    }

    .dialog {
        width: 50vw;
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

    .imgProfile:hover {
        background: #E0E0E0;
        z-index: 10;
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

    .btn-primary {
        background-color: #d0f2e1;
        border-color: #d0f2e1;
        color: #484848;
    }

    .btn btn-secondary {
        background-color: #484848;
        border-color: #484848;
        color: white;
    }

</style>