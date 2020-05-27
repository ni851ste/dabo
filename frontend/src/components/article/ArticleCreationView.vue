<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <div class="contentLayout my-buttons">
            <div class="borderbox b-container b-form-checkbox-group">
                <h1> Allgemein</h1>
                <div class="form-group">
                    <label for="articleNameInput">Artikelbezeichnung:</label>
                    <input type="name" class="form-control" id="articleNameInput">
                </div>
                <div>
                    <label>Artikelbeschreibung</label>
                    <textarea class="form-control" v-on:keyup="countdown" v-model="message"></textarea>
                    <p class='text-right text-small' v-bind:class="{'text-danger': hasError }">{{charCount + '/' +
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
                        ></b-form-checkbox-group>
                    </b-row>
                </b-container>
            </div>

            <div class="borderbox">
                <h1>Bilder</h1>
                <div class="b-container">
                    <b-row>
                        <div b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="imageFile"/>
                            </div>
                        </div>
                        <div b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="imageFile"/>
                            </div>
                        </div>
                        <div b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="imageFile"/>
                            </div>
                        </div>
                        <div b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="imageFile"/>
                            </div>
                        </div>
                        <div b-col>
                            <div class="imageUpload">
                                <base-image-input v-model="imageFile"/>
                            </div>
                        </div>
                    </b-row>
                </div>
            </div>

            <div class="borderbox">
                <h1>Verfügbarkeit</h1>
                <date-range-picker
                        :startDate="startDate"
                        :endDate="endDate"
                        @update="console.log(value)"
                        :locale-data="locale"
                >
                    <div slot="input" slot-scope="picker">...</div>
                </date-range-picker>
            </div>

            <div class="borderbox">
                <h1>Standort</h1>
                <div class="form-group">
                    <label for="countryInput">Land:</label>
                    <input type="string" class="form-control" id="countryInput">
                </div>
                <div class="form-group">
                    <label for="cityInput">Stadt:</label>
                    <input type="string" class="form-control" id="cityInput">
                </div>
            </div>

            <div class="addButton">
                <div>
                    <button type="button" class="btn add-btn-primary btn-block">Artikel hinzufügen</button>
                </div>
            </div>

        </div>
    </div>
</template>

<script lang="ts">
    import NavigationBar from "@/components/NavigationBar.vue";
    import BaseImageInput from "@/components/BaseImageInput.vue";
    import {Component, Vue} from 'vue-property-decorator';

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

        data() {
            return {
                imageFile: null
            }
        }
    }
</script>

<style scoped>

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
        border-radius: 8px;
        padding: 20px;
        text-align: left;
    }

    .b-container {
        align-content: start;
        alignment: left;
    }

    .addButton {
        background-color: #d0f2e1;
        border-color: #d0f2e1;
        color: #484848;
        width: 100%;
        margin-top: 15px;
        margin-bottom: 25px;
        border: solid #d0f2e1;
        border-radius: 8px;
    }

    textarea.form-control {
        height: 100px;
    }

    .text-small {
        font-size: 0.9em;
    }

</style>