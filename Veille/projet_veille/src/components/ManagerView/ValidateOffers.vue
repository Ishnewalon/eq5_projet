<template>
    <div>
        <ul>
            <li v-for="(offer, i) in this.liste" :key="i">
                {{ offer.title }}: {{ offer.description }}
                <router-link :to="{ path: '/offer/detail', query: { offer: offer }}">
                    <button class="btn btn-details">Détails</button>
                </router-link>
                <button class="btn btn-validate"
                        v-on:click="validateOffer(offer.id, true)">Valide
                </button>
                <button class="btn btn-invalidate"
                        v-on:click="validateOffer(offer.id, false)">Invalide
                </button>
                <hr>
            </li>
        </ul>
    </div>
    <p><i>{{ msg }}</i></p>
    <p><i>{{ msgNb }}</i></p>
</template>

<script>
import axios from "axios";

export default {
    name: "ValidationOffers",
    data() {
        return {
            liste: [{}],
            msg: "",
            msgNb: "",
            pluriel: "",
        }
    },
    methods: {
        getListOffers: function () {
            axios.get('http://localhost:8181/offers/not_validated')
                .then(response => {
                    this.liste = response.data;
                    this.checkListe();
                })
                .catch(error => {
                    console.log(error);
                });
        },
        validateOffer: function (id, valid) {
            axios.post('http://localhost:8181/offers/validate', {id, valid})
                .then(response => {
                    this.msg = response.data.message;
                    this.getListOffers();
                })
                .catch(error => {
                    console.log(error);
                });
        },
        checkListe: function () {
            if (this.liste.length === 0) {
                this.msgNb = "Aucune offre à valider";
            } else {

                this.liste.length > 1 ? this.pluriel = "Offres à valider" : this.pluriel = "Offre à valider";
                this.msgNb = "Encore " + this.liste.length + " " + this.pluriel;
            }
        }
    },
    created() {
        this.getListOffers();
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
}
.btn-validate {
    background-color: green;
}
.btn-invalidate {
    background-color: red;
}
.btn-details{
    background-color: blue;
}
</style>