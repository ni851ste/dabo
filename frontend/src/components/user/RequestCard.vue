<template>
    <div class="card request">
        <div class="card-body">
            <img class="card-img-top articleImg" src="../../assets/categoryImgs/sonstiges.jpg" alt="Card image cap">
            <img class="card-img-right userImg" :src="this.getUserPicture()" alt="Card image right">
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

        getUserPicture(): string {
            if (!this.user) {
                return "data:image/jpeg;base64,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mpqampqampqampqampqampqan+X/AP/Z"
            }
            if (!this.user.picture || this.user.picture === "") {
                return "data:image/jpeg;base64,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mpqampqampqampqampqampqan+X/AP/Z"
            }
            return this.user.picture;
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
