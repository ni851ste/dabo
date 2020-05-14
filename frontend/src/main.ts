import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import HelloWorld from "@/components/HelloWorld.vue";

Vue.config.productionTip = false

Vue.use(VueRouter);
const routes = [
  {path: '/', component: HelloWorld },
];
const router = new VueRouter({
  routes: routes
});
new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
