<template>
    <div class="card article">
        <div class="card-body">
            <img class="card-img-top articleImg" src="../../assets/categoryImgs/sonstiges.jpg" alt="Card image cap">
            <h5 class="card-title">{{article.name}}</h5>
            <p class="card-text">{{article.description}}</p>
            <p class="card-text location">
                <b-icon-geo-alt font-scale="1.2"></b-icon-geo-alt>
                <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
                {{article.location}}
            </p>
<!--            <p class="card-text insertionDate">{{this.getDate()}}-->
<!--            </p>-->
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import Article from "@/components/article/Article";
    import moment from "moment"

    @Component
    export default class ArticleCard extends Vue {
        @Prop() private article!: Article
        getDate(): string {
            return moment(this.article.insertionDate).format(" DD MMMM YYYY")
        }
    }
</script>

<style scoped>
    .card {
        /*border: .5vh solid;*/
    }

    .article {
        width: 60vw;
        height: 15vh;
        margin: auto;
        margin-bottom: 15px;
    }

    .card-body {
        padding: 0;
    }

    .articleImg {
        float: left;
        margin-right: 20px;
    }

    .card-img-top {
        object-fit: cover;
        /*height: calc(15vh-2px);*/
        height: 100%;
        width: 15vh;
        border-radius: 3px 0 0 3px;
        /*height: 150px;*/
        /*width: 150px;*/

    }

    .card-title {
        padding-top: 10px;
        display: flex;
        font-size: large;
        font-weight: bold;
    }

    .card-text {
        font-size: medium;
        display: flex;
    }

    .location {
        /*padding-right: 15px;*/
    }

    .insertionDate {
        padding-right: 15px;
    }
</style>