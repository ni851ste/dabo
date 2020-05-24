<template>
    <div>
        <nav class="navbar navbar-expand-lg ">
            <a class="navbar-brand" href="#">dabo</a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav row">
                    <RouterLink to="/">
                        <li class="nav-item ">
                            <a class="nav-link" href="#">Home</a>
                        </li>
                    </RouterLink>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Angebote</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Gesuche</a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item login">
                        <a class="nav-link disabled" href="#">Login</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator';

    @Component
    export default class NavigationBar extends Vue {
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
    }

    .login {
        float: right;
    }

    a {
        color: white;
    }
</style>
