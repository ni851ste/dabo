<template>
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
                                       v-model="firstName"
                                       required>
                                <small v-bind:style="{ display: firstName ? 'none' : validateInput ? '' : 'none' }">
                                    Bitte Vornamen eingeben!</small>
                                <br/>

                                <label for="surname">Nachname</label>
                                <input type="text" class="form-control" id="surname" placeholder="Nachname"
                                       v-model="lastName"
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
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" v-on:click="updateUser" data-dismiss="modal">Save changes</button>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import BaseImageInput from "@/components/BaseImageInput.vue";
    import User from "@/components/user/User";
    import Address from "@/components/user/Address";
    import $ from "jquery";

    @Component
    ({
        components: {BaseImageInput, NavigationBar},
    })

    export default class EditMyAccountView extends Vue {
        @Prop() private user!: User;

        validateInput: boolean = false;

        firstName: string;
        lastName: string;
        lastNameVisible: boolean;
        street: string;
        plz: string;
        city: string;
        country: string;
        streetVisible: boolean;

        constructor() {
            super();
            this.firstName = this.user.firstName;
            this.lastName = this.user.lastName;
            this.lastNameVisible = this.user.lastNameVisible;
            this.country = this.user.address.country;
            this.plz = this.user.address.plz;
            this.city = this.user.address.city;
            this.street = this.user.address.street;
            this.country = this.user.address.country;
            this.streetVisible = this.user.address.streetVisible;
        }

        updateUser(): void {
            let user: User | null = null;

            this.validateInput = true;

            if (!this.firstName || !this.lastName || !this.country || !this.plz ||
                !this.street || !this.city) {
                return;
            }

            $.ajax({
                url: "http://localhost:9000/user/update/" + this.user.id,
                type: "POST",
                data: JSON.stringify({
                    email: this.user.email,
                    password: this.user.password,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    lastNameVisible: this.lastNameVisible,
                    ratings: this.user.ratings,
                    picture: this.user.picture,
                    insertedArticlesId: this.user.insertedArticlesId,
                    borrowedArticlesId: this.user.borrowedArticlesId,
                    pinnedArticledId: this.user.pinnedArticledId,
                    street: this.street,
                    city: this.city,
                    plz: this.plz,
                    country: this.country,
                    streetVisible: this.streetVisible,
                }),
                dataType: "json",
                contentType: "application/json",
                success: (result) => {
                    user = result;
                    console.log("success ", result)
                    this.$router.push({name: 'refresh'}).then(() => {
                        this.$router.push({name: 'user', params: {user: user}});
                    });
                },
                error: error => {
                    console.log("error ", error)
                }
            });
        }
    }

</script>

<style scoped>
    .contentLayout {
        border: #d0f2e1 solid 2px;
        width: 100%;
        margin: 15px auto;
        border-radius: 3px;
        padding: 20px;
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