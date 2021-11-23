<template>
    <div>
        <h1><i>{{ this.offer.title }}</i></h1>
        <h2>{{ this.offer.description }}</h2>
        <h2>L'offre commence le {{ this.offer.dateDebut }} et se termine {{ this.offer.dateFin }}</h2>
        <div v-if="checkRole() === 'manager'">
            <router-link to="/ValidationOffers">
                <button>Retour</button>
            </router-link>
        </div>
        <div v-if="checkRole() === 'etudiant'">
            <router-link to="/ApplyToOffer">
                <button>Revenir</button>
            </router-link>
        </div>
    </div>

</template>

<script>

export default {
    name: "DetailsOffer",
    data() {
        return {
            offer: {},
            user: sessionStorage.getItem("currentUser") != null ? JSON.parse(sessionStorage.getItem("currentUser")) : {},
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
        checkRole: function () {
            return this.user.role;
        }
    },
    created() {
        this.getOffer();
    },
}
</script>

<style scoped>
p {
    color: black;
    font-size: x-large;
}

li {
    list-style-type: none;
    margin-bottom: 10px;
    font-size: x-large;
}

button {
    font-size: 15px;
    color: antiquewhite;
    border: 2px solid black;
    padding: 15px;
    border-radius: 50%;
    text-decoration: none;
    cursor: pointer;
    margin-left: 10px;
    background-color: #42b983;
}
</style>