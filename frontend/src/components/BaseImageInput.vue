<template>
    <div
            class="base-image-input" :style="{ 'background-image': `url(${imageData})` }" @click="chooseImage">
        <span v-if="!imageData" class="placeholder">
            <img class="placeholer-image" :src="require(`@/assets/upload.png`)">
        </span>
        <input class="file-input" ref="fileInput" type="file" @input="onSelectFile">
    </div>
</template>

<script>
    export default {
        data() {
            return {
                imageData: null
            }
        }, methods: {
            chooseImage() {
                this.$refs.fileInput.click()
            },
            onSelectFile() {
                const input = this.$refs.fileInput
                const files = input.files
                if (files && files[0]) {
                    const reader = new FileReader
                    reader.onload = e => {
                        this.imageData = e.target.result
                    }
                    reader.readAsDataURL(files[0])
                    this.$emit('input', files[0])
                }
            }
        }
    }

</script>

<style scoped>
    .base-image-input {
        display: block;
        width: 150px;
        height: 200px;
        cursor: pointer;
        background-size: cover;
        background-position: center center;
        margin-right: 10px;
        margin-left: 10px;
        border: #d0f2e1 solid 3px;
        border-radius: 8px;
    }

    .placeholder {
        background: white;
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #484848;
        font-size: 35px;
    }

    .placeholder:hover {
        background: #E0E0E0;
    }

    .file-input {
        display: none;
    }
</style>