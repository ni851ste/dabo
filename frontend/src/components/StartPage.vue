<template>
    <div class="body">
        <NavigationBar msg="hallo"></NavigationBar>
        <b-alert v-model="showAlert" class="alert" variant="success" dismissible>Login erfolgreich.</b-alert>
        <b-img class="logo" :src="require(`@/assets/logoImgs/dabo_logo_quadrat.png`)" fluid
               alt="Responsive image"></b-img>
        <div class="transparent-box">
            <div class="box-content">
                <h4>Finde alles was du brauchst</h4>
                <div class="search-line">
                    <div class="md-form mt-0 input search-input">
                        <input class="form-control" type="text" placeholder="Was suchst du?" aria-label="Search">
                    </div>
                    <div class="input">
                        <b-form-input placeholder="Stadt"></b-form-input>
                    </div>
<!--                    <div class="form-group input button">-->
<!--                        <select class="form-control input dropdown" id="class" name="class">-->
<!--                            <option class="dropdown-item" value="angebote">Angebote</option>-->
<!--                            <option class="dropdown-item" value="gesuche">Gesuche</option>-->
<!--                        </select>-->
<!--                    </div>-->
                    <b-button class="input button" variant="info" v-on:click="routeToArticleView()">Finden</b-button>
                </div>
            </div>
        </div>
        <div class="transparent-box">
            <p class="text-p">
                Du sucht etwas Bestimmtes aber möchtest es nicht kaufen? Dann bist du hier genau richtig!<br/>
                Dabo ist eine Online-Plattform auf der Du Gegenstände mit Niveau zum Aus- und Verleihen in Deiner Nähe
                finden kannst.<br/>
                Denn Du weißt, bei uns gilt: Sharing is caring!
            </p>
        </div>
        <br/>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import $ from "jquery";
    import Article from "@/components/article/Article";
    import category from "@/components/category/Category";

    @Component({
        components: {NavigationBar}
    })
    export default class StartPage extends Vue {
        @Prop({
            required: false,
            default: false
        }) private showAlert!: boolean;

        routeToArticleView() {
            this.clearLocalStorageFilters();

            let articles: Article[] = [];
            $.ajax({
                url: "http://localhost:9000/users/articles",
                type: "POST",
                data: JSON.stringify({
                    categories: ["Werkzeug"]
                }),
                dataType: "json",
                contentType: "application/json",
                success: result => {
                    console.log(result);
                    for (let i = 0; i < result.length; i++) {
                        articles.push(new Article(result[i].name,
                            result[i].description,
                            result[i].images,
                            result[i].location,
                            new Date(result[i].insertionDate),
                            result[i].categories,
                            //TODO: waiting for backend support
                            [],
                            result[i].userId
                        ))
                        // articles[i] = new Article(result[i].name,
                        //     result[i].description,
                        //     result[i].images,
                        //     result[i].location,
                        //     new Date(result[i].insertionDate),
                        //     result[i].category,
                        //     //TODO: waiting for backend support
                        //     [],
                        //     result[i].userId
                        // );
                        // console.log("for in StartPage " + articles[i]);
                    }
                    console.log("articles in StartPage " + articles);
                    //code is working, IntelliJ is just fooling around
                    this.$router.push({name: 'articles', params: {articles: articles}});

                },
                error: error => {
                    console.log("error ", error)
                }
            });
        }

        clearLocalStorageFilters(): void {
            localStorage.removeItem("searchString");
            localStorage.removeItem("location");
            localStorage.removeItem("ratingValue");
            localStorage.removeItem("categories");
        }
    }

</script>

<style scoped>

    h4 {
        float: left;
        margin-bottom: 1vw;
        margin-top: 0.5vw;
        font-weight: bold;
    }

    .logo {
        height: 15vw;
        margin-top: 2vw;
        margin-bottom: 2vw;
    }

    .text-p {
        color: #484848;
        margin-bottom: 0;
    }

    .body {
        background-image: url('../assets/startpageImgs/bicycle.jpg');
        position: fixed;
        min-width: 100%;
        min-height: 100%;
    }

    .transparent-box {
        width: 70vw;
        background: rgba(255, 255, 255, 0.6);
        margin: auto;
        border-radius: 3px;
        padding: 1vw;
        margin-bottom: 2vw;
    }

    .box-content {
        display: table;
        margin: 0 auto;
    }

    .search-line {
        clear: both;
        float: left;
    }

    .input {
        display: inline-block;
        background: rgba(208, 242, 225, 1);
        margin-right: 1vw;
        color: #484848;
        border-color: #d0f2e1;
        border-radius: 3px;
    }

    .search-input {
        width: 20vw;
    }

    .button {
        width: 10vw;
        /*TODO same height in line*/
        margin-bottom: 0.2vw;
    }

    .button:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
    }

    b-img {
        pointer-events: none;
        position: absolute;
        width: 100%;
        height: 100%;
        z-index: -1;
    }

    .dropdown-item {
        color: #484848;
        background-color: #d0f2e1;
        width: 10.5vw;
    }

    .dropdown:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
    }

    .alert {
        width: 70vw;
        text-align: left;
        margin: auto;
        margin-top: 2vw;
    }

</style>