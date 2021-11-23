<template>
    <div>
        <ul>
            <li v-for="(offer, i) in this.listOffers" :key="i">
                {{ offer.title }}: {{ offer.description }}
                <router-link :to="{ name: 'DetailsOffer', query: { prop : offer.id }}">
                    <button class="btn btn-details">Détails</button>
                </router-link>
                <button class="btn btn-apply"
                        v-on:click="applyToOffer(offer.id)">Soumettre ma candidature
                </button>
                <hr>
            </li>
        </ul>
        <p class="error">{{this.msgError}}</p>
        <p class="succes">{{this.msgSuccess}}</p>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "ApplyToOffer",
    data() {
        return {
            listOffers: [{}],
            user: sessionStorage.getItem("currentUser") != null ? JSON.parse(sessionStorage.getItem("currentUser")) : {},
            msgError: "",
            msgSuccess: ""
        }
    },
    methods: {
        getAllOffers() {
            axios.get('http://localhost:8181/offers/valid')
                .then(response => {
                    this.listOffers = response.data;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        async applyToOffer(id) {
            let offerDTO = {
                idOffer: id,
                idStudent: this.user.id
            };
            const response = await fetch(`http://localhost:8181/applications/apply`, {
                mode: 'cors',
                method: "POST",
                body: JSON.stringify(offerDTO),
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            if (response.status === 201) {
                this.msgSuccess = data.message;
                alert("Votre candidature a été soumise avec succès");
            } else {
                this.msgError = data.message;
                alert("Une erreur est survenue lors de la soumission de votre candidature");
            }
        }
    },
    created() {
        this.getAllOffers();
    }
}

</script>


<style scoped>
p {
    font-size: x-large;
}
.error {
    color: red;
}
.succes {
    color: green;
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

.btn-apply {
    background-color: #4CAF50;
}

.btn-details {
    background-color: blue;
}
</style>