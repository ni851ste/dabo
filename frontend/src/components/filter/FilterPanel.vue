<template>
    <div class="filter">

        <div class="filterOption">
            <b-input-group>
                <b-input
                        id="searchInput"
                        v-model="searchString"
                        type="text"
                        required
                        placeholder="Suche"
                ></b-input>
            </b-input-group>

        </div>
        <hr/>
        <div class="filterOption">
            <label>
                <b-icon-geo-alt font-scale="1.2"></b-icon-geo-alt>
                Standort
            </label>
            <b-form-input
                    id="locationInput"
                    v-model="location"
                    type="text"
                    required
                    placeholder="Standort"
            ></b-form-input>
        </div>
        <hr/>
        <div class="filterOption">
            <label>
                Bewertung ab
            </label>
            <b-form-rating class="rating" variant="warning" v-model="value"></b-form-rating>
        </div>
        <hr/>
        <div class="categoryCheckboxes filterOption">
            <label>
                <b-icon-list-ul font-scale="1.2"></b-icon-list-ul>
                Kategorien
            </label>
            <b-form-group>
                <b-form-checkbox-group
                        v-on:click=""
                        id="category-checkbox-group"
                        v-model="selectedCategories"
                        :options="categories"
                >
                </b-form-checkbox-group>
            </b-form-group>
            <b-button class="applyFilter" v-on:click="getFilteredArticles()">
                <a class="nav-link disabled" href="#">
                    <b-icon-funnel font-scale="1.2"></b-icon-funnel>
                    Filter anwenden
                </a>
            </b-button>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import Category from "@/components/category/Category";
    import $ from "jquery";
    import Article from "@/components/article/Article";

    @Component
    export default class FilterPanel extends Vue {
        @Prop() category!: Category
        categories: Category[] = this.getCategories();
        value: Number = 1

        getCategories(): Category[] {
            return Object.values(Category)
        }

        location: string = "";
        searchString: string = "";

        selectedCategories: Category[] = [this.category]

        getFilteredArticles(): void {
            let articles: Article[] = [];

            $.ajax({
                url: "http://localhost:9000/users/articles",
                type: "POST",
                data: JSON.stringify({
                    categories: this.selectedCategories,
                }),
                dataType: "json",
                contentType: "application/json",
                success: result => {
                    console.log("success ", result);

                    for (let i = 0; i < result.length; i++) {
                        articles[i] = new Article(result[i].name,
                            result[i].description,
                            result[i].image,
                            result[i].location,
                            new Date(result[i].insertionDate),
                            result[i].category,
                            []
                        );
                    }
                    this.$emit('filteredArticles', articles)
                },
                error: error => {
                    console.log("error ", error)
                }
            });
        }
    }

</script>

<style>
    @media (max-width: 500px) {
        .filter {
            display: none !important;
        }
    }

    .filter {
        display: inline-block;
        width: 15vw;
        float: left;
        top: 0;
        left: 0;
        overflow: auto;
        min-width: 135px;
    }

    .categoryCheckboxes {
        margin-top: 15px;
    }

    .filterOption {
        margin-left: 2vw;
        margin-right: 2vw;
        margin-top: 5px;
        text-align: left;
    }

    hr {
        margin-left: 2vw;
    }

    .custom-control-input:checked ~ .custom-control-label::after {
        background-color: #d0f2e1;
        border-radius: 3px;
    }

    .applyFilter {
        background-color: #d0f2e1;
        padding: 0;
        margin-top: 15px;
        margin-bottom: 15px;
        border: none;
    }

    .applyFilter:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
        border-radius: 3px;
    }

    .rating {
        width: 120px;
        display: inline-flex !important;
        border: none;
        cursor: pointer;
    }

    :focus {
        outline: none !important;
    }

</style>
