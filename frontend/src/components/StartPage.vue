<template>
    <div class="body">
        <NavigationBar msg="hallo"></NavigationBar>
        <b-img class="logo" :src="require(`@/assets/logoImgs/dabo_logo_quadrat.png`)" fluid
               alt="Responsive image"></b-img>
        <div class="transparent-box">
            <div class="box-content">
                <h4>Finde alles was du brauchst</h4>
                <div class="search-line">
                    <div class="md-form mt-0 input search-input">
                        <input class="form-control" type="text" placeholder="Was suchst du?" aria-label="Search">
                    </div>
                    <div class="input">
                        <b-form-input placeholder="Stadt"></b-form-input>
                    </div>
                    <div class="form-group input button">
                        <select class="form-control input" id="class" name="class">
                            <option class="dropdown-item" value="angebote">Angebote</option>
                            <option class="dropdown-item" value="gesuche">Gesuche</option>
                        </select>
                    </div>
                    <b-button class="input button" variant="info">Finden</b-button>
                </div>
            </div>
        </div>
        <div class="transparent-box">
            <p class="text-p">
                Lorem Ipsum is simply dummy text of the printing and typesetting industry.<br/>
                Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, <br/>
                when an unknown printer took a galley of type and scrambled it to make a type specimen book
            </p>
        </div>
        <br/>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import $ from "jquery";

    @Component({
        components: {NavigationBar}
    })
    export default class StartPage extends Vue {
    }

</script>

<style scoped>

    h4 {
        float: left;
        margin-bottom: 1vw;
        margin-top: 0.5vw;
        font-weight: bold;
    }

    .logo {
        height: 15vw;
        margin-top: 2vw;
        margin-bottom: 2vw;
    }

    .text-p {
        color: #484848;
        margin-bottom: 0;
    }

    .body {
        background-image: url('../assets/startpageImgs/bicycle.jpg');
        position: fixed;
        min-width: 100%;
        min-height: 100%;
    }

    .transparent-box {
        width: 70vw;
        background: rgba(255, 255, 255, 0.6);
        margin: auto;
        border-radius: 3px;
        padding: 1vw;
        margin-bottom: 2vw;
    }

    .box-content {
        display: table;
        margin: 0 auto;
    }

    .search-line {
        clear: both;
        float: left;
    }

    .input {
        display: inline-block;
        background: rgba(208, 242, 225, 1);
        margin-right: 1vw;
        color: #484848;
        border-color: #d0f2e1;
        border-radius: 3px;
    }

    .search-input {
        width: 20vw;
    }

    .button {
        width: 10vw;
        /*TODO same height in line*/
        margin-bottom: 0.2vw;
    }

    .btn:hover {
        color: #484848;
        background-color: #badbca;
        border-color: #badbca;
        text-decoration: none;
    }

    .btn-info:hover {
        color: #484848;
        background-color: #badbca;
        border-color: #badbca;
    }

    b-img {
        pointer-events: none;
        position: absolute;
        width: 100%;
        height: 100%;
        z-index: -1;
    }

    .dropdown {
        position: relative;
        width: 10.5vw;
    }

    .dropdown-item {
        color: #484848;
        background-color: #d0f2e1;
        width: 10.5vw;
    }


</style>