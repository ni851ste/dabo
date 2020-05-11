<template>
    <div>
        <nav class="navbar navbar-expand-lg ">
            <a class="navbar-brand" href="#">dabo</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item ">
                        <a class="nav-link" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Angebote</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Gesuche</a>
                    </li>
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
