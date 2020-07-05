<template>
    <div class="card request">
        <div class="card-body">
            <img class="card-img-top articleImg" src="../../assets/categoryImgs/sonstiges.jpg" alt="Card image cap">
            <img class="card-img-right userImg" src="../../assets/userImgs/avatar.png" alt="Card image right">
            <h5 class="card-title">Bohrmaschine</h5>
            <b-form-rating class="rating" variant="warning" readonly v-model="rating"></b-form-rating>
            <b-container class="date-button-container">
                <b-row class="text-center">
                    <b-col><p class="card-text user" locale="de">Lisa Müller am {{getDate()}}:</p></b-col>
                    <b-col><button class="btn btn-actions"><span>Annehmen</span></button></b-col>
                </b-row>
                <b-row>
                    <b-col><p class="card-text message">Hallo Max! Ich würde gerne diesen Artikel ausleihen!</p></b-col>
                    <b-col><button class="btn btn-actions"><span>Ablehnen</span></button></b-col>
                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import moment from "moment"
    import Article from "@/components/article/Article";
    import User from "@/components/user/User";

    @Component
    export default class RequestCard extends Vue {
        @Prop() private article!: Article;
        @Prop() private user!: User;
        rating = 3;
        today = new Date()

        getAvgStars(): number {
            let starSum: number = 0;

            for (let rating of this.article.ratings) {
                starSum += rating.amountOfStars;
            }

            return starSum / this.article.ratings.length;
        }

        getDate(): string {
            return moment(this.today).locale('de').format(" dddd, DD MMMM YYYY")
        }
    }
</script>

<style scoped>
    .request {
        width: 60vw;
        height: 20vh;
        margin: auto;
        margin-bottom: 15px;
    }

    .card-body {
        padding: 0;
        text-align: left;
    }

    .articleImg {
        float: left;
        margin-right: 20px;
    }

    .userImg {
        float: right;
    }

    .card-img-top {
        object-fit: cover;
        height: 100%;
        width: 20vh;
        border-radius: 3px 0 0 3px;
    }

    .card-img-right {
        object-fit: cover;
        height: 100%;
        width: 20vh;
        border-radius: 3px 0 0 3px;
    }

    .card-title {
        padding-top: 10px;
        display: inline-flex;
        font-size: large;
        font-weight: bold;
    }

    .card-text {
        font-size: medium;
        display: flex;
        padding-right: 15px;
        text-align: left;
    }

    .user {
        font-weight: bold;
    }

    .message {
        height: calc(20vh - 78.6px);
        overflow: hidden;
        margin-bottom: 1px;
    }

    .rating {
        width: 120px;
        display: inline-flex !important;
        margin-left: 10px;
        border: none;
    }

    a {
        text-decoration: none !important;
        color: #484848;
    }

    a:hover {
        text-decoration: none !important;
    }

    .btn-actions {
        background-color: #d0f2e1;
        padding: 0;
        margin-top: 8px;
        border: none;
        float: right;
        height: 2vw;
        width: 5vw;
    }

    .btn-actions:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
        border-radius: 3px;
    }
</style>
