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
                    <p>Bis wann möchtest du anfragen?</p>
                    <b-form-datepicker
                            id="toDatepicker" v-model="maxDate" :min="minDate" locale="de"
                            placeholder="bis...">

                    </b-form-datepicker>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary request-article" data-dismiss="modal" v-on:click="borrowArticle">Artikel anfragen</button>
                </div>
            </div>
        </div>
</template>

<script lang="ts">
    import Article from "@/components/article/Article";
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import $ from "jquery";
    import LoginService from "@/components/services/LoginService";

    @Component
    export default class BorrowArticleView extends Vue {
        @Prop() private article!: Article;
        @Prop() private currentUserId!: string;
        minDate: Date = new Date();
        maxDate: any = null;

        loginService: LoginService = LoginService.getInstance();



        borrowArticle(): boolean {
            $.ajax({
                url: "http://localhost:9000/user/request-borrow",
                type: "POST",
                data: JSON.stringify({
                    articleId: this.article.articleId,
                    borrower: this.loginService.loggedInUser?.id,
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
    .request-article {
        float: right;

        color: #484848;
        background: #d0f2e1;
        border-color: #d0f2e1;
        border-radius: 3px;
    }

    .request-article:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
        border-radius: 3px;
    }

</style>