import Vue from 'vue'
import "bootstrap-vue"
import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"
import App from './App.vue'
import VueRouter from "vue-router";
import Homepage from "@/components/Homepage.vue";
import HelloWorld from "@/components/HelloWorld.vue";

Vue.config.productionTip = false

Vue.use(VueRouter);
const routes = [
  {path: '/', component: Homepage },
  {path: '/helloworld', component: HelloWorld },
];
const router = new VueRouter({
  routes: routes
});
new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
