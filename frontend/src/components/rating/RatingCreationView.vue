<template>
    <div>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createRatingModal">
            Bewertung erstellen
        </button>

        <div class="modal fade" id="createRatingModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createRatingLabel">Bewertung erstellen</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="borderbox b-container">

                            <div class="form-group">
                                <label for="ratingHeaderInput">Überschrift: </label>
                                <input type="text" class="form-control" id="ratingHeaderInput" v-model="ratingHeader" autocomplete="off">
                            </div>

                            <div class="form-group">
                                <label>Sterne: </label>
                                <b-form-rating v-model="amountOfStars" show-value
                                               size="lg" variant="warning"></b-form-rating>
                            </div>

                            <div class="form-group">
                                <label>Kommentar:</label>
                                <label for="ratingComment"></label>
                                <textarea class="form-control" id="ratingComment" v-on:keyup="countdown"
                                          v-model="ratingComment"></textarea>
                                <p class='text-right text-small' v-bind:class="{'text-danger': hasError }">{{charCount +
                                    '/' + maxCount}}</p>
                            </div>

                        </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Abbrechen</button>
                        <button type="button" class="btn btn-primary" href="#" v-on:click="saveChanges">
                            Speichern
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</template>

<script lang="ts">
    import {Vue} from 'vue-property-decorator';
    import Rating from "@/components/rating/Rating";

    export default class RatingCreationView extends Vue {
        ratingHeader: string = "";
        amountOfStars:number = 0;
        ratingComment: string = " ";
        author: number = 1

        maxCount: number = 100;
        hasError: boolean = false;
        charCount: number = 0

        countdown() {
            this.charCount = this.ratingComment.length;
            this.hasError = this.ratingComment.length > this.maxCount;
        }

        saveChanges(): void {
            let header = this.ratingHeader
            let amountOfStars = this.amountOfStars
            let comment = this.ratingComment
            let author = this.author
            let rating = new Rating(amountOfStars, header, comment, author, new Date())
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

    .b-container {
        align-content: start;
        alignment: left;
    }

    :focus {
        outline: none !important;
    }

    .btn-primary {
        background-color: #d0f2e1;
        border-color: #d0f2e1;
        color: #484848;
    }

    .btn-secondary {
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