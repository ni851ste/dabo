<template>
    <router-link :to="{ name: 'articlePage', params: {article: article}}">
        <div class="card article">
                <div class="card-body">
                    <img class="card-img-top articleImg" src="../../assets/categoryImgs/sonstiges.jpg" alt="Card image cap">
                    <h5 class="card-title" v-html="article.name"></h5>
                    <b-form-rating class="rating" variant="warning" readonly v-model="rating"></b-form-rating>
                    <p class="card-text description" v-html="article.description"></p>
                    <p class="card-text location">
                        <b-icon-geo-alt font-scale="1.2"></b-icon-geo-alt>
                        <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
                        {{article.location}}
                    </p>
                </div>
        </div>
    </router-link>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import moment from "moment"
    import Article from "@/components/article/Article";

    @Component
    export default class ArticleCard extends Vue {
        @Prop() private article!: Article;
        rating = this.getAvgStars();

        getAvgStars(): number {
            let starSum: number = 0;

            for(let rating of this.article.ratings) {
                starSum += rating.amountOfStars;
            }

            return starSum / this.article.ratings.length;
        }

        getDate(): string {
            return moment(this.article.insertionDate).format(" DD MMMM YYYY")
        }

    }
</script>

<style scoped>
    .article {
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

    .description {
        height: calc(20vh - 78.6px);
        overflow: hidden;
        margin-bottom: 5px;
    }

    .rating {
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
