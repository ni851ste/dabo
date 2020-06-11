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
                            <span>Folgen</span>
                        </button>
                        <button class="btn btn-actions">
                            <span>Nachricht</span>
                        </button>
                    </div>
                    <div class="profile-info">
                        <ul class="nav">
                            <li><strong>26</strong>Artikel</li>
                            <li><strong>33</strong>Follower</li>
                            <li><strong>13</strong>Following</li>
                        </ul>
                    </div>
                </div>
                <div class="borderbox">
                    <div class="panel-heading">
                        <h3 class="panel-title">Julia's Artikel</h3>
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
        </div>
    </div>
</template>

<script lang="ts">
    import NavigationBar from "@/components/NavigationBar.vue";
    import {Component, Vue} from 'vue-property-decorator';
    import ArticleCard from "@/components/article/ArticleCard.vue";
    import Article from "@/components/article/Article";

    @Component({
        components: {NavigationBar, ArticleCard}
    })
    export default class UserProfileView extends Vue {
        articles: Article[] = [];
        perPage = 5;
        currentPage = 1;

        constructor() {
            super();
            let article: Article = new Article("Beerpong table", "Awesome beerpong table", "", "Konstanz", new Date(), []);
            let article2: Article = new Article("Bohrmaschine", "Toll zum bohren", "", "Konstanz", new Date(), []);
            this.articles.push(article);
            this.articles.push(article2);
        }

        lists(): Article[] {
            const items = this.articles;
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