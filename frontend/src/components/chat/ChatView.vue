<template>
    <div>
        <NavigationBar msg="hallo" with-content-layout="true"></NavigationBar>

        <b-container fluid class="chat-layout">
            <b-row>
                <b-col class="person-area">
                    <b-list-group align="left">
                        <b-list-group-item class="d-flex align-items-center"
                        v-for="user in this.users"
                        :user = user>
                            <b-avatar class="mr-3" button @click="openChat(user)" variant="secondary"
                                      :text= user.charAt(0)></b-avatar>
                            <span class="mr-auto">{{user}}</span>
                            <b-badge class="badge">5</b-badge>
                        </b-list-group-item>
                    </b-list-group>
                </b-col>

                <b-col class="chat-area" cols="10">
                    <basic-vue-chat class="basic-vue-chat"
                        :new-message="message"
                        :title=this.user
                        :initialAuthorId = 0>
                    </basic-vue-chat>
                </b-col>
            </b-row>
        </b-container>

    </div>

</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import NavigationBar from "@/components/NavigationBar.vue";
    import BasicVueChat from "@/components/chat/basic-vue-chat/BasicVueChat.vue"


    @Component({
        components: {NavigationBar, BasicVueChat}
    })

    export default class ChatView extends Vue {

        users = ["Max Mustermann", "Tanja Keller", "Lisa Müller"];
        user: String = this.users[0];


        openChat(user: String) {
            this.user = user;
        }

    }

</script>

<style scoped>

    .badge{
        background: #d0f2e1;
        color: #484848;
    }

</style>