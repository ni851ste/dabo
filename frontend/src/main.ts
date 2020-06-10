import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import CategoryPage from "@/components/CategoryPage.vue";
import SingleArticlePage from "@/components/article/SingleArticlePage.vue";
import StartPage from "@/components/StartPage.vue";
import "bootstrap-vue"
import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"
import ArticleView from "@/components/article/ArticleView.vue";
import ArticleCreationView from "@/components/article/ArticleCreationView.vue";
import BootstrapVue, {BootstrapVueIcons} from "bootstrap-vue";

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
const routes = [
  {path: '/categories', component: CategoryPage },
  {path: '/articlePage/:article', name: 'articlePage', component: SingleArticlePage, props: true },
  {path: '/articles', component: ArticleView },
  {path: '/createArticle', component: ArticleCreationView},
  {path: '/home', component: StartPage},
  {path: '/', component: StartPage}
];
const router = new VueRouter({
  routes: routes
});
new Vue({
  render: h => h(App),
  router: router
}).$mount('#app');
