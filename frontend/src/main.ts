import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import Homepage from "@/components/Homepage.vue";
import SingleArticlePage from "@/components/article/SingleArticlePage.vue";
import "bootstrap-vue"
import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"

Vue.config.productionTip = false

Vue.use(VueRouter);
const routes = [
  {path: '/', component: Homepage },
  {path: '/sap', component: SingleArticlePage }
];
const router = new VueRouter({
  routes: routes
});
new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
