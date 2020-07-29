<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <div class="contentLayout">
            <b-alert v-model="showAlert" class="alert" variant="success" dismissible>Ihr Artikel wurde erfolgreich erstellt.</b-alert>
            <b-container fluid class="bv-example-row">
                <b-row>
                    <b-col cols="8" class="article-col bcol">
                        <div id="carouselExampleIndicators" class="carousel slide img-slide"
                             data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/sonstiges.jpg`)"
                                         alt="First slide">
                                </div>
                                <div class="carousel-item">
                                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/sonstiges.jpg`)"
                                         alt="Second slide">
                                </div>
                                <div class="carousel-item">
                                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/sonstiges.jpg`)"
                                         alt="Third slide">
                                </div>
                            </div>
                            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button"
                               data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button"
                               data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                        <div class="article-info">
                            <h3 class="headline" v-html="article.name"></h3>
                            <b-form-rating class="rating" variant="warning" readonly
                                           v-model="articleRating"></b-form-rating>
                            <div class="categories">
                                <div class="category" v-for="categoryLabel in this.article.categories">
                                    {{categoryLabel}}
                                </div>
                            </div>
                            <p class="description-section section" v-html="article.description"></p>
                            <!--                            <div class="section">-->
                            <!--                    Verfügbarkeit-->
                            <!--                            </div>-->
                        </div>

                    </b-col>
                    <b-col cols="4" class="user-col bcol" v-on:click="routeToUserPage">
                        <b-row class="user">
                            <b-col class="bcol" cols="4">
                                <img class="clip-circle" :src="require(`@/assets/categoryImgs/haushalt.jpg`)" alt="">
                            </b-col>
                            <b-col align-self="stretch bcol" class="user-details" cols="5">
<!--                                //TODO: link to userpage-->
<!--                                <RouterLink to="/user">-->
                                    <p v-html="userName"></p>
<!--                                </RouterLink>-->
                                <b-form-rating class="rating" variant="warning" readonly
                                               v-model="userRating"></b-form-rating>
                                <p class="section"></p>
                                Adresse:
                                <p class="address" v-html="userAddress"></p>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import Article from "@/components/article/Article";
    import moment from "moment";
    import Rating from "@/components/rating/Rating";
    import User from "@/components/user/User";
    import $ from "jquery";
    import Address from "@/components/user/Address";

    @Component({
        components: {NavigationBar}
    })
    export default class SingleArticlePage extends Vue {
        @Prop() private article!: Article;
        @Prop({
            required: false,
            default: false
        }) private showAlert!: boolean;

        user: User | null;
        articleRating: number;
        userRating: number;

        get userName(): string {
            console.log("article is here ", this.article)
            if (!this.user)
                return "";

            if (this.user.lastNameVisible) {
                return this.user.firstName + " " + this.user.lastName;
            } else {
                return this.user.firstName
            }
        };

        get userAddress(): string{
            if (!this.user)
                return "";

            if (this.user.address.streetVisible) {
                return this.user.address.street + "<br/> " + this.user.address.plz + " "+ this.user.address.city
                    + "<br/> " + this.user.address.country;
            } else {
                return this.user.address.plz + " "+ this.user.address.city
                    + "<br/> " + this.user.address.country
            }
        };
        @Prop({
            required: false,
            default: false
        }) private showAlert!: boolean;
        articleRating: number = this.getAvgStars(this.article.ratings);

        constructor() {
            super();
            if(!this.article || localStorage.getItem("selectedArticle") != null) {
                this.article = JSON.parse(localStorage.getItem("selectedArticle")!)
            }
            this.articleRating = 0
            this.userRating = 0
            this.getUser = this.getUser.bind(this)
            this.getAvgStars = this.getAvgStars.bind(this)
            // this.user = new User(0, "email", "password", "max", "mustermann", "picture", true, new Address("starße", "12344", "Konstanz", "Deutschland", true), [], [], [], [], []);
            this.user = this.getUser();
            this.articleRating = this.getAvgStars(this.article.ratings);
            this.userRating = this.user != null ? this.getAvgStars(this.user.ratings) : 0;
            if (this.user == null) {

            }
        }

        getUser(): User | null {
            let user: User | null = null;

            $.ajax({
                url: "http://localhost:9000/user/find/" + this.article.userId,
                type: "GET",
                success: result => {
                    this.user = result;
                    user = result;
                    console.log("success ", result);
                },
                error: error => {
                    console.log("error ", error)
                }
            })
                .then(() => {
                    this.articleRating = this.getAvgStars(this.article.ratings);
                    this.userRating = this.user ? this.getAvgStars(this.user.ratings) : 0;
                    }
                )
            return user;
        }

        getAvgStars(ratings: Rating[]): number {
            if(!ratings || ratings.length === 0) {
                return 0
            }
            let starSum: number = 0;

            for (let rating of ratings) {
                starSum += rating.amountOfStars;
            }
            return starSum / ratings.length;
        }

        routeToUserPage(): void {
            if(this.user != null) {
                localStorage.setItem("selectedArticle", JSON.stringify(this.article))
            }
            this.$router.push({name: 'user', params: {user: this.user}});
        }

        getDate(): string {
            return moment(this.article.insertionDate).format(" DD MMMM YYYY")
        }
    }

</script>

<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    .carousel {
        width: 38vw;
    }

    .user {
        border-width: 1px;
    }

    .category {
        margin-top: 10px;
        border-style: solid;
        border-width: 1px;
        padding: 5px;
        display: inline-block;
        margin-right: 10px;
        float: left;
        border-radius: 3px;
    }

    .bcol {
        padding-left: 1px;
        padding-right: 1px;
    }

    .section {
        clear: both;
        text-align: left;
        margin-top: 30px;
    }

    .description-section {
        margin-top: 70px;
    }

    .user-col {
        clear: both;
        text-align: left;
    }

    .headline {
        display: inline-block;
        margin-right: 10px;
    }

    .article-info {
        width: 38vw;
        border: 2px solid #ced4da;
        border-radius: 3px;
        margin-top: 1vw;
        padding: 0 1vw 1vw;
        clear: both;
        float: left;
        text-align: left;
    }

    .rating {
        width: 120px;
        display: inline-flex !important;
    }

    .user-details {
        margin-top: 20px;
        padding-right: 0;
    }

    .clip-circle {
        width: 5.7vw;
        height: 5.7vw;
        border-radius: 50%;
        margin-top: 15px;
    }

    .address {
        width: 300px;
    }
    
    .alert {
        width: 100%;
        text-align: left;
    }
    
    .address {
        width: 300px;
    }
</style>
