<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <div class="contentLayout my-buttons">
            <div class="borderbox b-container b-form-checkbox-group">
                <h1> Allgemein</h1>

                <div class="form-group validation">
                    <label for="articleNameInput">Artikelbezeichnung: *</label>
                    <input type="text" class="form-control" id="articleNameInput" required>
                    <div class="invalid-feedback"></div>
                </div>

                <div>
                    <label>Artikelbeschreibung:</label>
                    <textarea class="form-control" id="articleDescription" v-on:keyup="countdown" v-model="message"
                              required></textarea>
                    <p class='text-right text-small' v-bind:class="{'text-danger': hasError }">{{charCount + '/' +
                        maxCount}}</p>
                </div>

                <p>Zugehörigkeit: *</p>
                <b-form-group>
                    <b-form-checkbox-group
                            v-on:click=""
                            id="category-checkbox-group"
                            v-model="selectedCategories"
                            :key="uuid"
                            :options="categories"
                    >
                    </b-form-checkbox-group>
                </b-form-group>
            </div>

            <div class="borderbox">
                <h1>Bilder</h1>
                <div class="b-container">
                    <b-row>
                        <b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="image1"/>
                            </div>
                        </b-col>
                        <b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="image2"/>
                            </div>
                        </b-col>
                        <b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="image3"/>
                            </div>
                        </b-col>
                        <b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="image4"/>
                            </div>
                        </b-col>
                        <b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="image5"/>
                            </div>
                        </b-col>
                    </b-row>
                </div>
            </div>

            <div class="borderbox">
                <h1>Verfügbarkeit</h1>
                <b-container>
                    <b-row>
                        <b-col>

                            <div>
                                <b-form-datepicker
                                        v-model="minDate" :min="today" locale="de">
                                </b-form-datepicker>
                            </div>
                        </b-col>
                        <b-col>
                            <div>
                                <b-form-datepicker
                                        id = "toDatepicker" v-model="value" :min="minDate" locale="de" placeholder="bis...">
                                </b-form-datepicker>
                                <br>
                                    <b-form-checkbox onclick="setDisabled()">Ohne Begrenzung</b-form-checkbox>
                            </div>
                        </b-col>
                    </b-row>
                </b-container>
            </div>

            <div class="borderbox">
                <h1>Standort</h1>
                <div class="form-group validation">
                    <label for="countryInput">Land: *</label>
                    <input type="text" class="form-control" id="countryInput" required>

                </div>

                <b-row>
                    <b-col md="3">
                        <div class="form-group validation">
                            <label for="plzInput">PLZ: *</label>
                            <input type="text" class="form-control" id="plzInput" required>
                        </div>
                    </b-col>
                    <b-col>
                        <div class="form-group validation">
                            <label for="cityInput">Stadt: *</label>
                            <input type="text" class="form-control" id="cityInput" required>
                        </div>
                    </b-col>
                </b-row>
            </div>

            <b-button class="addButton" v-on:click="createArticle">
                <a class="nav-link disabled" href="#">
                    Artikel hinzufügen
                </a>
            </b-button>
        </div>
    </div>
</template>

<script lang="ts">
    import NavigationBar from "@/components/NavigationBar.vue";
    import BaseImageInput from "@/components/BaseImageInput.vue";
    import Category from "@/components/category/Category";
    import {Component, Vue} from 'vue-property-decorator';
    import moment from "moment"
    import $ from "jquery";

    @Component({
        components: {NavigationBar, BaseImageInput}
    })

    export default class ArticleCreationView extends Vue {
        maxCount: number = 200;
        message: string = '';
        hasError: boolean = false;
        charCount: number = 0

        countdown() {
            this.charCount = this.message.length;
            this.hasError = this.message.length > this.maxCount;
        }

        categories: Category[] = this.getCategories();

        getCategories(): Category[] {
            return Object.values(Category)
        }
        selectedCategories: Category[] = [];

        today = new Date(moment().format("YYYY-MM-DD"));
        minDate: Date = new Date();

        disabledState = false

        setDisabled() {
            if(!this.disabledState)
                this.disabledState = true
            else
                this.disabledState = false
            $(".toDatepicker").prop("disabled", this.disabledState)
        }

        createArticle(): void {
            let name: string = (<HTMLInputElement>document.getElementById("articleNameInput")).value;
            let description: string = (<HTMLInputElement>document.getElementById("articleDescriptionInput"))!.value;
            let image: any = null;
            let location: string = (<HTMLInputElement>document.getElementById("cityInput"))!.value;
            let insertionDate: Date = new Date();
            // let article: Article = new Article(name, description, image, location, insertionDate)

            $.ajax({
                url: "http://localhost:9000/users/articles/create",
                type: "POST",
                data: {
                    name: name,
                    description: description,
                    image: image,
                    location: location,
                    insertionDate: insertionDate
                },
                dataType: "application/json",
                success: result => {
                    console.log("success ", result)
                },
                error: error => {
                    console.log("error ", error)
                }
            });
        }
    }
</script>

<style>
    h1 {
        font-size: x-large;
        font-weight: bold;
    }

    .borderbox {
        border: #d0f2e1 solid 2px;
        width: 100%;
        margin-top: 15px;
        margin-left: auto;
        margin-right: auto;
        border-radius: 3px;
        padding: 20px;
        text-align: left;
    }

    .b-container {
        align-content: start;
        alignment: left;
    }

    :focus {
        outline: none !important;
    }

    .custom-control-input:checked ~ .custom-control-label::after {
        background-color: #d0f2e1;
        border-radius: 3px;
    }

    .validation input:required:focus:valid {
        border: green solid 1px;
    }

    .validation input:focus:invalid {
        border: red solid 1px;
    }

    .availableCheck {
        margin-left: 1vw;
    }

    .addButton {
        background-color: #d0f2e1;
        padding: 0;
        margin-top: 15px;
        margin-bottom: 15px;
        border: none;
        width: 100%;
    }

    .addButton:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
        border-radius: 3px;
    }

    textarea.form-control {
        height: 100px;
        resize: none;
    }

    .text-small {
        font-size: 0.9em;
    }
</style>