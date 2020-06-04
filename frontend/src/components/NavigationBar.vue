<template>
    <div>
        <nav class="navbar navbar-expand-lg ">
            <RouterLink to="/">
                <a class="navbar-brand logo-a" href="#">
                    <img class="logo" :src="require(`@/assets/logoImgs/dabo_logo_quadrat.png`)" height="40" alt="">
                </a>
            </RouterLink>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav navBarLeft">
                    <RouterLink to="/">
                        <li class="nav-item ">
                            <a class="nav-link" href="#">Angebote</a>
                        </li>
                    </RouterLink>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Gesuche</a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <RouterLink to="/createArticle">
                        <b-button class="nav-item addArticle">
                            <a class="nav-link disabled" href="#">Artikel hinzufügen</a>
                        </b-button>
                    </RouterLink>
                    <RouterLink to="/">
                        <li class="nav-item login">
                            <a class="nav-link disabled" href="#">Login</a>
                        </li>
                    </RouterLink>
                </ul>
            </div>
        </nav>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class NavigationBar extends Vue {
        @Prop() withContentLayout!: boolean;
    }
</script>

<style scoped>
    .navbar-brand {
        color: #d0f2e1;
        font-weight: bold;
        font-size: xx-large;
        padding: 0;
    }

    .navbar {
        background-color: #484848;
        padding: 5px;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 100;
        height: 60px !important;
    }

    .login {
        float: right;
        margin-right: 10px;
    }

    .logo {
        top: 0;
        bottom: 0;
        margin: auto;
    }

    .logo-a {
        height: 50px;
        margin-left: calc(2vw - 5px);
    }

    .addArticle {
        float: right;
        background-color: #d0f2e1;
        padding: 0;
        right: 20vw;
        margin-right: 15px;
    }

    .addArticle:hover > a {
        color: #d0f2e1;
        border: #d0f2e1 solid;
        border-radius: 3px;
    }

    .addArticle {
        float: right;
        background-color: #d0f2e1;
        padding: 0;
        right: 20vw;
        margin-right: 10px;
    }

    .addArticle:hover > a {
        color: #d0f2e1;
        border: #d0f2e1 solid;
        border-radius: 3px;
    }

    a {
        color: white;
    }

    .navBarLeft {
        margin-left: 10px;
    }
</style>
