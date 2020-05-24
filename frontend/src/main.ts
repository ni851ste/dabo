import Vue from 'vue'
import "bootstrap-vue"
import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"
import ArticleView from "@/components/article/ArticleView.vue";
import BootstrapVue, {BootstrapVueIcons} from "bootstrap-vue";
import ArticleCreationView from "@/components/article/ArticleCreationView.vue";
import App from './App.vue'
import VueRouter from "vue-router";
import Homepage from "@/components/Homepage.vue";

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
const routes = [
  {path: '/', component: Homepage },
  {path: '/articles', component: ArticleView },
  {path: '/createArticle', component: ArticleCreationView}
];
const router = new VueRouter({
  routes: routes
});
new Vue({
  render: h => h(App),
  router: router
}).$mount('#app');
