<template>
    <div class="card">
        <b-button class="card-body" v-on:click="routeToArticleView()">
            <img class="card-img-top" :src="require(`@/assets/categoryImgs/${img}`)" alt="Card image cap">
            <h5 class="categoryLabel">
                {{category}}
            </h5>
        </b-button>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import $ from "jquery";
    import Article from "@/components/article/Article";
    import category from "@/components/category/Category";

    @Component
    export default class CategoryCard extends Vue {
        @Prop() readonly category!: string;
        @Prop() readonly img!: string;

        constructor() {
            super();
            this.clearLocalStorageFilters()
        }

        clearLocalStorageFilters(): void {
            localStorage.removeItem("searchString");
            localStorage.removeItem("location");
            localStorage.removeItem("ratingValue");
            localStorage.removeItem("categories");
        }

        routeToArticleView() {
            let articles: Article[] = [];
            $.ajax({
                url: "http://localhost:9000/users/articles",
                type: "POST",
                data: JSON.stringify({
                    categories: [this.category],
                }),
                dataType: "json",
                contentType: "application/json",
                success: result => {
                    for (let i = 0; i < result.length; i++) {
                        articles[i] = new Article(result[i].name,
                            result[i].description,
                            result[i].images,
                            result[i].location,
                            new Date(result[i].insertionDate),
                            result[i].categories,
                            //TODO: waiting for backend support
                            result[i].ratings,
                            result[i].userId,
                            result[i].id
                        );
                    }
                    //code is working, IntelliJ is just fooling around
                    this.$router.push({name: 'articles', params: {articles: articles, category: this.category}});

                },
                error: error => {
                    console.log("error ", error)
                }
            });
        }
    }
</script>

<style scoped>
    .card {
        margin: 15px 7.5px 0 7.5px;
    }

    .categoryLabel {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        color: white;
        font-weight: bold;
        font-size: 30px;
    }

    .card-img-top {
        object-fit: cover;
        height: 250px;
        width: 400px;
        filter: brightness(70%);
        border-radius: calc(.25rem - 1px);
    }

    .card-body {
        padding: 0;
        opacity: 1;
        transition: .5s ease;
        border: none;
    }

    .card-body:hover {
        opacity: 0.7;
    }

</style>
