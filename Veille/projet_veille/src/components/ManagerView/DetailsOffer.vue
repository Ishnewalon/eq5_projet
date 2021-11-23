<template>
    <div>
        <h1><i>{{ this.offer.title }}</i></h1>
        <h2>{{ this.offer.description }}</h2>
        <h2>L'offre commence le {{ this.offer.dateDebut }} et se termine {{ this.offer.dateFin }}</h2>
        <router-link to="/ValidationOffers">
            <button>Retour</button>
        </router-link>

    </div>
</template>

<script>

export default {
    name: "DetailsOffer",
    data() {
        return {
            offer: {},
        }
    },
    methods: {
        async getOffer() {
            const response = await fetch(`http://localhost:8181/offers/get_offer/` + this.$route.query.prop, {
                mode: 'cors',
                method: "GET",
            });
            this.offer = await response.json();
        },
    },
    created() {
        this.getOffer();
    },
}
</script>

<style scoped>

</style>