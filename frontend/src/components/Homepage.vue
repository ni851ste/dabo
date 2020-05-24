<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <CategoryView></CategoryView>


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
