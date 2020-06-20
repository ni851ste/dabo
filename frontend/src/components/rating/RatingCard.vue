<template>
    <router-link :to="{ name: 'userProfile', params: {rating: rating}}">
        <div class="card rating">
            <div class="card-body">
                <img class="card-img-top userImg" src="../../assets/userImgs/avatar.png" alt="Card image cap">
                <h5 class="card-title" v-html="rating.author"></h5>
                <b-form-rating class="star-rating" variant="warning" readonly v-model="rating.amountOfStars"></b-form-rating>
                <p class="card-text comment" v-html="rating.text"></p>
                <p class="card-text rating-date">
                    <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
                    {{getDate()}}
                </p>
            </div>
        </div>
    </router-link>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import Rating from "@/components/rating/Rating";
    import moment from "moment"


    @Component
    export default class RatingCard extends Vue {
        @Prop() private rating!: Rating;

        getDate(): string {
            return moment(this.rating.date).format("dddd, DD. MMMM YYYY HH:mm")
        }

    }
</script>

<style scoped>
    .rating {
        width: 60vw;
        height: 20vh;
        margin: auto;
        margin-bottom: 15px;
    }

    .card-body {
        padding: 0;
        text-align: left;
    }

    .userImg {
        float: left;
        margin-right: 20px;
    }

    .card-img-top {
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

    .comment {
        height: calc(20vh - 78.6px);
        overflow: hidden;
        margin-bottom: 5px;
    }

    .star-rating {
        width: 120px;
        display: inline-flex !important;
        margin-left: 10px;
        border: none;
    }

    a {
        text-decoration: none!important;
        color: #484848;
    }

    a:hover {
        text-decoration:none!important;
    }

</style>