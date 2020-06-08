<template>
    <div>
        <NavigationBar msg="hallo" with-content-layout="true"></NavigationBar>
        <FilterPanel></FilterPanel>
        <div class="contentLayout">
            <div class="articleView">

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
                <b-pagination
                        class="customPagination"


                        v-model="currentPage"
                        :total-rows="rows()"
                        :per-page="perPage"
                        aria-controls="cards"
                >
                </b-pagination>

            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import Article from "@/components/article/Article.ts";
    import ArticleCard from "@/components/article/ArticleCard.vue";
    import NavigationBar from "@/components/NavigationBar.vue";
    import FilterPanel from "@/components/filter/FilterPanel.vue";

    @Component({
        components: {FilterPanel, ArticleCard, NavigationBar}
    })
    export default class ArticleView extends Vue {
        articles: Article[] = [];
        perPage = 5;
        currentPage = 1;

        constructor() {
            super();
            let article: Article = new Article("Beerpong table", "Awesome beerpong table", "", "Konstanz", new Date(), []);
            let article2: Article = new Article("Bohrmaschine", "Toll zum bohren", "", "Konstanz", new Date(), []);
            this.articles.push(article);
            this.articles.push(article2);
            this.articles.push(article2);
            this.articles.push(article2);
            this.articles.push(article2);
            this.articles.push(article);
        }

        lists(): Article[] {
            const items = this.articles;
            return items.slice(
                (this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage
            )
        }

        rows(): number {
            return this.articles.length
        }
    }
</script>

<style>

    .page-item.active .page-link {
        border-color: #d0f2e1;
    }

    .customPagination {
        border: #d0f2e1 !important;
        display: inline-flex;
    }

    .customPagination > li.active > button,
    .customPagination > li > button:hover {
        color: black;
        background-color: #484848 !important;
        border-color: #d0f2e1;
    }

    .page-link {
        color: black;
    }

    .articleView {
        margin-top: 5px;
    }

</style>