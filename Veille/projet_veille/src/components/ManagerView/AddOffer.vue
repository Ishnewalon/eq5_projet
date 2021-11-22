<template>
    <div>
        <form>
            <div>
                <h2>Créer une offre</h2>
            </div>
            <div>
                <input name="titre" v-model.lazy="offerDTO.title" type="text" placeholder="Titre" required/>
                <input name="description" v-model.lazy="offerDTO.description" type="text" placeholder="Description"
                       required/>
                <select name="session" v-model.lazy="offerDTO.idSession" required>
                    <option disabled value="">Choisissez une session</option>
                    <option v-for="(session, i) in this.listSession" :value="session.id" :key="i">{{ session.typeSession }} {{ session.year }}</option>
                </select>
                <input name="address" v-model.lazy="offerDTO.address" type="text" placeholder="Adresse de la compagnie"
                       required/>


                <input name="email" v-model.lazy="offerDTO.creator_email" type="email" placeholder="E-mail du moniteur"
                       required/>
                <input name="salaire" v-model.lazy="offerDTO.salary" type="number"
                       placeholder="Salaire"
                       required/>
            </div>
            <div>
                <br/>
                <button v-on:click="addOffer">Créer</button>
            </div>
        </form>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "AddOffer",
    data() {
        return {
            offerDTO: {
                title: "",
                department: "informatique",
                description: "",
                address: "",
                creator_email: "",
                salary: "",
                idSession: "",
            },
            listSession: [{}],
        }
    },
    methods: {
        addOffer() {
            axios.post("http://localhost:8181/offers/add", this.offerDTO)
                .then(() => {
                    alert("Offre créée avec succès");
                })
                .catch(error => {
                    alert(error.response.data.message);
                });
        },

        getListSession() {
            axios.get("http://localhost:8181//sessions").then((response) => {
                this.listSession = response.data;
                console.log(response);
                console.log(this.listSession);
            })
                .catch(error => {
                    console.log(error);
                });
        }
    },
    mounted: function () {
        this.getListSession();
    },
    created() {
        this.getListSession();
    }
}
</script>

<style scoped>
input, select {
    width: 80%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

button {
    background-color: transparent;
    border: 1px solid black;
    box-sizing: border-box;
    color: #00132C;
    font-family: "Avenir Next LT W01 Bold", sans-serif;
    font-size: 16px;
    font-weight: 700;
    line-height: 24px;
    padding: 16px 23px;
    position: relative;
    text-decoration: none;
    user-select: none;
    -webkit-user-select: none;
    touch-action: manipulation;
}

button:hover,
button:active {
    outline: 0;
}

button:hover {
    background-color: transparent;
    cursor: pointer;
}

button:before {
    background-color: #73807DBC;
    content: "";
    height: calc(100% + 3px);
    position: absolute;
    right: -7px;
    top: -9px;
    transition: background-color 300ms ease-in;
    width: 100%;
    z-index: -1;
}

button:hover:before {
    background-color: #40FFA9BC;
}

@media (min-width: 768px) {
    button {
        padding: 16px 32px;
    }
}
</style>