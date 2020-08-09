<template>
    <div>
        <NavigationBar></NavigationBar>
        <div class="container">
            <div class="col">
                <div class="borderbox profile">
                    <div class="profile-cover-img">
                        <img class="placeholer-image" :src="require(`@/assets/userImgs/avatar.png`)">
                        <h3 class="h3" v-html="userName"></h3>
                    </div>
                    <div class="profile-actions cover-img" data-overlay="0.3">
                        <button v-if="this.userAuthorizedForChanges()" type="button" class="btn btn-primary tmpButton" data-toggle="modal"
                                data-target="#editArticleModal">
                            Account bearbeiten
                        </button>
                        <button v-else class="btn btn-actions">
                            <span>Nachricht</span>
                        </button>
                    </div>
                    <div class="profile-info">
                        <ul class="nav">
                            <li><strong>DE</strong>{{this.user.address.city}}</li>
                            <li><strong>{{this.insertedArticlesCount}}</strong>Artikel</li>
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
                    <li v-if="this.userAuthorizedForChanges()" class="nav-item">
                        <a class="nav-link requests" data-toggle="tab" href="#requests">Anfragen</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane container active" id="articles">
                        <div class="borderbox">
                            <div class="panel-heading">
                                <h3 v-if="this.userAuthorizedForChanges()" class="panel-title">Meine Artikel</h3>
                                <h3 v-else class="panel-title">{{this.user.firstName}}s Artikel</h3>
                            </div>
                            <ArticleCard
                                    :key="article.name"
                                    id="cards"
                                    v-for="article in this.getUsersInsertedArticles()"
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
                                <h3 v-if="this.userAuthorizedForChanges()" class="panel-title">Meine Bewertungen</h3>
                                <h3 v-else class="panel-title">{{this.user.firstName}}s Bewertungen</h3>
                            </div>
                            <RatingCard
                                    id="rating-cards"
                                    v-for="rating in getUserRatings()"
                                    :rating="rating"
                                    :per-page="perPage"
                                    :current-page="currentPage"
                                    small
                            ></RatingCard>
                        </div>
                    </div>
                    <div v-if="this.userAuthorizedForChanges()"  class="tab-pane container fade" id="requests">
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
        <div class="modal fade" id="editArticleModal" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <EditMyAccountView :user="this.user" ></EditMyAccountView>
        </div>
    </div>

</template>

<script lang="ts">
    import NavigationBar from "@/components/NavigationBar.vue";
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import ArticleCard from "@/components/article/ArticleCard.vue";
    import Article from "@/components/article/Article";
    import Rating from "@/components/rating/Rating";
    import RatingCard from "@/components/rating/RatingCard.vue";
    import User from "@/components/user/User";
    import RequestCard from "@/components/user/RequestCard.vue";
    import EditMyAccountView from "@/components/user/EditMyAccountView.vue";
    import LoginService from "@/components/services/LoginService";

    @Component({
        components: {EditMyAccountView, RequestCard, RatingCard, NavigationBar, ArticleCard}
    })
    export default class UserProfileView extends Vue {
        @Prop() private user!: User;
        articles: Article[] = [];
        perPage = 5;
        currentPage = 1;
        ratings: Rating[] = [];
        requests: Article[] = [];
        loginService: LoginService = LoginService.getInstance();

        get userName(): string {
            if (!this.user)
                return "";

            if (this.user.lastNameVisible) {
                return this.user.firstName + " " + this.user.lastName;
            } else {
                return this.user.firstName
            }
        };

        get insertedArticlesCount(): number {
            if (!this.user || !this.user.insertedArticlesId)
                return 0;

            return this.user.insertedArticlesId.length;
        };

        getUsersInsertedArticles(): Article[] {
            if(!this.user.insertedArticlesId || this.user.insertedArticlesId.length === 0) {
                return [];
            }

            let insertedArticles: Article[] = [];

            for(let articleId of this.user.insertedArticlesId) {
                let article: Article | null = this.fetchArticle(articleId);
                if(article) {
                    insertedArticles.push(article)
                }
            }

            return insertedArticles.slice(
                (this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage
            )
        }

        fetchArticle(articleId: number): Article | null {
            let article: Article | null = null
             $.ajax({
                url: "http://localhost:9000/users/articles/" + articleId,
                type: "GET",
                success: result => {
                    console.log("success fetching article", result);
                    article = result
                },
                error: error => {
                    console.log("error ", error)
                }
            });
            return article
        }

        getUserRatings(): Rating[] {
            if(!this.user.ratings || this.user.ratings.length === 0) {
                return []
            }
            let ratings: Rating[] = this.user.ratings;
            return ratings.slice(
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

        userAuthorizedForChanges(): boolean {
            if(!this.user || !this.loginService.loggedInUser) {
                return false;
            }
            return this.user.id === this.loginService.loggedInUser.id
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

    .btn {
        border: #484848;
    }

    .btn:hover {
        color: #484848;
        background-color: #abc7b8;
        border: none;
        text-decoration: none;
    }

    .btn:focus {
        background-color: #484848;
        color: white;
        border: none;
    }

    .btn:active {
        background-color: #484848 !important;
        color: white !important;
        border: none;
    }

</style>
