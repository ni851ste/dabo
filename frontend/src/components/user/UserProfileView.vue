<template>
    <div>
        <NavigationBar></NavigationBar>
        <div class="container">
            <div class="col">
                <div class="borderbox profile">
                    <div class="profile-cover-img">
                        <img class="placeholer-image" :src="require(`@/assets/userImgs/avatar.png`)">
                        <h3 class="h3">Max Mustermann</h3>
                    </div>
                    <div class="profile-actions cover-img" data-overlay="0.3">
                        <button class="btn btn-actions">
                            <span>Freund hinzufügen</span>
                        </button>
                        <button class="btn btn-actions">
                            <span>Nachricht</span>
                        </button>
                    </div>
                    <div class="profile-info">
                        <ul class="nav">
                            <li><strong>DE</strong>Konstanz</li>
                            <li><strong>2</strong>Artikel</li>
                            <li><strong>13</strong>Freunde</li>
                        </ul>
                    </div>
                </div>
                <!-- Nav tabs -->
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link articles" data-toggle="tab" href="#articles">Artikel</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ratings" data-toggle="tab" href="#ratings">Bewertungen</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link requests" data-toggle="tab" href="#requests">Anfragen</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane container active" id="articles">
                        <div class="borderbox">
                            <div class="panel-heading">
                                <h3 class="panel-title">Max' Artikel</h3>
                            </div>
                            <ArticleCard
                                    :key="article.name"
                                    id="cards"
                                    v-for="article in lists()"
                                    :article="article"
                                    :per-page="perPage"
                                    :current-page="currentPage"
                                    small
                            >
                            </ArticleCard>
                        </div>
                    </div>
                    <div class="tab-pane container fade" id="ratings">
                        <div class="borderbox">
                            <div class="panel-heading">
                                <h3 class="panel-title">Max' Bewertungen</h3>
                            </div>
                            <RatingCard
                                    id="rating-cards"
                                    v-for="rating in ratingLists()"
                                    :rating="rating"
                                    :per-page="perPage"
                                    :current-page="currentPage"
                                    small
                            ></RatingCard>
                        </div>
                    </div>
                    <div class="tab-pane container fade" id="requests">
                        <div class="borderbox">
                            <div class="panel-heading">
                                <h3 class="panel-title">Meine Anfragen</h3>
                            </div>
                            <RequestCard
                                    id="request-cards"
                                    v-for="request in requestLists()"
                                    :request="request"
                                    :per-page="perPage"
                                    :current-page="currentPage"
                                    small
                            ></RequestCard>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import NavigationBar from "@/components/NavigationBar.vue";
    import {Component, Vue} from 'vue-property-decorator';
    import ArticleCard from "@/components/article/ArticleCard.vue";
    import Article from "@/components/article/Article";
    import Rating from "@/components/rating/Rating";
    import RatingCard from "@/components/rating/RatingCard.vue";
    import RequestCard from "@/components/user/RequestCard.vue";

    @Component({
        components: {RequestCard, RatingCard, NavigationBar, ArticleCard}
    })
    export default class UserProfileView extends Vue {
        perPage = 5;
        currentPage = 1;

        articles: Article[] = [];
        ratings: Rating[] = [];
        requests: Article[] = [];

        constructor() {
            super();
            let article: Article = new Article("Beerpong table", "Awesome beerpong table", "", "Konstanz", new Date(), [], []);
            let article2: Article = new Article("Bohrmaschine", "Toll zum bohren", "", "Konstanz", new Date(), [], []);
            this.articles.push(article);
            this.articles.push(article2);

            this.requests.push(article);

            let rating: Rating = new Rating(3, "Sehr zuverlässig und unkompliziert.", 1, new Date())
            let rating2: Rating = new Rating(1, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took " +
                "a galley of type and scrambled it to make a type specimen book. "
                , 2, new Date())
            this.ratings.push(rating)
            this.ratings.push(rating2)

        }

        lists(): Article[] {
            const items = this.articles;
            return items.slice(
                (this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage
            )
        }

        ratingLists(): Rating[] {
            const items = this.ratings;
            return items.slice(
                (this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage
            )
        }

        requestLists(): Article[] {
            const items = this.requests;
            return items.slice(
                (this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage
            )
        }

    }

</script>

<style scoped>
    .profile {
        position: relative;
        z-index: 0;
    }

    .borderbox {
        margin-bottom: 30px;
        border-radius: 5px;
        padding: 0px;
        border: none;
        box-shadow: 0 1px 5px #484848;
    }

    .profile-actions {
        display: -ms-flexbox;
        display: -webkit-box;
        display: flex;
        padding: 120px 30px 10px 153px;
        border-radius: 5px 5px 0 0;
        -ms-flex-wrap: wrap;
        flex-wrap: wrap;
        -ms-flex-pack: end;
        -webkit-box-pack: end;
        justify-content: flex-end;
        overflow: hidden;
        background: #d0f2e1;
        background-size: cover;
    }

    .profile-actions > .btn {
        margin-left: 10px;
        margin-bottom: 10px;
        background: #484848;
        color: white;
    }

    .profile-cover-img {
        position: absolute;
        top: 120px;
        left: 30px;
        text-align: center;
        z-index: 1;
    }

    .profile-cover-img > img {
        max-width: 120px;
        border: 5px solid white;
        border-radius: 50%;
    }

    .profile-cover-img > .h3 {
        color: #484848;
        font-size: 20px;
        line-height: 30px;
    }

    .profile-cover__img > img + .h3 {
        margin-top: 6px;
    }

    .profile-info .nav {
        margin-right: 28px;
        padding: 15px 0 10px 170px;
        color: #999;
        font-size: 16px;
        line-height: 26px;
        font-weight: 300;
        text-align: center;
        text-transform: uppercase;
        -ms-flex-pack: end;
        -webkit-box-pack: end;
        justify-content: flex-end;
    }

    .profile-info .nav li {
        margin-top: 13px;
        margin-bottom: 13px;
    }

    .profile-info .nav li:not(:first-child) {
        margin-left: 30px;
        padding-left: 30px;
        border-left: 1px solid #eee;
    }

    .profile-info .nav strong {
        display: block;
        margin-bottom: 10px;
        color: #d0f2e1;
        font-size: 34px;
    }

    @media (min-width: 481px) {
        .profile-cover__action > .btn {
            min-width: 125px;
        }

        .profile-cover__action > .btn > span {
            display: inline-block;
        }
    }

    @media (max-width: 600px) {
        .profile-cover__info .nav {
            display: block;
            margin: 90px auto 0;
            padding-left: 30px;
            padding-right: 30px;
        }

        .profile-cover__info .nav li:not(:first-child) {
            margin-top: 8px;
            margin-left: 0;
            padding-top: 18px;
            padding-left: 0;
            border-top: 1px solid #eee;
            border-left-width: 0;
        }
    }

    ul.nav li a, ul.nav li a:visited {
        color: #484848 !important;
    }

    ul.nav li a:hover, ul.nav li a:active {
        color: #d0f2e1 !important;
    }

    .borderbox {
        margin-bottom: 30px;
        color: #484848;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.08);
    }

    .panel-heading {
        color: #484848;
        padding: 10px 20px;
        border-width: 0;
        border-radius: 0;
    }

    .panel-heading:after,
    .panel-heading:before {
        content: " ";
        display: table;
    }

    .panel-heading:after {
        clear: both;
    }

    .panel-title {
        float: left;
        margin-top: 3px;
        margin-bottom: 3px;
        font-size: 14px;
        line-height: 24px;
        font-weight: 700;
        text-transform: uppercase;
    }

</style>