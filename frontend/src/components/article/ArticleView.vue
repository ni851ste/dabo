<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <div class="articleView">
            <ArticleCard v-for="article in articles" :article="article"></ArticleCard>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import Article from "@/components/article/Article.ts";
    import ArticleCard from "@/components/article/ArticleCard.vue";
    import NavigationBar from "@/components/NavigationBar.vue";
    @Component({
        components: {ArticleCard, NavigationBar}
    })
    export default class ArticleView extends Vue {
        articles: Article[] = [];

        constructor() {
            super();
            let article: Article = new Article("Beerpong table", "Awesome beerpong table", "", "Konstanz", new Date());
            let article2: Article = new Article("Bohrmaschine", "Toll zum bohren", "", "Konstanz", new Date());
            this.articles.push(article)
            this.articles.push(article2)
        }
    }
</script>

<style scoped>
    .articleView {
        margin-top: 75px;
    }
</style>