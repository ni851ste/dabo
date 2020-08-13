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
                        <h5 class="modal-title" id="createRatingLabel">Bewertung erstellen für {{ratingObject}}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="borderbox b-container">

                            <div class="form-group amount-of-stars">
                                <label>Anzahl Sterne:</label>
                                <b-form-rating v-model="amountOfStars" show-value size="lg" variant="warning"></b-form-rating>
                            </div>

                            <div class="form-group rating-comment">
                                <label>Kommentar:</label>
                                <textarea class="form-control" v-model="ratingComment"></textarea>
                            </div>

                        </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Abbrechen</button>
                        <button type="button" class="btn btn-primary" href="#" v-on:click="createRating()">
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
    import $ from "jquery";

    export default class RatingCreationView extends Vue {

        ratingObject: String = "RatingObject";
        amountOfStars: number = 0;
        ratingComment: string = "";
        author: number = 0;

        createRating(): void {
            let rating: Rating;

            let date: Date = new Date();
/*            $.ajax({
                url: "http://localhost:9000/rating/create",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    amountOfStars: this.amountOfStars,
                    text: this.ratingComment,
                    author: this.author,
                    date: date
                }),
                dataType: "json",

                success: result => {
                    console.log("success CreateRating", result);
                    rating = result
                    console.log("rating created is", rating);
                    this.$router.push({name: '??', params: {rating: rating, showAlert: true}});
                },
                error: error => {
                    console.log("error ", error)
                }
            });
            
 */
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
