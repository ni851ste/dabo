<template>
    <div class="row categoryView">
        <CategoryCard v-for="item in categories" :category="item.category" :img="item.img"></CategoryCard>
    </div>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator';
    import CategoryCard from "@/components/category/CategoryCard.vue";
    @Component({
        components: {CategoryCard}
    })
    export default class CategoryView extends Vue {
         categories = [
             {category: "Haushalt", img: "haushalt.jpg"},
             {category: "Garten", img: "garten.jpg"},
             {category: "Werkzeug", img: "werkzeug.jpg"},
             {category: "Unterhaltung", img: "unterhaltung.jpg"},
             {category: "Elektronik", img: "elektronik.jpg"},
             {category: "Sport & Freizeit", img: "sport&freizeit.jpg"},
             {category: "Sonstiges", img: "sonstiges.jpg"},
             {category: "Auto & Motorrad", img: "auto&motorrad.jpg"},
             {category: "Kleidung", img: "kleider.jpg"},
         ]
    }
</script>

<style scoped>
    .categoryView {
        margin-left: 7vw;
        margin-right: 7vw;
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        margin-bottom: 15px;
    }
</style>
