
<template>
    <div class="card">
        <router-link to="/articles">
            <div class="card-body">
                <img class="card-img-top" :src="require(`@/assets/categoryImgs/${img}`)" alt="Card image cap">
                <h5 class="categoryLabel">
                    {{category}}
                </h5>
            </div>
        </router-link>
    </div>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator';

    @Component
    export default class CategoryCard extends Vue {
        @Prop() readonly category!: string;
        @Prop() readonly img!: string
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
    }
</style>
