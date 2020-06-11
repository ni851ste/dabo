<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from './components/HelloWorld.vue';

@Component({
  components: {
    HelloWorld,
  },
})
export default class App extends Vue {}
</script>

<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #484848;
    margin-top: 60px;
    margin-bottom: 15px;
  }

  .contentLayout {
    margin-left: 19vw;
    margin-right: 20vw;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    margin-top: 75px;
  }

</style>
