<template>
    <div>
        <NavigationBar msg="hallo" with-content-layout="true"></NavigationBar>
        <FilterPanel :category="this.category" @filteredArticles="items => articles = items"></FilterPanel>
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
    import {Component, Prop, Vue} from "vue-property-decorator";
    import Article from "@/components/article/Article.ts";
    import ArticleCard from "@/components/article/ArticleCard.vue";
    import NavigationBar from "@/components/NavigationBar.vue";
    import FilterPanel from "@/components/filter/FilterPanel.vue";
    import _ from 'lodash';
    import category from "@/components/category/Category";

    @Component({
        components: {FilterPanel, ArticleCard, NavigationBar}
    })

    export default class ArticleView extends Vue {
        @Prop() articles!: Article[]
        @Prop() category!: category
        perPage = 5;
        currentPage = 1;

        lists(): Article[] {
            const items : Article[] = _.cloneDeep(this.articles);
            return _.slice(items,
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
