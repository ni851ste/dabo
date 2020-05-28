<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <div class="contentLayout my-buttons">
            <div class="borderbox b-container b-form-checkbox-group" >
                <h4> Allgemein</h4>

                <div class="form-group">
                    <label for="articleNameInput">Artikelbezeichnung:</label>
                    <input type="text" class="form-control" id="articleNameInput">
                </div>

                <div class="form-contol">
                    <label for="articleDescriptionInput">Artikelbeschreibung:</label>
                    <textarea class="form-control" id="articleDescriptionInput" rows="3" maxlength='250'></textarea>
                </div>
                <br>
                <p>Zugehörigkeit:</p>
                    <b-container fluid>
                        <b-row>
                            <b-form-checkbox-group
                                    id="categories"
                                    v-model="selected"
                                    :options="options"
                                    name="category"
                            ></b-form-checkbox-group>
                        </b-row>
                    </b-container>
                </div>

            <div class="borderbox file-upload-wrapper">
                <h4>Bilder</h4>
                <div>
                    <b-form-file v-model="file" ref="file-input" class="mb-2" multiple
                            :state="Boolean(file)"
                            placeholder="Choose a file or drop it here..."
                    ></b-form-file>
                </div>
            </div>

            <div class="borderbox">
                <h4>Verfügbarkeit</h4>

                <b-container fluid>
                    <b-row>
                        <b-col>
                            <b-form-datepicker id="example-datepicker" v-model="value" class="mb-2" placeholder="von"></b-form-datepicker>
                        </b-col>
                        <b-col>
                            <b-form-datepicker id="example-datepicker" v-model="value" class="mb-2" placeholder="bis"></b-form-datepicker>
                        </b-col>
                    </b-row>

                </b-container>

            </div>

            <div class="borderbox">
                <h4>Standort</h4>
                <div class="form-group">
                    <label for="countryInput">Land:</label>
                    <input type="text" class="form-control" id="countryInput">
                </div>
                <div class="form-group">
                    <label for="cityInput">Stadt:</label>
                    <input type="text" class="form-control" id="cityInput">
                </div>
            </div>

            <div class="borderbox">
                <div>
                    <button type="button" class="btn btn-primary btn-block" v-on:click="createArticle">Artikel hinzufügen</button>                </div>
                </div>
            </div>

    </div>
</template>

    <script lang="ts">
    import NavigationBar from "@/components/NavigationBar.vue";
    import { Component, Vue } from 'vue-property-decorator';
    import Article from "@/components/article/Article";
    import $ from "jquery";
    @Component({
        components: {NavigationBar}
    })


    export default class ArticleCreationView extends Vue{
        data() {
            return {
                selected: [], // Must be an array reference!
                options: [
                    { text: 'Auto & Motorrad', value: 'car' },
                    { text: 'Elektronik', value: 'electronics' },
                    { text: 'Garten', value: 'garden' },
                    { text: 'Haushalt', value: 'household' },
                    { text: 'Kleidung', value: 'chlothes' },
                    { text: 'Sport & Freizeit', value: 'sports' },
                    { text: 'Unterhaltung', value: 'entertainment' },
                    { text: 'Werkzeug', value: 'tools' }
                ]
            }
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
                data: {name: name, description: description, image: image, location: location, insertionDate: insertionDate },
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

<style scoped>

    .borderbox {
        border:#484848 solid 1px;
        width: 80%;
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

    .btn-primary {
        background-color: #d0f2e1;
        border-color: #d0f2e1;
        color:#484848;

    }
</style>