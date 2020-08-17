<template>
    <div>
        <NavigationBar></NavigationBar>
        <div class="container">
            <div class="col">
                <div class="borderbox profile">
                    <div class="profile-cover-img">
                        <img class="placeholer-image" :src=this.getUserPicture()>
                        <h3 class="h3" v-html="userName"></h3>
                    </div>
                    <div class="profile-actions cover-img" data-overlay="0.3">

                        <button type="button" class="btn btn-primary"
                                data-toggle="modal"
                                data-target="#createRatingModal">
                            Nutzer bewerten
                        </button>
                        <RatingCreationView class="rating-modal" :rating-object="this.user"></RatingCreationView>

                        <button v-if="this.userAuthorizedForChanges()" type="button" class="btn btn-primary tmpButton"
                                data-toggle="modal"
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
                            <li><strong>{{this.ratingsCount}}</strong>Bewertungen</li>
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
                    <div v-if="this.userAuthorizedForChanges()" class="tab-pane container fade" id="requests">
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
            <EditMyAccountView :user="this.user"></EditMyAccountView>
        </div>

<!--        <div class="modal fade" id="createRatingModal" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <RatingCreationView :rating-object="this.user"></RatingCreationView>
        </div>
-->
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
    import RatingCreationView from "@/components/rating/RatingCreationView.vue";

    @Component({
        components: {EditMyAccountView, RatingCreationView, RequestCard, RatingCard, NavigationBar, ArticleCard}
    })
    export default class UserProfileView extends Vue {
        @Prop() private user!: User;
        articles: Article[] = [];
        perPage = 5;
        currentPage = 1;
        ratings: Rating[] = [];
        requests: Article[] = [];
        loginService: LoginService = LoginService.getInstance();

        mounted(): void {
            if (this.loginService.loggedInUser) {
                this.loginService.getUserWithId(this.loginService.loggedInUser.id)
            }
        }

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

        get ratingsCount(): number {
            if (!this.user || !this.user.ratings)
                return 0;

            return this.user.ratings.length;
        };

        getUsersInsertedArticles(): Article[] {
            if (!this.user.insertedArticlesId || this.user.insertedArticlesId.length === 0) {
                return [];
            }

            let insertedArticles: Article[] = [];

            for (let articleId of this.user.insertedArticlesId) {
                let article: Article | null = this.fetchArticle(articleId);
                if (article) {
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
            if (!this.user.ratings || this.user.ratings.length === 0) {
                return []
            }
            let ratings: Rating[] = this.user.ratings;
            return ratings.slice(
                (this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage
            )
        }

        //TODO: load from user, not implemented in user yet
        requestLists(): Article[] {
            const items = this.requests;
            return items.slice(
                (this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage
            )
        }

        userAuthorizedForChanges(): boolean {
            if (!this.user || !this.loginService.loggedInUser) {
                return false;
            }
            return this.user.id === this.loginService.loggedInUser.id
        }

        getUserPicture(): string {
            if (!this.user) {
                return "data:image/jpeg;base64,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mpqampqampqampqampqampqan+X/AP/Z"
            }
            if (!this.user.picture || this.user.picture === "") {
                return "data:image/jpeg;base64,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mpqampqampqampqampqampqan+X/AP/Z"
                // return this.getBase64(new File(['avatar'], '../../assets/userImgs/avatar.png'));
            }
            return this.user.picture;
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

        width: 6.25vw;
        height: 6.25vw;
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
