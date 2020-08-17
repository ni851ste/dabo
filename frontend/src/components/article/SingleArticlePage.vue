<template>
    <div>
        <NavigationBar msg="hallo"></NavigationBar>
        <div class="contentLayout">
            <b-alert v-model="showAlert" class="alert" variant="success" dismissible>Ihr Artikel wurde erfolgreich erstellt.</b-alert>
            <b-container fluid class="bv-example-row">
                <b-row>
                    <b-col cols="8" class="article-col bcol">

                        <b-carousel
                                id="carousel-1"
                                v-model="slide"
                                :interval="4000"
                                controls
                                indicators
                                background="#ababab"
                                img-width="1024"
                                img-height="480"
                                style="text-shadow: 1px 1px 2px #333;"
                                @sliding-start="onSlideStart"
                                @sliding-end="onSlideEnd"
                        >
                            <div v-for="image in this.getArticleImages()">
                                <b-carousel-slide :img-src="image" class="slides"></b-carousel-slide>
                            </div>
                        </b-carousel>

                        <div class="article-info">
                            <h3 class="headline" v-html="article.name"></h3>
                            <b-form-rating class="rating" variant="warning" readonly
                                           v-model="articleRating"></b-form-rating>
                            <button v-if="this.userAuthorizedForChanges()" type="button" class="btn editArticleButton" data-toggle="modal" data-target="#editArticleModal">
                                Artikel bearbeiten
                                <EditArticleView></EditArticleView>
                            </button>

                            <EditArticleView :article="this.article"/>
                            

                            <button v-if="this.userAuthorizedForRatings()" type="button" class="btn editArticleButton" data-toggle="modal"
                                    data-target="#createRatingModal">
                                Artikel bewerten
                            </button>
                            <RatingCreationView :rating-object="this.article"/>


                            <div class="categories">
                                <div class="category" v-for="categoryLabel in this.article.categories">
                                    {{categoryLabel}}
                                </div>
                            </div>
                            <p class="description-section section" v-html="article.description"></p>
                            <!--                            <div class="section">-->
                            <!--                    Verfügbarkeit-->
                            <!--                            </div>-->
                        </div>
                    </b-col>
                    <b-col cols="4" class="user-col bcol">
                        <b-row class="user" v-on:click="routeToUserPage">
                            <b-col class="bcol" cols="4">
                                <img class="clip-circle" :src="this.getUserPicture()" alt="">
                            </b-col>
                            <b-col align-self="stretch bcol" class="user-details" cols="5">
                                    <p v-html="userName"></p>
                                <b-form-rating class="rating" variant="warning" readonly
                                               v-model="userRating"></b-form-rating>
                                <p class="section"></p>
                                Adresse:
                                <p class="address" v-html="userAddress"></p>
                                E-Mail:
                                <p v-html="this.user.email"></p>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";
    import Article from "@/components/article/Article";
    import moment from "moment";
    import Rating from "@/components/rating/Rating";
    import User from "@/components/user/User";
    import $ from "jquery";
    import EditArticleView from "@/components/article/EditArticleView.vue";
    import LoginService from "@/components/services/LoginService";
    import RatingCreationView from "@/components/rating/RatingCreationView.vue";


    @Component({
        components: {EditArticleView, RatingCreationView, NavigationBar}
    })
    export default class SingleArticlePage extends Vue {
        @Prop() private article!: Article;
        @Prop({
            required: false,
            default: false
        }) private showAlert!: boolean;

        user: User | null;
        articleRating: number = this.getAvgStars(this.article.ratings);
        userRating: number;

        loginService: LoginService = LoginService.getInstance();

        get userName(): string {
            if (!this.user)
                return "";

            if (this.user.lastNameVisible) {
                return this.user.firstName + " " + this.user.lastName;
            } else {
                return this.user.firstName
            }
        };

        get userAddress(): string{
            if (!this.user)
                return "";

            if (this.user.address.streetVisible) {
                return this.user.address.street + "<br/> " + this.user.address.plz + " "+ this.user.address.city
                    + "<br/> " + this.user.address.country;
            } else {
                return this.user.address.plz + " "+ this.user.address.city
                    + "<br/> " + this.user.address.country
            }
        };

        constructor() {
            super();
            if(!this.article || localStorage.getItem("selectedArticle") != null) {
                this.article = JSON.parse(localStorage.getItem("selectedArticle")!)
            }
            this.articleRating = 0
            this.userRating = 0
            this.getUser = this.getUser.bind(this)
            this.getAvgStars = this.getAvgStars.bind(this)
            // this.user = new User(0, "email", "password", "max", "mustermann", "picture", true, new Address("starße", "12344", "Konstanz", "Deutschland", true), [], [], [], [], []);
            this.user = this.getUser();
            this.articleRating = this.getAvgStars(this.article.ratings);
            this.userRating = this.user != null ? this.getAvgStars(this.user.ratings) : 0;
            if (this.user == null) {

            }
            console.log("SingleArticle:" + this.article.name)
        }

        getUser(): User | null {
            let user: User | null = null;

            $.ajax({
                url: "http://localhost:9000/user/find/" + this.article.userId,
                type: "GET",
                success: result => {
                    this.user = result;
                    user = result;
                    console.log("success gettingUser", result);
                },
                error: error => {
                    console.log("error ", error)
                }
            })
                .then(() => {
                    this.articleRating = this.getAvgStars(this.article.ratings);
                    this.userRating = this.user ? this.getAvgStars(this.user.ratings) : 0;
                    }
                )
            return user;
        }

        getAvgStars(ratings: Rating[]): number {
            if(!ratings || ratings.length === 0) {
                return 0
            }
            let starSum: number = 0;

            for (let rating of ratings) {
                starSum += rating.amountOfStars;
            }
            return starSum / ratings.length;
        }

        routeToUserPage(): void {
            if(this.user != null) {
                localStorage.setItem("selectedArticle", JSON.stringify(this.article))
            }
            this.$router.push({name: 'user', params: {user: this.user}});
        }

        getDate(): string {
            return moment(this.article.insertionDate).format(" DD MMMM YYYY")
        }

        userAuthorizedForChanges(): boolean {
            console.log("checking loggedinuser")
            if (!this.loginService.loggedInUser || !this.user) {
                return false;
            }
            return this.user.id === this.loginService.loggedInUser.id
        }

        userAuthorizedForRatings(): boolean {
            console.log("checking loggedinuser")
            if (!this.loginService.loggedInUser || !this.user) {
                return false;
            }
            return this.user.id !== this.loginService.loggedInUser.id
        }

        getUserPicture(): string {
            if (!this.user) {
                return "data:image/jpeg;base64,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mpqampqampqampqampqampqan+X/AP/Z"
            }
            if (!this.user.picture || this.user.picture === "") {
                return "data:image/jpeg;base64,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mpqampqampqampqampqampqan+X/AP/Z"
            }
            return this.user.picture;
        }

        getArticleImages(): string[] {
            if ((this.article.images === undefined) || (this.article.images.length < 1) || (this.article.images[0] === "")) {
                return ["data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD//gATQ3JlYXRlZCB3aXRoIEdJTVD/4gKwSUNDX1BST0ZJTEUAAQEAAAKgbGNtcwQwAABtbnRyUkdCIFhZWiAH5AAIAAoACgALAA9hY3NwTVNGVAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA9tYAAQAAAADTLWxjbXMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1kZXNjAAABIAAAAEBjcHJ0AAABYAAAADZ3dHB0AAABmAAAABRjaGFkAAABrAAAACxyWFlaAAAB2AAAABRiWFlaAAAB7AAAABRnWFlaAAACAAAAABRyVFJDAAACFAAAACBnVFJDAAACFAAAACBiVFJDAAACFAAAACBjaHJtAAACNAAAACRkbW5kAAACWAAAACRkbWRkAAACfAAAACRtbHVjAAAAAAAAAAEAAAAMZW5VUwAAACQAAAAcAEcASQBNAFAAIABiAHUAaQBsAHQALQBpAG4AIABzAFIARwBCbWx1YwAAAAAAAAABAAAADGVuVVMAAAAaAAAAHABQAHUAYgBsAGkAYwAgAEQAbwBtAGEAaQBuAABYWVogAAAAAAAA9tYAAQAAAADTLXNmMzIAAAAAAAEMQgAABd7///MlAAAHkwAA/ZD///uh///9ogAAA9wAAMBuWFlaIAAAAAAAAG+gAAA49QAAA5BYWVogAAAAAAAAJJ8AAA+EAAC2xFhZWiAAAAAAAABilwAAt4cAABjZcGFyYQAAAAAAAwAAAAJmZgAA8qcAAA1ZAAAT0AAACltjaHJtAAAAAAADAAAAAKPXAABUfAAATM0AAJmaAAAmZwAAD1xtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAAgAAAAcAEcASQBNAFBtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAAgAAAAcAHMAUgBHAEL/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAEzATMDAREAAhEBAxEB/8QAGwAAAwEBAQEBAAAAAAAAAAAAAAECAwUEBgf/xAAXAQEBAQEAAAAAAAAAAAAAAAAAAQID/9oADAMBAAIQAxAAAAH9I68djRaTaXvzWsNbhjAkAhjpxNZngs5JzrIWUki2UgUs1I5lBE0qUkx3ttjVaNY+gzrVUly0BQiQGOCmIzPBZxjwWRE1JFQJVJNqmUg0qkSQnaXazRdJdTvZ1vCAooYASAxiLiayPDZxLPDUCiKlUJIVISIKUspmvXs3rQ0l1O/nWsMQxlDACRgAyomsDw2cSzyVmImWSbESqsMwpRKQvUratV0jU7mb6JWAhlDGOCoABjKIPPXis4dnmMxEKoRNyKCFJJCdG3etZdDRe1L6sqAZIxlK5GFQADGMg89eOzhV50gmJJEFESBKQnQut60jQte1l6JdChAIZQ1YIEDAYDJPNXks4VeZmZZJEJBUiJWU91bNapoWvYzfVFFFAIAKGrRKkRQgAZJ568lnBrBIkRKqwEKWUk9du9bRa2djN9MWBY1ECRrSAKIAMkBgI89eJOFpikSIVKAkRFeo3a1S10Ovm+uAopaGiAlWjACgJGSMYyDzWeM4mmCSiUhIlkiz0LuupZodfN9MUMpWUEFiEAxjGIQCGMCDz14rOLWKQOEIkk0t9BoWaHXzfTFFDGoWgqRCAoYwEIAGAhHms8dcWsQQWUlILa3NDQ0Otm+qAooCgKBUIEBlAAhDAAAk89eKzjVmIRJLI1uaGhZ1s31QxjKAoRQQWqRUxjABCGAABJhXis5FkRNRE2S1uaFmh1c30xQxjKAomLolLJABjGAhAADEI89nirlJnUkGS+g0Sls6mb64ooYDGMcIpSwJAYxgAhDAAAk8tnhOdZnUHnXc1KLNDpZvrhlAMY4LWKSgCkADGAyRFAIAMzzWeWuYZp5bdyyyiyzp5vphjHAOqhU1UlhQIAAYwJAYCGQQmZ4q5leE2rQosoos6eLuMqGoha5CnCNKAJABjABAAAQIZjZ5z5mtqssosZRWXTl2HFgMKYQDq4KUKkMYDAQgAgChGFnyWmxZRZRQxlR083aKKHSCHQOAsBCoABjAQhmYjRAk+L22LLKKSlY4ZUdOXYqKoARQooKqAKQgAYwEBADRiPittyyyjaIpjHDi46a6jKgpDGEOmVCCpARQwARAFIAfFbbFmhZ1M3zJ59AcolRZ05dRjAQxw6YxgIQgGOCggCkQHxW5suholL1s3U59mAAMouOnLoMYAMBjGVBUiAAGBIFIgPittjRLWzq5uoznJjQEMdaR0pdBlAAxgMYwJJGAwJGUCC/EbmxolmkdSXZWBzUysBqDNI6UtjLEMBgUMBCEADJGWJA+J22SyzQ6eburGBzbMgABmsdGXQoYhgMYxiAQgBErLAE+I22TQos6WbuugABzLMwAYG0dGWyhgAxjEMAEJAlaLHCr4fedzRLWjp5uhZagxHNTKgYAbx0paKAAKAAGACRErRoEFfD7zsaJa2dPN0GtJagCOZZmA4KZ6I6BUrscoBQkagwARIyxgfEbzqmha6HTzdQAspQCTmWQAQ6D0x7lsqGADEMBgBCUtDBPidzZLLWzp5uwwApaARKcwigYDPTHtLWoYhjAYhjSCilQz4ned0stbjoy7FLQkClYAQcyyQgoHHqPatRQwGADBBZS1YAfE7zuWlLpHQl2KKGIFoYAQnMqQFDpnqj2y2UAAMYAklrQoD4jpnctLWz3y7lQyhgqShqILmnMqRIKxnrl9sUNGoMYAkroUAR8L0zsllmi++XU0HDLABFDBUQcuyUQ4KZ65fctRQDAYJJRooM+B3ndLSy16MupS0UOKGqRjGso1yTl2QAURR7JfcVFKwBGSWUMZ8B0m1xpLZovvl9MojLUKigBUlKkas8rPL1EADKXpy+mKGrQAUXViLj/xAAlEAABBAEEAgMBAQEAAAAAAAAAAQIxMgMQERMgEiIhMDMEQSP/2gAIAQEAAQUCZjbtxtExtONo3G0ZibhTiZhONmI4mYjiZiOJmM4mYzhYw42NOJiCYmHEw4mHEw4WKcLcS/0fxpiVcTTjacbTjaLjacbTjacbTjacbTjacbTjacbTjaeDTwaMgTRpj/5N/I/IT/mUKFSDbbWSSxt5Ct8022M/864nKnVTY267dGQgmiGPfE38z8z8ypUjXYsWLly5cc3lRU3M+FcTlTXYXpt3ZCaIIY98TPyKFCpGsljbyLly5c/QuPbyoqchmwrjdt9O3VsIIIIYXeDPzKFSNZLFi5Y/QuXLn6Fx7OZFbzJlxLjd9rKpoghhXxYn/MoVI1ksXLly5+hcuWLip5mTHyo5vOmTGrF2+xsJoghgfsygnqVNttJJLFy5c/QsXLFyxYX2MjORHsTO3JjVi6bG30NhNUMLvRPUqJ8EaSWLFy5cuXLFyxYkkkyM8x7E/oa9niu31NhBNEMDvSgnwQJpJYsXLly5cuSWJJ6ZGeRkxpna9niu3fbRsJqhhd6VI1kksXLly5csWLEk9VHs3MuJM7XM2NumxsbaNhNUMLvSpGslixYuXLlixYkn6HsMuJMyObsvTbVkJqhgd6VI1kkuWLly5YsST3gjR7NjNiTKjm7Gxt1ZCaIIYXelSNZLFi5cuWLEkk9IIIIIIIHN8VzYUyIrTY26MhNUMDvSpXWSxcsXLlixYnqpBBBBBBA5viZsKPRUNujYTRBDC70qQRpJYsXLFy5YknpBBBBBBBBBA5viZsXkippto2E1QwZUVtSNZLFyxcsXLFiekEEEEEEEEEG2w5PEy4vIVNWwnXDlRyRrJYsXLFyxYsT0gggqbbEEEEEECm3iZcO6KhsMr1QxZEemklixcuWLkliekEEEEEEEEEaL8aKngZsO54jK9kMeRMiElixcsXLFiSekEEEEEEEEdlTxFwIqshO6GPImRLFixYuWLFiesEEEEEEEEaqJr4DI+hFMb+VLFi5csWLEk9IIIIIII0jVerK/SijH8qWLlyxYknpBBBBBBBHVerK9FYqJ1RdhjuVLCexYsST0ggggggj62V6Y19cuJca9WrsNdypcsWLE9IIIIIII+tlRNca+sGXEuNerXbK13KlixJPSCCCCCCPrbCaoMXZsaZMa416td4qjuRLEk6wQQQQQQR3ToyBNEGLs2NJMmNWO6sd4qi+RJOsEEEEEEEd06Mrqhjds2NdvIexWL1Y/wVF8ukEEEEEfSnRlU1QZ8InxrIqcg9isXqx/gqLvrBBBBBH0p0ZCaoM9W1E0ksKnIjmeK9cb/FUXSCCCCCPpToyE1QZ6pUgQkksKnKjm+K9ceTxVFIIIIIII+xldUGerdvAjRPksWLDm8yOb4r1x5PEgggggj6U6MrqgxfFtSuslixYc3lRzdl64sniQQQQQR9jI1QZ6NoVI1sWLlh7eVFTbtiyeJBBBH0p0ZVNE0x+jaFSNZLFixYe1MqKmy9ceTYT4III+hOjITozdqIviUKkayWLFiR7UyoqbdsWXY32I+lOjITVDH6pURfEoV1kksWLFh7EyoqbddzFl2EXYj6E6MX403NzHXfYqb+JUrrJJYsb+RI9iZUX403Nzc3MWU3+vcZXRBDF8OX1zM+MuO+Ix/KM9sae2GcK/OJ9H1yD/AIHfDv6k74l3a2Ej/P8AP8/zr//EABQRAQAAAAAAAAAAAAAAAAAAAKD/2gAIAQMBAT8BPj//xAAVEQEBAAAAAAAAAAAAAAAAAACQAf/aAAgBAgEBPwEJ65X/xAAqEAACAQMDBAICAwEBAQAAAAAAAQIREjEhQWEgMlGBEzADECJAkWJCcf/aAAgBAQAGPwJfxR2r/DtX+HajtX+CuipfjlwUcVL8UuCjin+N8Fsop/je9C2UU/xvDoWSinB4dCyUU4PDoWSirXh0PjnFU2dD45xVNnQ+OcVw6HxziuHQ+OcI8OhZOC4dD45wXDofHOCcHh0O1U/+HajtR2o7UdqO1HajtRhHajtR2o7UdqMI7UYQulKX8vxyLZfy/Ey2Wv43uWS1/G8MsnrB4ZZPWLwz459uzPjnjZlk8bM+Ofplk/TLJ52ZZPOzLJ52ZZPPksnk+L8mNn/SXSlP+X45FstfxvDLJaweGWT1g8MsnrHZnxzxsz4542Z8c/TPjn6ZZP0yyedmWS7tmWS7vJbLuW5bLSSLZdxbLuPi/J6ZR/0F0pT1/HItlr+N4ZZPWDwyyesdmfHPt2Z8c8bM+Ofpnxz9M+OedmWTzsyyXdsy2WkluWy0kij0mij0mij0mij0kij0kj4/yZ2ZR/eulR/JrCRZLX8bwyyesHhnxz1jsz4542Z8c/TPjn6ZZPOzLJ52ZbLu2ZbLSS3LXpNFHpNFHpNHiaPE0eJo8TR4miyek0Uf3LpUZ9jLJ6weGWT1g8MsnrHZnxzxsz45+mWT9MsnnZlku7ZlstJLco9Joo9Jo8TR4mjxNHiaPEkVWkkVxJHiaLZaTRR/aum2fY8MsnrB4Z8c9Y7M+Ofbsz4542ZZP0yyedmWSzsy2Wklhlr0mij0mjxNHiaPE0eJoqtJIqtJIqtJFV3FV3FVpJFHpNFPsXTbNfxeGfHPWOzPjn27M+OeNmfHPGzLJ+mWTzsy2Xdsy2WkluUek0Uek0eJo8TR4miq0kiq0kiq0kVWS5ZLlkuWS6OkjxNGv1rptn2vDLJ9uzPjnjZnxzxsz45+mWT9MsnnZlsu7ZlstJLco9Jo8TR4mjxNFVpJFVpJFV3FyyXLJcslyLolUXR7j/r6102z7Xhlk9Y7MsnjZnxz9Msn6ZZP0yyedmWy7tmWvSaKPSaPE0eJoqtJoqtJIqu4uWS5ZLol0S5FUXIqi+OSq7vqXTbNfxe5ZPWOzPjnjZlk/TLJ+mWTzsyyedmWy7tmUekkUek0eJo8TRVaSRVaSKrJdHJdHJci6JciqKoqiqLolVn6V02z7WWT1jsyyeNmWT9Msn6ZZPOzLZd2zLZaSW5R6SRR6TR4mjxNFVpJFVpIuWS5ZLo/4XRKouRVFUVWCqwVRdEujn6F02z7XuWT1jsyyeNmWT9Msn6ZZLOzLZd2zLZaSW5R6TRR6TR4mjxNFVpJFV3FyyXRLol0S5FVgqsFVgqsHBXY4Lol0c9a6bZ9r3LJ6x2ZZPGzLJ+mWT9MsnnZlsu7ZlstJLco9Jo8TR4mjxNFVpJFV3FVnwXL/C6P+FyKoqiqwVWDg4ODg4LkXR6l02z7Xhlk9Y7MsnjZlk/TLJ+mWSzsy2Xdsyj0kij0mjxNHiaPE0VWkkVXcXLJci5FUVWCqwVWDg4ODg4ODgqsF0eldNk8Fk+3Zlk8bMsn6ZZP0yyWdmWy7tmWvSSKPSaPE0eJoriaKrSSKruKrPguRVFUVWCqwV2ODg4ODg4ODgqsFV0LqsngsnjZlk/TLJ+mWSzsy2Xdsyj0ktyj0mjxNHiaK4kiq0kVXcXLJci5FUVWCqwcHBwcHBwcfrj9cFy/a67J/wClk/TLJ+mWSzsy2Xdsyj0kij0mjxNHiaKrSSKrSRcslyyXIuRVFVg4ODg4ODg4OOngqv0vosl6ZZPOzLJZ2ZbLu2ZR6SRR6TR4mjxNFVpJFV3FVkuWSqKoqiqwcHBwcHBwcHHVwVF9Nss7MtlnZlsu7yUekluUek0eJo8SRXEkVXcVWSqLkVRVFVg4ODg4ODg4+lfVa8+S2Xd5KPSRR6TR4kjxJFVpJFVkqslyKr/C5FVgqsHBwcHBwcfUvro+4o9JIo9Jo8TR4kiq0kiqyVWS5FyKoqsFVg4ODg4OPrXSn1/9HiaPE0eJIqtJFVkqslyLkVRVYKrBwcHBx9i6bZ9rwzjr8SR4mjxJFVpIqslVkqi5FUVWDg4ODg4+xdNs8PDLJ42f0VXcVxJFV3FVkqslyKoqsFVgrscHBwcfYum2fa8MsnjZlk/T+iq7iq7iqyXLJVFUVWCqwcHBwcHH2Lptn2vDLJ42ZZP0yyfp/RcslVkqi5FUVWCqwcHBwcHH2Lptn2ssnjZlk/TLJ+mWTzsynXdHJciqLkVWCuxwcHBwU+xdNJdrLJ42ZZP0yyfplss7Mtl3FH13RLkVRVYK7HBwcHH6oUKfSumku1lsu3Zlk/TLJemWSzsy2WktmWy7ij67kVWCqwV2ODg4OCn6oU+ldNJdrLZax2ZbLGzLJ+mWSzsy2Xdsyj0kij0kinXVYKrBwcHBwcfqj+tdNJawZbLWLwy2WNmWy9Msl6ZbLOzLXpI8TRrpJdfBVYODg4ODj90+pdOusGWy1g9y2Xbsy2WNmWy9MtlnZlr7vJR6SR4mjxJdfBwcHBwcfqn1rp11gyj1gy2WsXhlssbMtl6ZbLOzLZd3ko9JI8SR4mjxLr4ODg4OPtXT5izzBlHrBlsu3ZlsvTLZemWyzsyj0l5NdJo8TR4kj/rro8HBwcHH2LprmO55gzzBlHrF7lssbMtl6ZbL0yjz5KPSR4kjxJFVpI/666PBwcfaumq1W6KrWJ5gzzBlHrHZlssbMtl6ZbLOzKPu8lHpJHiSPEkVWkiv/rrozg4+xdN0dfKLo43RVawZ5gzzBlstY7MtljZlsvTLZZ2ZR6S8lHpJHiaPEkVWkiq7uujOP6MaGg1sSWxJbElsOpqaldxPcT3E9xMQv6f/xAAnEAACAQMEAgIDAQEBAAAAAAAAAREhMVEQQWFxkaGB4SDB8dGx8P/aAAgBAQABPyHgzgWiGl9YPsSiTV9gmdjDhQTu39woJjY/BQTGLooLguhQTmHAJM/tAVYbiVabCQqaeyEix4AvQAqXupFRqNjOwqNoKBeo9mPoJ/JH9AfzD6YfwR/QH8AbP8j+AfwD+AfwD+Afwj+Qfyj0hakJ0Wr4I+IXgj4VwPpYH8wBCSlkMMZri8kwqol8wr/2DF1nyCp2vyFD4AW5fB5HXqsMQdtnoNDQ0NCaGIIIGtDRBB6WhBIQtIV8Ef5uEf5cH8ABc5Bclx7LH8opCuWYV0oLgoFllLDpWgqeJMjAWzEtXCWZyEGjyaDQ0MIQNUGhiCCBogg9LQh9FoUo8Ef4+Ef5EEZSPZYVyWFD3yiimOF+Q4FAXhIegmeuGLwIyzsA0RJevKITpKDEgYaGiBogggjRA0NEHrailw1C2TwR/loR/kCRmJxmsaphNVFRj0lBcdAj4SDXxQz0w9JLYekSwjUQLhYFMXsGMCIGhoaka0gaIIIGhog9TQtFw1Jmy8DjxH+YJGcHGSOB75x6iiuKnyHoIeuGRHjDIsfM1VM0DNCdIKBASJkBLVCe0Q0MNDRA0QQQQNDRCPW0LUgV+kI/zBGzQYzWPkWOJPgRXFXQyF4oZ6AZizOiukzNaSL2EicMuh020uhMdNhJaYkCYWUMaGhhiCCBogaIPW0JC0RL60HKoxmsPKscZI4Pgl6aB6iC8MM/7JpmZrZQP+RHtBHTLo6a3R0Uuh/9pDWKl0QrCPFAPckhoYaGiCCCCBog9YTQtESL9QRlMfKuMZujLcmMBBjHT5D1EPQDFtekTJA9hIj5CR10uh8Fbo6KXR+yQnOXdDU5N0NTn3RhaDk0oW6GMTUDEEEEEEDEHraULREivUC5YcZ7mu1HBq7YdRAjDQGvghnoBi2PWIJ7iR00uh8Fbo6O5HyN0Xybov7Ufeh/6oT96PeSyWBJdDnaYxBBGoxB62hC0RJgWAueCuSwxGM1XPogKmwodJA9dMa+IGctIn/Ijrl0dNbo6m5DXsEfaiJ9iPtR490TGPdDUb63Q117orb+6yWzrdD4moGiCCBhrT0tCFohRGJ4IzgVz2PxnHcK4iBGOgLwkPXTPQDGJkBeYkdMujqrdH7JF8m4an2ImPeh0w7otvrdFt5bo7DXQ+Q10NRuLdEjzrJ8rCGtUehA0NEHoaVoQjRGLAjOR7zmqYxVcw9FQXHQPUQXjhn/AEDQM0D3EicZLonFW6Pkbo+xH2Ia6d0dpro7bXR2GujsMZqrDVqrDVyD/JI+RhETHoQNEHpaULRCiMWB3kYynG2M1XMK4ihGEgLxEPUDFtegDNA9xI6aXR00uJnLuPsHh3RbfTuhI307oXMa6O4xkqsZKrGaqGr1Vhq5XD3urdG+rdEehoaIPW0LUiRXqDvgrnuMcI4nMPRJ10D10z1AyrwyWgyQPcSOul0dVLomcu6J/ai2+t0dpro7jXQ1mNdE3qrmaq5mqsZ3YXyYi+4aBj39yF+oIRoej1tCFo3hbPB22Fc8tsYx3D0UDpIHopnqBmOkjED3Eh8NLo6e4Xy7o+9Ccb63R22ujuPdGKrjV6q4vm55uebfgAwT/kIQhwjRB62hCEKh8DDwdsKpjG+YfigjCQPXQ9APSMwYOETjN0dNLo6qXRfLuj7UWxu6Ow10dx9jNVcyVXHdVXF82PMeQYehALHQ55VWFepIdHoCEIQhoPGTDSb5g6qB0kD10z/oBgDNaAKcYujordHR3Ivl3R9qLY3dHYa6M1VzNVc83PNxruxju1D0FSjseISimw1pVcBSG6j0BCEIWjE5ahciuKodZA9dM9AMRGQPYSOsl0J4a3R+6RfPuj70OmPdHYa6M1VjjVzzc820oJbGMBajsWpsOnQlFNiNnYk5c5MPW1IQtENGk4LhqC4SWC8dD1A9AzFi4R00uiMdboTnPui/tR96Lb6d0dhrozVWPNzzc82MbsYOoasYwdi1HYtR2KOjBkQNbMbTR0PSEtFovwgYmVhbDoJYLjtmegGctFXsJHSW6Jw0uj5O6E/3j5Vui2+10dhrozVXPNjzYmy4wdRg6i1HYasdi1HYtR20KlBYZBDPQEISFovwgYpboSzF46YvYBgDMXsBE4y3R10ujy7o+1Ccb63R22ujsNdGSq55uebHkMGMHYtR2LUdiIo7FqMwYsacMqeoIQhIahUdmR+LGlC1JuFszF6BGaF1C6HxluhcPci+XdC/1RbfTuhPIa6O490Zqrj+bnm5iMHYVKOxajscMtRlqMeGI40lnqCEIREq/QEJ+D/JrpqgtVdEE3oXULo6a3R09yFXPuj7UWxu6Ow10J3qrmaq55uR3ci24wdi1HY4ZwzhnDHhiwcPSp6gtSIFX6gjLcYC7bP8UMmTEIoKYuETgJdHXW6P2yL+1H3otvtdC5D3Q9qrifdzzc8w1YxFjsWo7HDOGcM4Y8MSOGd6etoQtEQr0BGc4xzoYy/JH4Icho2jQ6aXROOt0fskX96PHuhcpro7jXRmqucauJ92MLsYOoex2LUdjhnDOGcMZVTc4LktHpC0K2iIV6AXOcIjEMR3wgT/ACbMiPZW6Jx0uhPl7o+1Fse6O010dx7ozVXF83PNjB1YwYwdi1HY4Zwzhl6MeCrsvpJ6AhC0QCMDwdxxtiGJyC4Sj2y/JkqIXyEXy7oT/ejw7o7DXRiq+wt6q55uL5sYuotVYtR2LUdjhnDOGcMZV3pfT0NCFoaFc2HgbMcI5EN8gddQgW0s8/mEfKiF5EJ/tQnG+ndHaa6MVX2M1Vzzc83MHUWo7HItUcjhnDOGOpV2X0k9LQlqaBc2ngjKYGjAMdo9VQjAATaFs8jnoh/k3iKF1O6FTfa6JzHujFVzNVc83FtdWHsdQqVWIijscvJyOGPDOGPRfST0tC1SWubTwRlgRpwGAjroHrpiy09jKPydxEG+10J5j3RN6q5nvPNzF1cwdRg7aHDFhnDOGcMYtL6egIWp4ucYPoEEZ7hEYw7EOkoRitmYvkZEQBMgah/k94YTyH2FvVXE+7nm5Fl2MGLUdi1HY5HDL0ZejuXoxlXf4egJCEXDlL9QLxBgRlsEQYjvBcdQjFWw9RPSEkmRP8pUMJ3KrifdxfNzG7GDGDsWqtk4ZwzhirR3L0dx1oKvZfX0hISFomjlOBQnBHgX0EEZ76GNSATwEsPUTMHooS+kOw92n+MkqGE71VxfIrZdjB3FqOxajscM4Zwzhl6Mdeyrv8PQ0LQhnJzkQk/mF44eBc9gajGHnDoKC45YY8hpGSFK1RLodE/z4bs4vmxg6sYOotR2OH5OGcMvR3Hzcv2Vdl9fSGEJiHhqlghZXGwYP9Ys41gTlvoZfMHTWwTxmzMXoqvsN0ThUTYY8On4p6ALscmx1YmKOxajsWozhnDOGTNHckq7L9l9G8RcSJjDMYE89usEH+YweI8Fl9+AtvsD0xC62lgn8RMwYpKmVguiJqJdDmhkkkkiHADsMYMWo7FqOxwydmTNGPnT2E50k8USJiCCNSKAWUcE4c4WBJ/7kYvEeCcgCY32BmdAHXLBP4iYwMAk8QuhzoJdCt4dGT+AQhxChR1CcUdiYo7ZOGcMnZkzRkic9k+SZ0WOhXFqM7ZNyxoTuigbtijxRW21xKkqmxSqosVl0WN6prcpWs3G/wC0P/3ihL+RUVRlbVGxKbRUej0TE2h1VyVPJUFUquudjYdtf//aAAwDAQACAAMAAAAQt1ZMQAQXqkqsn/ymQeTpSrYqUAUPtqOCL+CQOMFbimW2Ak29nrTsVgMr9Q0XUSEUpttNP+7EA7Wnutlknw0DBZ9JJ4tFMipC+Muki0GVjZTfdcMhaiKyfQ0jyGkp0pZrbcIm2TXs71pWWhhpBl5//cMHQJs41ICztlPRotJ7fY0G8+phRhClAJKJNJ77b1wZi60LMKVkExJrNLb7b4oPe2UMlDQMhBv5pLe7eVphFNEpOHloJkjddfbbP4tgiIMBgRJpt8dPdtff/wCAEMCTTNKKSfUaf6a3/wB9kKgEk1YEUk96m0k+vvtv1ay022GSksB+k201/tv9OLUw22E38ujskk3t/t9tsJ002lAMOe/kmu8t99v+kY0009H/ALtfLPNp5L/7fVNNJJtX7J9r/frfZL7+7ZplpPbt5pp7tZPf5bff/JMvBpblvtNpL977JL/bZNBzNlP1tNvZJNbtdTT7thiNb5L7pPt97NbfW7fZAkVnB3P5pLZ7Nb5a26aEFfFPJFPb5prpJLaS+H4MochJQlv6b7//ADfUu/IRRJ4LJZIb9u2gTXT3/o5QRH7II6Da1k1k53Q/sGJKB+qJIbFbyi/h6iBngQbIO/2BAeeIoE2n/iJ0hybI2+YBD5TA6N/2342xDCBu4uZIJQ7exv8AN+udWCCzbOvySyyjEP8Af+bzaP8ACAIF2xTCLR4Kvv8AbrpGs3UCBd8+0ARR28ZddN9Utm7kQLLHhjjKbYvLdvv9F8yXiJKfLD220Sve3k1//8QAIhEAAQQCAwEBAQEBAAAAAAAAAQAQESAhMTBAYUFxUFFg/9oACAEDAQE/EMsGC0tNptV0/wCtrCM0lvVtg0qUNNmmUBDBabVPH8vrBRtljtoYYwUYUArDQjhDO0FGW0/6/nCRK/UVLypeWIRbCwsMOqcr9RFjwQVhBFxfzj2xDhzw4cP+t4vF5bdtrbENKngJrth1DlotNYpFfF437znLQxQaGikqUUOwXKlSipphyJQ7MfQxUWiGCCPbLRKhQoRCiHDG3lJ6RpliVP8AqlC373oR/hl/qKkWHdIc3B7pUcU9whoU8M9yGI4t2iexCHDvvDi31i+ucf8AEw47x/gShcsLa68cO1FiFKHfnvauaBedHVZ45y32v6p6ErTSo5ZtC87ELS3Qqawx/wAX6t4ffJHQw4bxvDTa201msPtihaWKhwg/hrvgmscAoW240tN4v25uOEuOEU8eW2hWaC22LTWGPB4vLbW0XlSpUtCi0NLSiUXmmlpyweWOVNgWlSx4S4b6vrBhkL4vi+I6Rf6i5QcL5x//xAAhEQABAwQDAQEBAAAAAAAAAAABABEgEDAxYSFAcUFQUf/aAAgBAgEBPxCBR5WbLU3R4/U6CZEQdFAwaJRnmm4eILxeQagRp9QsDMTzLy47x+wCNGo/8iaHlZl4thPaEQiaBGho0zqO6+WnabUHCdC0VnmzuyC0XRTp6uyejJ08DTxbXkcWwsQKaLVHKaBXi3TY6IMDP5MrK2K76IKHFsRNdim4O150OKNV1mLwIW446QKdk9WWE6eZzwsrYW1tbjhC8EDdOllbEsUx0QbhzwvJYi/QB6bFMd0iuxHEcIdB7RDTx1waPYIpuhse9IIIWCGq3bcoWCKYTzbvMjxefrlMiFihke7yEVlMyKxNqMhI3iEDUooJkzTFM2ChdOqAzaX3sGo5/H3R/wAJqbXi2swz+IE8D1muYk3Va5hY7TTzPCd+8ZYriZTdBp5s4mRfxM28TzfaRuYq0CO15HFMTIe8Qmqb+KtVkRe+QK+UMvsQhMr7QZp9RqEaf//EACYQAAIBBAEDBAMBAAAAAAAAAAABESExQVFhcYGRobHB4RDR8PH/2gAIAQEAAT8QkWJjo6b4FzW6D6PKs8T9CvOqFcvBtAhKuxvn9PYbddqrsNl/B6yB9+nqLDZT22hw6DtDy2cWN8/k8SMcu5PHUk+XUelycbtznpcdlpNP0y5SbZ/mK5FSn+AuYZwinoUjXi0hoReZcRpt4v6G5t9I73pxj9MddTdpHdnpI1p4n6bjTHsxufxxMmj2j53pKnwx5em1G+/hkFoOkTqQ9heBaVFxUZKnwaeglZsUz7xwpR0ZdWQLzNhZPGnY4KnbbHAu729FJIf9i8lJ1Jv5+xJyr+V9kkdt+l3Ei6P3LqVFCoj1LkTMoLVWeVyKCRX7y5JCeVDsiXqizQ9OxUJvwyMotDokZHYb9BtDFSWRtX8NsjSbSJLuHt+El8lai7kT5HT0FCVmQresApDaSqLbz58C9x70Ev5Qt3IpNB977JE6/rfYyfz77KDoVNvPURptCaTyhVMnVnlCT5MVnlFflCc8ocTz1eeUY4wc8ohNHLyLopjBoYn+SqNkuoLZwhGQmFvRsQodUOrY5dSVyN4mw3miKOk9he4kQ26oY3EUFlwMh64ms2L3KAhVfVwv0bhL9SxS5hegl6yfc+xKuw72u4tVh3vsUDp1PRPqNqi9TnlCNXlCs8oUo8iXnlCpeaozyiS7yjPK4F16pPdcEZqv017rgx5gm9oQk5bktVLPaOMhTOEdYm0DvpDuaRI60ObHRwS6iLkKt2G03gvuHsPDpYRNT3LlpEoRBoW+QvdchDeQ6kmC56CBNhgRrk82qiRdNe9ruJPkJOeRI7/t5QquUKzyhnNq88os2dGeUVTY01nnoJdIL/sCboa9z+0OP5P0f2X/AAdE4rV099BBRCvkfgZbz+OF/hNGvI2rH4b7DoHBlS0FPgdTojL09hIZaoJOBJQhrud8/Shik/vS59WzVSNRJ9BRk9TQqXSXvaEv3hEl29HqEnLOlZ5REd9XnlDqi/RnlDbltJn6LIek1n6KpP7f0WclX9gTfX5f2BEjdF8Pg4JjT9uh1IXKf6E8qVPY04wY9Eboiihwb/AzX0KFoc7oSq5MCQ5KuHqBfGIO3qLYSEXIcz3cMhCyRPZdoXcKY0OST+BU615BtWwneEnv+8SvbhGeSXJq88inn/ZyRHjL39CUNHz9DTeVw9/QlFbyv7BD4maeeHwRDWGZ4KugszwxOKrM8Mh6K2zwxK9zzpllNa5HgkOGmcZLAipQH4lBWyqOg8xHSFuWh7CVRSqVqiqJLKpFfaKPsm7il9jww0KC4fSV7v4qPVJXcJQ9+vIpr7pWeSXMqc8imWhpnnoJtIfy/odEPk/Q11rrf0KeBm2SIaUzPDGnDEW54Y6NebnhifSpueGJFUVps4ZH0pmeGJU/3PDFw7O08j6lA0eTn863JvAqxzHFEByJ9p7FplkFAk9RMu99dSt7fBv19aK1w+gaksooeS/MT5y3Di3dPqIc8yVnkTfLyMjNOJvf0VWf2/oba6q1v6LNW2cHZwzPDKvorc8MSOkNzwx06U/PDHbRSj88Ej9rIP7CR4/clFuq3wxOSnZkbUZUaY54HHk6Rm4cMDgzQOuwngexyItKUJIssWy85kU/abo+mB3FjKfRUTbip2F75Ety2j6p9vJ1TF55Ifo+SM+gb39CbrW3z9FesK39Cbp7BmeGWRWrc8MSOnPzwxN4LR+eBqOLTeOfsP7GqLvn+xOMyfdEnKn3Sxm7g5lRX6AyJttgohpqjTQxOw2kchltQcCaYuOsSvwEtUWi/Baoq/X7IRVXhwsfZ3ok95gfjO8Q6qE74/yTRp1t/t5Gnz0vPIzkdGSGscnv6JaSttb+hN8TPb6K8BtzwJw19+eGOiw0fngtaEpv4Jm7Pn+x0K09/wCyzup9/wCy2ZgW0YJl4CLIwNj04GNflA4SCo0N0XCSKobxpEBWqiFr1KOm9hVKHhfgu2VF327hXXw4SHtI0KivsFWkVH3kiZ5r3aPDaN3JHXrfyNNtl2CZ/L+hykOJv6JNBbbBOegtzwJx0x+eGTopR+R+ibiJm8/2KS+n/fURZLd8T3kHzWwJ9sASXGD3N/QZtrIaUJKuoNCRpp1RUsKCj2HwsNS0VBzdpEmNYFUC8CfgVM+OuF+lMRH24aOSFRnp7xD+aMo8XckNtg3DR2XaR2mTI06H836Gm/JVsfaZmRNxbv4F0EPzwTRSWm/7Jn/b+xuMiff+yLK399ROQhp0AD+4OT1iQn3aWjuELRsbXReCn3xyWkiBzk1DV0ypYcFRSOSOIdZSrh+CiFxUX78Er3wYX6HQltMCXML3hL643kml89EhsSs8lW46ZGjx17K0P5v0Q+sq2NNtYZkt0VueGJ29k7IpIdN32TzT3vslJ3W/vqX8STs5A0uJA13SSwKvnSWBqfOloT2G/BI5AtGwXFouFbeSBnEyNdpqGUYp+NSO7sKuy9hXRlolSMxY3pux1FX7ThP0tHIsCYKSnbK7xJ8jpyQ+Wl5FT79BrhDexP8Aqv6K9RVv6GumDM8DxrRueCYaU/PDFZdN4uSn3/s6tNPqQgfho9zqpfILu4SwNd+Fo2MpaGrHC0Nj8AdhU9C6VWxoY0SWRmuO6ElpqIJ7fgarRiz0HsLFBBpitCSdiCE9ZYVXtSOWu0JbTkmf8rqRe+Vm75oJS2reV5lRktlpvZLaVxd/QnDli2aVgzPAkNFfngc0cNHZJ8VvE715ND3E6dAj3PRAwX8sWjvsJYGnK2FoicrgaNl8Iaxq3oJ9beg1PbehcKreg965YGlMp1aQ5nJOOo9O9hHCFmBJgvGuFl8tfuwlO89onnN9Qv4pkPB7yUNt2CmvulZFPMuwU0v5o6IcXf0Jt+XbFFbSZ4JfCrcjXQp+SP8ASxQssnRrxKZ0eQGmBecFoiXIFo8UGhv6oI6n0Fjc9BJbb0E5Q6v6Dkh1b0K/LjnrylojDKd0HM00chaGGlewsRom7F34tKSJiEt1M1wqVyVdhDX2Ulfk0K/LGVRaJsJxlzdlW5Itj8gJ5E3FLcio05ueDVho/PA1GGPLGoyp9864mUNChucLR6wLRuuBov8A8BL/AMQslbXBFrq3oXQ6tZ6GrHV86OUfIlSu5KlVxwRLfQhHKdWkVgsdB7C00SSFmZ/CkTGpymKPKF0xcRO8P+yEUp7tBJ8sbxp9FDJKKeq9kuhuLZLf3ADXQ1meBzag/JRFRS/ngTh+9ji3Kff+zlnP0SBeWloTv2HBH9EEWfpCwdXtwO1d86OQaz0NXKtbgjcezGr1WsOqHVskiVWsxwVxh7KZC0PyQlUJCrU+xVImNSBI/BQo/CRMiG2E6hL5YR3W+bEl98RD+ZAhpD5uytLeWxU8obJXgXaUUuwfngmW1o/Imtnz/Y+un3x2qFvx0YKdWloWS+AJ2foGj+EbNJ6OsLPRJuNZ6OoezEp3HszrGszG6tkfoYY7DtsouomJMmLASMVHsVhIu6iX4IVxDFNOGsid3ShT87w37ZxSj9SAqY0z2NNpW8tkt/cAJt3DT7epJtafkfS7+SM6eUNkC11SRga7sLRSpfCP4rCllVs9CruFno6osxc97M2B7MXMazGthrMoUOqdmLcthjSsGxwo7YY1Olj0j2MRKmwSBfgqiExqmm00XYX3nhA5JVGJsJ1NzdlaW8tjTpPKBWW3bkt1p+RNvCnfHWj85tybcOIHuU+Mga78LQ1euBocfxI1dWz0J7bWehct7M6t7M69rMfKLMXJTsyh3FsWsuzEmqPsxOfQY4OBqr09vxWBKXZIMwJc7BQoJeRUQqigWmNNZLvS7CWnDXslW+Vv6JfXZbG+iewZ8XNyO8Zo/I3a+7G5EzgaVtxIwegBgp1IWhLlwNDX8EEXKvZ6Eth7PRybWZ1bWYqZ1hkbawxrYWGUK5yLFH2IyIijIKQUdF7C1KxY6F5bf5A9NVqps0JCF0FImMKbJ0gYCRKU1kTaSvmCZeYLZMNT2Do0baJytFN43+tlNqZp7hLS6MBK5wtES5gtDV6+AYP0BySq24I2Hs9CW81mTzrDFR3lhkU8bH28MVr0GVpt2ZHwMaw/I2mzRw9vxTQrCzBYsp2BIvtCGtUvVFmiIFaBWgg6hXeTTpA8hetE+5Gwuj3YeMm5JtoTvi6nzicZ0zTmAa/EDAkuUJYL1fAI/ggasdXOjZGs9D2z2Z1zWYlGt2Yn8bJ+Jl/TYvwMxO+GXWQamnkS1SCvpvb8FkpguUj5uROwK4+JEIZk8rphLNHQR+F+D+2mmKQzMWxPoKzIulB+R2phKbyeX5xOMzHAIHHRIxyJ98FoaVXAaLR/CGr2k9HUFnoTaez0RttZlsydmfUy/osTleFlbh3wyjKmBEuVHseDuWEC+J7DjYgabBWx0tInYE/RGULomrKQ3pdrPaGiCwqIQmvhoUtvkAuhJ+TQ17467fOJ7THIQT+gDkTlyhaGnK+AJf1QJ9onoa2ms9DW29mNb7WY1GdYZb02KuzDFdcwzdfDElDsyWQ7rI60dySuhI6L2F/kJCoYQi5FYRU7wlBekIXJ/wARwy94Q+incIQTVnsj8KxIqC4/qtilUU7onFfKyTP641GVzTGD1ITA3cBaFn+gNY/hDWw2ehPdezInfezI22sxqNeGNfAyJ2YYnOxWZXR3K0OiDcqtGiI8kxdVE8L2JKwkClw+PhO8RJvGRQU0hJNJoQ4dV5CIatGzwhpr1EmaE6x+FcVGUytiEOl+UoQzJoiS2l0KCV1I0FHWBLAn/BAlOHocCe0Weh857MTSUsp2ZGynZk9HDGvjZd5sMusmGNKHdCdCFkO5fqckaeHsIVrCRdQdBxnaHQb4iJS8/aKq/VCTb3QkvlMGJZxBgBiu5H+EL7VWuhEBCyjoCJOWEYKdaEsErrEtC/ooQ6BPRyhZ6G2Xsx7drMT+ENP2WX34YnUwyjWRWZKYd8FXDF2MvwyGUYdoexJjRAkJLQ0GvKchLzHpHepftjSk4srC98r8yo5bYt40qVk9jOTJ2Y0J1EL1EJWXuhCrQA7CBjkSPLkLRFqt7gVNfgE9gOBPbLPQ+W9noTjInZlVnWGNO/DHXfhl9+GNTsVmJNHfYs8NCc8MnuROxI6D2HjsNK+RKYMRqBg3SO5oWe8ZG8XSFopsKMdq3Im+Scq3HAbVL1Q2xga8/ixgTguka6EpvDTHIy6lJYENK+ENJf8AAZWk9ETmlrPQqb72ZG21mJ8umR8LLM2GJeiMfAeGXQ74EsshfhovwyVo9C9hGSCoVQJKCE4t9hkiYSU/Y6LfsN9hBeEnkB4G2SHW+cFXh83Y7ppJVWxmW01g0RVimBCdRk1LYF9eEsCnlfAGVDj0Asmk9ESuNZ6I23syy61hbOsMVfTY11MM6Asx0WEsyTSuhehBLKjR7k8EnNpXsTNGwgQkaKepB5GIMlTgJ/ohkRnTFrp8J3SkZ5sOflhPQZ7KtK3FsbkLlsi2Yao0yIZIhCgMubeh/mAN1noNe0noa3LD0RvvZnVtZiprwyfiZffhkTgeGNpcisyTQ6JYo1og1LrRl+oxmsjh7FkuJKig3F11ZamUJ6bDJJCayzxUoazjE1qBalxAyNQ/y4r0U2E28P5hLfmS2PCLdg8IhqkMfAyYJE4ZOVRvQUsr4AyofK0JroA9HVlmLltZkw76wF+pl1nwzoMGVUoSzG28Ow3UQsrdDVdMlaG8b8DRElOB5gUcU0nIggoTgDEpTwRIiZ4CNRFZMXa2F7o03n8iJCb5qNtuDPYm2/3EvUHYPa7Rh9Q2WGN1gknImQsuaW3gUsP4AuyJ6Ft3s9E772Z1admL9bJnfhktvNhjRLCWY6odGho6QnoQp1fiXpvYd1NwKsmxQgaE586gkJzvCJ1sISkT+qBKQ8Imn0EiMl2Nha7RvmYrJdmA5HL9TbJVLeWyo3MyMCbQGNDTWH+HqFUX/ghJ8vnQlEm8J6E9hrPQms97Mlt2jJzUwyXtsXIYYzai7ZLdHcaeEIN4aJnhnNGWw9hRjBa1+B0i/TEcZFavGRJEyGVsq4lKn9UCCJnhgkRNAyoyptuFNs5KtNc7hrcCMjTh/MKre2Wxs9pMigCDyhoumTWfyIQsXBsvZvBHkls9G3vZ6Fu2sE404Y38TFLZhjiybJNw7lpYeCC75DKPRvYepFLS4MOgzgnc6FGRVDU8FmYcVXESB34mHiYeyMvijYMSc2B5lecndDb4pZIa9kJrJDG86YayHKwWORjiaoGuhaTayhnXohtZQiKJrsdpruS5Y1GJjKVGihkaiK0jxqknA7anWHA7fNYGdWpdG7jcydypHkToeSw8ku/4/9k="];
            }
            return this.article.images;
        }
    }

