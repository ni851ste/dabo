<template>
    <div class="filter">

        <div class="filterOption">
            <b-input-group>
                <b-input
                        id="input-1"
                        v-model="form"
                        type="email"
                        required
                        placeholder="Suche"

                ></b-input>
            </b-input-group>

        </div>
        <hr/>
        <div class="filterOption">
            <label>
                <b-icon-geo-alt font-scale="1.2"></b-icon-geo-alt>
                Standort
            </label>
            <b-form-input
                    id="input-1"
                    v-model="form"
                    type="email"
                    required
                    placeholder="Standort"
            ></b-form-input>
        </div>
        <hr/>
        <div class="categoryCheckboxes filterOption">
            <label>
                <b-icon-list-ul font-scale="1.2"></b-icon-list-ul>
                Kategorien</label>
            <b-form-checkbox
                    class="categoryCheckbox"
                    :id="category.category"
                    v-model="status"
                    name="checkbox-1"
                    value="accepted"
                    unchecked-value="not_accepted"
                    v-for="category in categories"
            >
                {{category.category}}
            </b-form-checkbox>
        </div>
        <b-button class="applyFilter">
            <a class="nav-link disabled" href="#">
                <b-icon-funnel font-scale="1.2"></b-icon-funnel>
                Filter anwenden
            </a>
        </b-button>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class FilterPanel extends Vue {
        categories = [
            {category: "Haushalt", img: "haushalt.jpg"},
            {category: "Garten", img: "garten.jpg"},
            {category: "Werkzeug", img: "werkzeug.jpg"},
            {category: "Unterhaltung", img: "unterhaltung.jpg"},
            {category: "Elektronik", img: "elektronik.jpg"},
            {category: "Sport & Freizeit", img: "sport&freizeit.jpg"},
            {category: "Sonstiges", img: "sonstiges.jpg"},
            {category: "Auto & Motorrad", img: "auto&motorrad.jpg"},
            {category: "Kleidung", img: "kleider.jpg"},
        ]
    }

</script>

<style>
    @media (max-width: 500px) {
        .filter {
            display: none !important;
        }
    }

    .filter {
        display: inline-block;
        width: 15vw;
        float: left;
        top: 0;
        left: 0;
        overflow: auto;
        min-width: 135px;
    }

    .categoryCheckboxes {
        margin-top: 15px;
    }

    .filterOption {
        margin-left: 2vw;
        margin-right: 2vw;
        margin-top: 5px;
        text-align: left;
    }

    hr {
        margin-left: 2vw;
    }

    .custom-control-input:checked ~ .custom-control-label::after {
        background-color: #d0f2e1;
        border-radius: 3px;
    }

    .applyFilter {
        background-color: #d0f2e1;
        padding: 0;
        margin-top: 15px;
        margin-bottom: 15px;
        border: none;
    }

    .applyFilter:hover > a {
        color: #d0f2e1;
        border: #d0f2e1 solid;
        border-radius: 3px;
    }

</style>
