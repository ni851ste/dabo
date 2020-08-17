<template>
    <div class="body">
        <NavigationBar msg="hallo"></NavigationBar>

        <h1>Impressum</h1>
        <div class="contact-area">
            <strong>Angaben gemäß § 5 TMG:</strong>
            <br><br>
            <strong>Max Mustermann</strong>
            <p>Alfred-Wachtel-Straße 8 <br>
            78462 Konstanz <br>
            E-Mail: dabo@htwg-konstanz.de</p>
        </div>

        <div class="liability-area">
            <strong>Haftung für Inhalte:</strong>
            <br>
            <p align="justify">
                Als Diensteanbieter sind wir gemäß § 7 Abs.1 TMG für eigene Inhalte auf diesen Seiten nach
                den allgemeinen Gesetzen verantwortlich. Nach §§ 8 bis 10 TMG sind wir als Diensteanbieter
                jedoch nicht verpflichtet, übermittelte oder gespeicherte fremde Informationen zu überwachen
                oder nach Umständen zu forschen, die auf eine rechtswidrige Tätigkeit hinweisen.
                <br><br>
                Verpflichtungen zur Entfernung oder Sperrung der Nutzung von Informationen nach den
                allgemeinen Gesetzen bleiben hiervon unberührt. Eine diesbezügliche Haftung ist jedoch erst
                ab dem Zeitpunkt der Kenntnis einer konkreten Rechtsverletzung möglich. Bei Bekanntwerden
                von entsprechenden Rechtsverletzungen werden wir diese Inhalte umgehend entfernen.
            </p>

            <strong>Haftung für Links:</strong>
            <br>
            <p align="justify">
                Unser Angebot enthält Links zu externen Websites Dritter, auf deren Inhalte wir keinen
                Einfluss haben. Deshalb können wir für diese fremden Inhalte auch keine Gewähr
                übernehmen. Für die Inhalte der verlinkten Seiten ist stets der jeweilige Anbieter oder
                Betreiber der Seiten verantwortlich. Die verlinkten Seiten wurden zum Zeitpunkt der
                Verlinkung auf mögliche Rechtsverstöße überprüft. Rechtswidrige Inhalte waren zum
                Zeitpunkt der Verlinkung nicht erkennbar.
                <br><br>
                Eine permanente inhaltliche Kontrolle der verlinkten Seiten ist jedoch ohne konkrete
                Anhaltspunkte einer Rechtsverletzung nicht zumutbar. Bei Bekanntwerden von
                Rechtsverletzungen werden wir derartige Links umgehend entfernen.
            </p>

            <strong>Urheberrecht:</strong>
            <br>
            <p align="justify">
                Die durch die Seitenbetreiber erstellten Inhalte und Werke auf diesen Seiten unterliegen dem
                deutschen Urheberrecht. Die Vervielfältigung, Bearbeitung, Verbreitung und jede Art der
                Verwertung außerhalb der Grenzen des Urheberrechtes bedürfen der schriftlichen
                Zustimmung des jeweiligen Autors bzw. Erstellers. Downloads und Kopien dieser Seite sind
                nur für den privaten, nicht kommerziellen Gebrauch gestattet.
            </p>
            <br>
        </div>

    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import NavigationBar from "@/components/NavigationBar.vue";

    @Component({
        components: {NavigationBar}
    })

    export default class ImpressumView extends Vue {
    }
</script>

<style scoped>

    .body {
        text-align: left;
    }
    h1 {
        margin-top: 5vw;
        margin-left: 30%;
    }

    .contact-area {
        margin-top: 1vw;
        margin-left: 30%;
    }

    .liability-area {
        margin-top: 1vw;
        margin-left: 30%;
        margin-right: 30%;
    }

</style>