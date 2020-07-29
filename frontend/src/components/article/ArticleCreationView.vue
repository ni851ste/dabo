<template>
    <div>
        <NavigationBar></NavigationBar>
        <div class="contentLayout my-buttons">
            <div class="borderbox b-container b-form-checkbox-group">
                <h1> Allgemein</h1>

                <div class="form-group" :class="validateInput ? 'validate' : ''">
                    <label for="articleNameInput">Artikelbezeichnung: *</label>
                    <input type="text" class="form-control" id="articleNameInput" v-model="articleName" required>
                    <invalid-small v-bind:style="{ display: articleName ? 'none' : validateInput ? '' : 'none' }">
                        Bitte Artikelbezeichnung eingeben!
                    </invalid-small>
                    <br>
                </div>

                <div class="form-group">
                    <label>Artikelbeschreibung:</label>
                    <label for="articleDescription"></label>
                    <textarea class="form-control" id="articleDescription" v-on:keyup="countdown"
                              v-model="articleDescription" required></textarea>
                    <p class='text-right text-small' v-bind:class="{'text-danger': hasError }">{{charCount + '/' +
                        maxCount}}</p>
                </div>

                <div class="form-group" :class="validateInput && (this.selectedCategories.length === 0) ? 'validate' : ''">
                    <label>Zugehörigkeit: *</label>
                    <b-form-group>
                        <b-form-checkbox-group
                                v-on:click=""
                                id="category-checkbox-group"
                                v-model="selectedCategories"
                                :key="uuid"
                                :options="allCategories"
                        >
                        </b-form-checkbox-group>
                    </b-form-group>
                    <invalid-small v-bind:style="{ display: this.selectedCategories.length !== 0 ? 'none' : validateInput ? '' : 'none' }">
                        Bitte Kategorie auswählen!
                    </invalid-small>
                </div>
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

                            <div class="datepicker">
                                <b-form-datepicker
                                        v-model="minDate" :min="today" locale="de">
                                </b-form-datepicker>
                            </div>
                        </b-col>
                        <b-col>
                            <div class="datepicker">
                                <b-form-datepicker
                                        id="toDatepicker" v-model="maxDate" :min="minDate" locale="de"
                                        placeholder="bis..." :disabled="checked == 1">

                                </b-form-datepicker>
                                <br>
                                <b-form-checkbox v-model="checked" :options="checkOptions">Ohne Begrenzung
                                </b-form-checkbox>
                            </div>
                        </b-col>
                    </b-row>
                </b-container>
            </div>

            <div class="borderbox">
                <h1>Standort</h1>
                <div class="form-group" :class="validateInput ? 'validate' : ''">
                    <label for="countryInput">Land: *</label>
                    <input type="text" class="form-control" id="countryInput" v-model="country" required>
                    <invalid-small v-bind:style="{ display: country ? 'none' : validateInput ? '' : 'none' }">
                        Bitte Land eingeben!
                    </invalid-small>
                </div>
                <br/>

                <b-row>
                    <b-col md="3">
                        <div class="form-group" :class="validateInput ? 'validate' : ''">
                            <label for="plzInput">PLZ: *</label>
                            <input type="text" class="form-control" id="plzInput" v-model="plz" required>
                            <invalid-small v-bind:style="{ display: plz ? 'none' : validateInput ? '' : 'none' }">
                                Bitte PLZ eingeben!
                            </invalid-small>
                        </div>
                        <br/>
                    </b-col>
                    <b-col>
                        <div class="form-group" :class="validateInput ? 'validate' : ''">
                            <label for="cityInput">Stadt: *</label>
                            <input type="text" class="form-control" id="cityInput" v-model="city" required>
                            <invalid-small v-bind:style="{ display: city ? 'none' : validateInput ? '' : 'none' }">
                                Bitte Stadt eingeben!
                            </invalid-small>
                        </div>
                        <br/>
                    </b-col>
                </b-row>
            </div>

            <b-button class="addButton" v-on:click="createArticle">
                <a class="nav-link disabled" href="#">Artikel hinzufügen</a>
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
    import Article from "@/components/article/Article";
    import * as _ from "lodash"

    @Component({
        components: {NavigationBar, BaseImageInput}
    })

    export default class ArticleCreationView extends Vue {
        articleName: string = "";
        articleDescription: string = "";
        selectedCategories: Category[] = [];
        image1 = '';
        image2 = '';
        image3 = '';
        image4 = '';
        image5 = '';
        minDate: Date = new Date();
        maxDate: any = null;
        country: string = "";
        plz: string = "";
        city: string = "";
        validateInput: boolean = false;

        /*Article Description Count*/
        maxCount: number = 200;
        hasError: boolean = false;
        charCount: number = 0

        countdown() {
            this.charCount = this.articleDescription.length;
            this.hasError = this.articleDescription.length > this.maxCount;
        }

        allCategories: Category[] = this.getCategories();

        getCategories(): Category[] {
            return Object.values(Category)
        }

        /*Availabilty*/
        today = new Date(moment().format("YYYY-MM-DD"));
        checked: number = 0;
        checkOptions = [{value: 0}, {value: 1}]

        /*Create Article*/
        createArticle(): void {
            let article: Article;

            this.validateInput = true;
            let image: any = null;
            let insertionDate: Date = new Date();

            if (!this.articleName || _.isEmpty(this.selectedCategories) || !this.country || !this.plz || !this.city) {
                return;
            }

            $.ajax({
                url: "http://localhost:9000/users/articles/create",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify ({
                    name: this.articleName,
                    description: this.articleDescription,
                    images: image,
                    fromDate: this.minDate,
                    toDate: this.maxDate,
                    country: this.country,
                    plz: this.plz,
                    location: this.city,
                    insertionDate: insertionDate,
                    categories: this.selectedCategories,
                    userId: 1662771985
                }),
                dataType: "json",

                success: result => {
                    console.log("success ", result);
                    article = new Article(result.name,
                        result.description,
                        result.image,
                        result.location,
                        new Date(result.insertionDate),
                        result.category,
                        []
                        );
                    //code is working, IntelliJ is just fooling around
                    this.$router.push({name: 'articlePage', params: {article: article, showAlert: true}});

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

    .validate input:invalid {
        border-color: red;
    }

    .validate .custom-control-label::before {
        border: red solid 1px;
    }

    invalid-small {
        color: red;
        float: left
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
