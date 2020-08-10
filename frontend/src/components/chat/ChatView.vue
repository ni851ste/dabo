<template>
    <div>
        <NavigationBar msg="hallo" with-content-layout="true"></NavigationBar>

        <b-container fluid class="chat-layout">
            <b-row>
                <b-col class="person-area">
                    <b-list-group align="left">
                        <b-list-group-item>
                            <b-avatar button @click="openChat()" variant="secondary" text="M" class="align-baseline"></b-avatar>
                            Max Mustermann
                        </b-list-group-item>
                    </b-list-group>
                </b-col>

                <b-col class="chat-area" cols="10">
                    <basic-vue-chat class="basic-vue-chat"
                        :new-message="message"
                        :title="'Max Mustermann'"
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

        openChat() {
        
        }

    }

</script>

<style scoped>
</style>