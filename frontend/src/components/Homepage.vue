<template>
    <div>
        {{msg}}
        Homepage


    </div>
</template>

<script lang="ts">
    import { Component, Prop, Vue } from 'vue-property-decorator';

    @Component
    export default class HomePage extends Vue {
        @Prop() private msg!: string;
    }
</script>

<style scoped>
</style>
