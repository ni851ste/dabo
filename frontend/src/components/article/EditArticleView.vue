<template>
    <div>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editArticleModal">
            Artikel bearbeiten
        </button>

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
                            <div class="form-group validation">
                                <label for="articleNameInput">Artikelbezeichnung: *</label>
                                <input type="text" class="form-control" id="articleNameInput" required>
                                <div class="invalid-feedback">Example invalid custom file feedback</div>
                            </div>
                            <div>
                                <label>Artikelbeschreibung</label>
                                <textarea class="form-control" id="articleDescription" v-on:keyup="countdown"
                                          v-model="message"
                                          required></textarea>
                                <p class='text-right text-small' v-bind:class="{'text-danger': hasError }">{{charCount +
                                    '/' +
                                    maxCount}}</p>
                            </div>
                            <p>Zugehörigkeit:</p>
                            <b-container fluid>
                                <b-row>
                                    <b-form-checkbox-group
                                            id="categories"
                                            v-model="selected"
                                            :options="categories"
                                            name="category"
                                            required
                                    ></b-form-checkbox-group>
                                </b-row>
                            </b-container>
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
                                                    v-model="value" :min="minDate" locale="de">
                                            </b-form-datepicker>
                                        </div>
                                    </b-col>
                                </b-row>
                            </b-container>
                            <br>
                            <div>
                                <b-form-checkbox class="availableCheck" id="availabilityCheck" v-model="status"> Keine
                                    Begrenzung
                                </b-form-checkbox>
                            </div>
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


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Schließen</button>
                        <button type="button" class="btn btn-primary">Änderungen speichern</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">

    import {Vue} from 'vue-property-decorator';
    import moment from "moment"


    export default class EditArticleView extends Vue {
        maxCount: number = 200;
        message: string = '';
        hasError: boolean = false;
        charCount: number = 0

        countdown() {
            this.charCount = this.message.length;
            this.hasError = this.message.length > this.maxCount;
        }

        selected: [] = [];
        categories = [
            {text: 'Auto & Motorrad', value: 'car'},
            {text: 'Elektronik', value: 'electronics'},
            {text: 'Garten', value: 'garden'},
            {text: 'Haushalt', value: 'household'},
            {text: 'Kleidung', value: 'chlothes'},
            {text: 'Sport & Freizeit', value: 'sports'},
            {text: 'Unterhaltung', value: 'entertainment'},
            {text: 'Werkzeug', value: 'tools'}
        ]

        today = new Date(moment().format("YYYY-MM-DD"));
        minDate: Date = new Date()
    }
</script>

<style scoped>

    .borderbox {
        border:#d0f2e1 solid 2px;
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