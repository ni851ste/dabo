<template>
    <div>
        <!-- Modal -->
        <div class="modal fade" data-backdrop="false" id="createRatingModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">

                    <!-- HEADER -->
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Bewertung erstellen für:
                            {{ratingObjectName}}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <!-- BODY -->
                    <div class="modal-body">
                        <div class="borderbox b-container">

                            <div class="form-group amount-of-stars">
                                <label>Anzahl Sterne:</label>
                                <b-form-rating v-model="amountOfStars" show-value size="lg"
                                               variant="warning"></b-form-rating>
                            </div>

                            <div class="form-group rating-comment">
                                <label>Kommentar:</label>
                                <textarea class="form-control" v-model="ratingComment"></textarea>
                            </div>
                        </div>

                        <!-- FOOTER -->
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Abbrechen</button>
                            <button type="button" class="btn btn-primary btn-save" data-dismiss="modal" @click="createRating">
                                Speichern</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import Article from "@/components/article/Article";
    import LoginService from "@/components/services/LoginService";
    import Rating from "@/components/rating/Rating";
    import User from "@/components/user/User";
    import $ from "jquery";

    @Component
    export default class RatingCreationView extends Vue {
        @Prop() private ratingObject!: Article | User;



        get ratingObjectName() {
            if (this.ratingObject instanceof Article) {
                return this.ratingObject.name;
            }
            else if (this.ratingObject instanceof User) {
                return this.ratingObject.firstName
            }
        }

        loginService: LoginService = LoginService.getInstance();
        amountOfStars: number = 0;
        ratingComment: String = "";
        authorId = this.loginService.loggedInUser?.id;

        createRating(): void {
            let rating: Rating;
            let date: Date = new Date();

            if (this.ratingObject instanceof Article) {

                $.ajax({
                    url: "http://localhost:9000/article/rating/create",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        article: this.ratingObject,
                        amountOfStars: this.amountOfStars,
                        comment: this.ratingComment,
                        author: this.authorId,
                        date: date
                    }),
                    dataType: "json",

                    success: result => {
                        console.log("success CreateRating", result);
                        rating = result
                        console.log("rating created is", rating);
                        this.$router.push({name: 'articlePage', params: {rating: rating, showAlert: true}});
                    },
                    error: error => {
                        console.log("error ", error)
                    }
                });
            }
                else if (this.ratingObject instanceof User) {

                $.ajax({
                    url: "http://localhost:9000/user/rating/create",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        userId: this.ratingObject.id,
                        amountOfStars: this.amountOfStars,
                        comment: this.ratingComment,
                        author: this.authorId,
                        date: date
                    }),
                    dataType: "json",

                    success: result => {
                        console.log("success CreateRating", result);
                        rating = result
                        console.log("rating created is", rating);
                        this.$router.push({name: 'user', params: {rating: rating, showAlert: true}});
                    },
                    error: error => {
                        console.log("error ", error)
                    }
                });
            }
        }
    }
</script>

<style>

    .modal {
        top: 50px !important;
        left: -10px !important;
        background: rgba(0, 0, 0, 0.9);
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

    h5 {
        font-weight: bold;
    }

    .b-container {
        align-content: start;
        alignment: left;
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

    .btn-save {
        background-color: #484848;
        border-color: #484848;
        color: white;
    }

    textarea.form-control {
        height: 100px;
        resize: none;
    }

</style>