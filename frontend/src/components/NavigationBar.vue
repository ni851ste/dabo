<template>
    <div>
        <nav class="navbar navbar-expand-lg ">
            <RouterLink to="/">
                <a class="navbar-brand logo-a" href="#">
                    <img class="logo" :src="require(`@/assets/logoImgs/dabo_logo_quadrat.png`)" alt="">
                </a>
            </RouterLink>

<!--            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"-->
<!--                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">-->
<!--                <span class="navbar-toggler-icon"></span>-->
<!--            </button>-->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav navBarLeft">
                    <RouterLink to="/categories">
                        <li class="nav-item ">
                            <a class="nav-link" href="#">Angebote</a>
                        </li>
                    </RouterLink>
<!--                    <li class="nav-item">-->
<!--                        <a class="nav-link" href="#">Gesuche</a>-->
<!--                    </li>-->
                </ul>
                <ul class="navbar-nav ml-auto">
                    <b-button class="nav-item addArticle" v-on:click="onCreateArticleClick">
                        <a class="nav-link disabled" href="#">Artikel hinzufügen</a>
                    </b-button>
                    <RouterLink v-if="!loginService.isLoggedIn()" to="/login">
                        <li class="nav-item login">
                            <a class="nav-link" href="#">Login</a>
                        </li>
                    </RouterLink>
                    <b-dropdown v-else id="dropdown-1" class="nav-item login" :text="loginService.loggedInUser.firstName">
                        <b-dropdown-item v-on:click="routeToUserProfile">Profil</b-dropdown-item>
                        <b-dropdown-item v-on:click="loginService.logout">Logout</b-dropdown-item>
                    </b-dropdown>
                </ul>
            </div>
        </nav>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import LoginService from "@/components/services/LoginService";

    @Component
    export default class NavigationBar extends Vue {
        @Prop() withContentLayout!: boolean;
        loginService: LoginService = LoginService.getInstance()
        constructor() {
            super();
        }

        routeToUserProfile(): void {
            console.log("loggedin User is ",this.loginService.loggedInUser)
            this.$router.push({name: 'user', params: {user: this.loginService.loggedInUser}});
        }

        onCreateArticleClick(): void {
            if(!this.loginService.loggedInUser) {
                this.$router.push({name: 'login', params: {showLoginFirstAlert: true}});
            } else {
                this.$router.push({name: 'createArticle'});
            }
        }
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
        margin-bottom: 4px;
        height: 42px;
    }

    .logo-a {
        height: 50px;
        margin-left: calc(2vw - 5px);
    }

    .addArticle {
        float: right;
        padding: 0;
        right: 20vw;
        margin-right: 15px;

        color: #484848;
        background: #d0f2e1;
        border-color: #d0f2e1;
        border-radius: 3px;
    }

    .addArticle:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
        border-radius: 3px;
    }

    a {
        color: white;
    }

    a:hover {
        color: #d0f2e1;
        text-decoration: none;
    }

    .navBarLeft {
        margin-left: 10px;
    }
</style>
