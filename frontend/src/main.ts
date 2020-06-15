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
import RegistrationPage from "@/components/user/RegistrationPage.vue";
import UserProfileView from "@/components/user/UserProfileView.vue";

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
const routes = [
  {path: '/user', component: UserProfileView},
  {path: '/categories', component: CategoryPage },
  {path: '/articlePage/:article', name: 'articlePage', component: SingleArticlePage, props: true },
  {path: '/articles', name: 'articles', component: ArticleView, props: true },
  {path: '/createArticle', component: ArticleCreationView},
  {path: '/home', component: StartPage},
  {path: '/', component: StartPage},
  {path: '/login', component: RegistrationPage}
];
const router = new VueRouter({
  routes: routes
});
new Vue({
  render: h => h(App),
  router: router
}).$mount('#app');
