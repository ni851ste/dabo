<template>
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Ausleihen</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p>Wie lange möchtest du den Artikel leihen?</p>
                    <b-form-datepicker
                            id="toDatepicker" v-model="maxDate" :min="minDate" locale="de"
                            placeholder="bis...">

                    </b-form-datepicker>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" v-on:click="borrowArticle">Ausleihen</button>
                </div>
            </div>
        </div>
</template>

<script lang="ts">
    import Article from "@/components/article/Article";
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import $ from "jquery";

    @Component
    export default class BorrowArticleView extends Vue {
        @Prop() private article!: Article;
        @Prop() private currentUserId!: string;
        minDate: Date = new Date();
        maxDate: any = null;



        borrowArticle(): boolean {
            $.ajax({
                url: "http://localhost:9000/user/borrow",
                type: "POST",
                data: JSON.stringify({
                    articleId: this.article.articleId,
                    // TODO This is the std user hash from my postman, as placeholder
                    // TODO for when I know how to get the user Id
                    borrower: "-1743314910",
                    until: this.maxDate
                }),
                dataType: "json",
                contentType: "application/json",
                success: result => {
                    console.log("success ", result)
                },
                error: error => {
                    console.log("error ", error)
                }
            });
            return true
        }
    }


</script>

<style scoped>

</style>