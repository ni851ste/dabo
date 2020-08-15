<template>
    <div>
        <div class="modal fade" id="editArticleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editArticleLabel">Artikel bearbeiten</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <div class="borderbox b-container b-form-checkbox-group">
                            <h1> Allgemein</h1>

                            <div class="form-group" :class="validateInput ? 'validate' : ''">
                                <label for="articleNameInput">Artikelbezeichnung: *</label>
                                <input type="text" class="form-control" id="articleNameInput" v-model="articleName"
                                       required>
                                <invalid-small
                                        v-bind:style="{ display: articleName ? 'none' : validateInput ? '' : 'none' }">
                                    Bitte Artikelbezeichnung eingeben!
                                </invalid-small>
                                <br>
                            </div>

                            <div class="form-group">
                                <label>Artikelbeschreibung:</label>
                                <label for="articleDescription"></label>
                                <textarea class="form-control" id="articleDescription" v-on:keyup="countdown"
                                          v-model="articleDescription" required></textarea>
                                <p class='text-right text-small' v-bind:class="{'text-danger': hasError }">{{charCount +
                                    '/' +
                                    maxCount}}</p>
                            </div>

                            <div class="form-group" :class="validateInput ? 'validate' : ''">
                                <label>Zugehörigkeit: *</label>
                                <b-form-group :class="validateInput ? 'validate' : ''">
                                    <b-form-checkbox-group
                                            v-on:click=""
                                            id="category-checkbox-group"
                                            v-model="selectedCategories"
                                            :key="uuid"
                                            :options="categories"
                                    >
                                    </b-form-checkbox-group>
                                </b-form-group>
                                <invalid-small
                                        v-bind:style="{ display: this.selectedCategories.length !== 0 ? 'none' : validateInput ? '' : 'none' }">
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
                            </div>
                            <invalid-small v-bind:style="{ display: country ? 'none' : validateInput ? '' : 'none' }">
                                Bitte Land eingeben!
                            </invalid-small>
                            <br/>

                            <b-row>
                                <b-col md="3">
                                    <div class="form-group" :class="validateInput ? 'validate' : ''">
                                        <label for="plzInput">PLZ: *</label>
                                        <input type="text" class="form-control" id="plzInput" v-model="plz" required>
                                    </div>
                                    <invalid-small
                                            v-bind:style="{ display: plz ? 'none' : validateInput ? '' : 'none' }">
                                        Bitte PLZ eingeben!
                                    </invalid-small>
                                    <br/>
                                </b-col>
                                <b-col>
                                    <div class="form-group" :class="validateInput ? 'validate' : ''">
                                        <label for="cityInput">Stadt: *</label>
                                        <input type="text" class="form-control" id="cityInput" v-model="city" required>
                                    </div>
                                    <invalid-small
                                            v-bind:style="{ display: city ? 'none' : validateInput ? '' : 'none' }">
                                        Bitte Stadt eingeben!
                                    </invalid-small>
                                    <br/>
                                </b-col>
                            </b-row>
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Abbrechen</button>
                        <button type="button" class="btn btn-primary" href="#" v-on:click="saveChanges">
                            Änderungen speichern
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import BaseImageInput from "@/components/BaseImageInput.vue";
    import Category from "@/components/category/Category";
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import moment from "moment"
    import Article from "@/components/article/Article";
    import $ from "jquery";

    @Component({
        components: {BaseImageInput}
    })

    export default class EditArticleView extends Vue {
        @Prop() private article!: Article;

        validateInput = false;

        articleName: string;
        articleDescription: string;
        selectedCategories: Category[];
        image1 = '';
        image2 = '';
        image3 = '';
        image4 = '';
        image5 = '';
        minDate: Date;
        maxDate: any;
        country: string;
        plz: string;
        city: string;

        /*Article Description Count*/
        maxCount: number = 200;
        hasError: boolean = false;
        charCount: number = 0

        constructor() {
            super();
            this.articleName = this.article.name;
            this.articleDescription = this.article.description;
            this.selectedCategories = this.article.categories;
            // this.image1 = '';
            // this.image2 = '';
            // this.image3 = '';
            // this.image4 = '';
            // this.image5 = '';
            this.minDate = new Date();
            this.maxDate = null;
            this.country = this.article.location;
            //TODO with new Jsons
            this.plz = "";
            this.city = "";
        }

        countdown() {
            this.charCount = this.articleDescription.length;
            this.hasError = this.articleDescription.length > this.maxCount;
        }

        /*Selected Categories*/
        categories: Category[] = this.getCategories();

        getCategories(): Category[] {
            return Object.values(Category)
        }

        /*Availabilty*/
        today = new Date(moment().format("YYYY-MM-DD"));
        checked: number = 0;
        checkOptions = [{value: 0}, {value: 1}]

        /*Save Changes*/
        saveChanges(): void {
            let article: Article | null = null;

            this.validateInput = true;

            $.ajax({
                url: "http://localhost:9000/users/articles/update/" + this.article.id,
                type: "POST",
                data: JSON.stringify({
                    name: this.articleName,
                    description: this.articleDescription,
                    images: [""],
                    insertionDate: this.article.insertionDate,
                    location: this.country,
                    id: this.article.id,
                    categories: this.selectedCategories,
                    userId: this.article.userId
                }),
                dataType: "json",
                contentType: "application/json",
                success: result => {
                    article = result;
                    console.log("success ", result)
                    $('#editArticleModal').modal('hide');
                    this.$router.push({name: 'refresh'}).then(() => {
                        this.$router.push({name: 'articlePage', params: {article: article}});
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

    h1 {
        font-size: 25px;
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

    invalid-small {
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

    textarea.form-control {
        height: 100px;
        resize: none;
    }

    .text-small {
        font-size: 0.9em;
    }

</style>