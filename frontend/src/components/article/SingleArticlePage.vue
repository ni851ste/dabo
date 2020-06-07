<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <div class="contentLayout">
            <b-container fluid class="bv-example-row">
                <b-row>
                    <b-col cols="8" class="article-col">
                        <div id="carouselExampleIndicators" class="carousel slide img-slide"
                             data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/haushalt.jpg`)"
                                         alt="First slide">
                                </div>
                                <div class="carousel-item">
                                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/haushalt.jpg`)"
                                         alt="Second slide">
                                </div>
                                <div class="carousel-item">
                                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/haushalt.jpg`)"
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
                        <div class="header">
                            <h3 class="headline" v-html="article.name"></h3>
                            <b-form-rating class="rating" variant="warning" readonly v-model="value"></b-form-rating>
                            <!--TODO: dynamic categories-->
                            <div class="categories">
                                <div class="category">
                                    Haushalt
                                </div>
                                <div class="category">
                                    Garten
                                </div>
                            </div>
                            <p class="description-section section" v-html="article.description"></p>
<!--                            <div class="section">-->
                            <!--                    Verfügbarkeit-->
<!--                            </div>-->
                        </div>

                    </b-col>
                    <b-col cols="4" class="user-col">
                            <b-row class="user">
                                <b-col cols="4">
                                    <img class="clip-circle" :src="require(`@/assets/categoryImgs/haushalt.jpg`)" alt="">
                                </b-col>
                                <b-col align-self="stretch" class="user-details" cols="5">
                                    Max Mustermann
                                    <b-form-rating class="rating" variant="warning" readonly v-model="value"></b-form-rating>
                                    <p class="section">
                                        Adresse:
                                    <p v-html="article.location">
                                    </p>
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
    // import $ from "jquery";

    @Component({
        components: {NavigationBar}
    })
    export default class SingleArticlePage extends Vue {
        //TODO: User prop
        @Prop() private article: Article = new Article("Staubsauger", "Cooler Staubsauger saugt Staub.</br>Nur zum Abholen.", "", "Musterstaße 10</br>78462 Konstanz", new Date(), []);
        private value: number = 3.5555

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
        /*margin-top: 70px;*/
        /*border-style: solid;*/
        /*border-left: solid;*/
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

    .header {
        clear: both;
        float: left;
        text-align: left;
    }

    .rating {
        width: 120px;
        display: inline-flex !important;
        /*border: none;*/
    }

    .user-details {
        margin-top: 20px;
        padding-right: 0;
    }

    .clip-circle {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        margin-top: 15px;
    }
</style>