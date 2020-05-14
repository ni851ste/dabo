<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <CategoryView></CategoryView>
<!--        {{msg}}-->
<!--        Homepage-->

<!--        <router-link to="/">-->
<!--            <button type="button" class="btn btn-dark " >-->
<!--                Test-->
<!--            </button>-->
<!--        </router-link>-->

    </div>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import CategoryView from "@/components/category/CategoryView.vue";
    @Component({
        components: {CategoryView, NavigationBar}
    })
    export default class HomePage extends Vue {
        @Prop() private msg!: string;
    }
</script>

<style scoped>
</style>
