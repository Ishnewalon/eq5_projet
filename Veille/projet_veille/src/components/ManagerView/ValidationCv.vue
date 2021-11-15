<template>
    <div>
        <ul>
            <li v-for="(item, i) in this.liste" :key="i">
                {{ item.student.lastName }} {{ item.student.firstName }}
                <button class="btn btn-outline-success"
                        v-on:click="validateCv(item.id, true)">Valide
                </button>
                <button class="btn btn-outline-danger"
                        v-on:click="validateCv(item.id, false)">Invalide
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
    name: "ValidationCv",
    data() {
        return {
            liste: [{}],
            msg: "",
            msgNb: ""
        }
    },
    methods: {
        getListeCv: function () {
            axios.get('http://localhost:8181/curriculum/invalid/students')
                .then(response => {
                    this.liste = response.data;
                    this.checkListe();
                })
                .catch(error => {
                    console.log(error);
                });
        },
        validateCv: function (id, valid) {
            axios.post('http://localhost:8181/curriculum/validate', {id, valid})
                .then(response => {
                    this.msg = response.data.message;
                    this.getListeCv();
                })
                .catch(error => {
                    console.log(error);
                });
        },
        checkListe: function () {
            if (this.liste.length === 0) {
                this.msgNb = "Aucun CV à valider";
            } else {
                this.msgNb = "Encore " + this.liste.length + " CV à valider";
            }
        }
    },
    created() {
        this.getListeCv();

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
    border: 2px solid black;
    padding: 15px;
    border-radius: 50%;
    text-decoration: none;
    cursor: pointer;
    margin-left: 10px;
    background: rgba(64, 255, 169, 0.74);
}

</style>