</script>

<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    .carousel {
        width: 38vw;
    }

    .user {
        border-width: 1px;
        cursor: pointer;
    }

    .category {
        margin-top: 10px;
        border-style: solid;
        border-width: 1px;
        padding: 5px;
        display: inline-block;
        margin-right: 10px;
        float: left;
        border-radius: 3px;
    }

    .bcol {
        padding-left: 1px;
        padding-right: 1px;
    }

    .section {
        clear: both;
        text-align: left;
        margin-top: 30px;
    }

    .description-section {
        margin-top: 70px;
    }

    .user-col {
        clear: both;
        text-align: left;
    }

    .headline {
        display: inline-block;
        margin-right: 10px;
    }

    .article-info {
        width: 38vw;
        border: 2px solid #ced4da;
        border-radius: 3px;
        margin-top: 1vw;
        padding: 0 1vw 1vw;
        clear: both;
        float: left;
        text-align: left;
    }

    .rating {
        width: 120px;
        display: inline-flex !important;
    }

    .user-details {
        margin-top: 20px;
        padding-right: 0;
    }

    .clip-circle {
        width: 5.7vw;
        height: 5.7vw;
        border-radius: 50%;
        margin-top: 15px;
    }

    .address {
        width: 300px;
    }
    
    .alert {
        width: 100%;
        text-align: left;
    }
    
    .address {
        width: 300px;
    }

    .editArticleButton {
        float: right;
        margin-top: 40px;

        color: #484848;
        background: #d0f2e1;
        border-color: #d0f2e1;
        border-radius: 3px;
    }

    .editArticleButton:hover {
        color: #484848;
        background-color: #abc7b8;
        border-color: #abc7b8;
        text-decoration: none;
        border-radius: 3px;
    }

    .slides {
        height: 25vw;
    }

</style>
