<template>
    <div class="col-sm-4">
        <div class="card">
            <div class="card-body">
                <img class="card-img-top" :src="require(`@/assets/categoryImgs/${img}`)" alt="Card image cap">
                <h5 class="categoryLabel">
                    {{category}}
                </h5>
            </div>
        </div>
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
    .categoryLabel {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        color: white;
        font-weight: bold;
        font-size: 30px;
    }

    .col-sm-4 {
        padding-bottom: 15px;
        padding-top: 15px;
    }

    .card-img-top {
        height: 250px;
        width: 400px;
        filter: brightness(70%);
    }

    .card-body {
        padding: 0;
    }
</style>
