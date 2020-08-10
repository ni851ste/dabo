<template>
    <div>
        <NavigationBar msg="hallo" with-content-layout="true"></NavigationBar>

        <b-container fluid class="chat-layout">
            <b-row>
                <b-col class="person-area">
                    <b-list-group align="left">
                        <b-list-group-item>
                            <b-avatar button @click="onClick" variant="primary" text="M" class="align-baseline"></b-avatar>
                            Max Mustermann
                        </b-list-group-item>
                        <b-list-group-item>
                            <b-avatar button @click="onClick" icon="star-fill" class="align-baseline"></b-avatar>
                            Max Mustermann
                        </b-list-group-item>
                        <b-list-group-item>
                            <b-avatar button @click="onClick" icon="star-fill" class="align-baseline"></b-avatar>
                            Max Mustermann
                        </b-list-group-item>
                    </b-list-group>
                </b-col>

                <b-col class="chat-area" cols="10">
                    <b-form-textarea
                            id="textarea"
                            placeholder="Enter message..."
                            rows="3"
                            max-rows="6"
                    ></b-form-textarea>
                </b-col>
            </b-row>
        </b-container>

    </div>

</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";
    import NavigationBar from "@/components/NavigationBar.vue";


    @Component({
        components: {NavigationBar}
    })

    export default class ChatView extends Vue {
        data() {
            return {
                items: [
                    { user: "Max Mustermann" },
                    { user: "Lisa Müller"},
                    { user: "Hanna Maier"}
                ]
            }
        }
    }

</script>

<style scoped>


</style>