<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <h3>Henlo</h3>
        <!--        <img v-bind:src="'@/assets/categoryImgs/haushalt'" />-->
        <div id="carouselExampleIndicators" class="carousel slide img-slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/haushalt.jpg`)" alt="First slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/haushalt.jpg`)" alt="Second slide">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" :src="require(`@/assets/categoryImgs/haushalt.jpg`)" alt="Third slide">
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    // import $ from "jquery";

    @Component({
        components: {NavigationBar}
    })
    export default class SingleArticlePage extends Vue {
    }
    // $('.carousel').carousel('pause')
</script>

<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    .img-slide {
        width: 500px;
        margin: auto;
    }

    /*.carousel-inner {*/
    /*    width: 500px*/
    /*}*/
</style>