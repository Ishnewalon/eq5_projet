<template>
    <div>
        <ul>
            <li v-for="(item, i) in this.liste" :key="i">
                {{ item.student.lastName }} {{ item.student.firstName }}
                <div class="btn-group">
                    <button class="btn btn-outline-success"
                            v-on:click="validateCv(item.id, true)">Valide
                    </button>
                    <button class="btn btn-outline-danger"
                            v-on:click="validateCv(item.id, false)">Invalide
                    </button>
                </div>
            </li>
        </ul>
    </div>
    <p><i>{{ msg }}</i></p>
</template>

<script>
import axios from "axios";

export default {
    name: "ValidationCv",
    data() {
        return {
            liste: [{}],
            msg: "",
        }
    },
    methods: {
        getListeCv: function () {
            axios.get('http://localhost:8181/curriculum/invalid/students')
                .then(response => {
                    this.checkListe();
                    this.liste = response.data;
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
                this.msg = "Aucun CV à valider";
            }
        }
    },
    mounted: function () {
        this.getListeCv();
    },
    created() {
        this.getListeCv();
    },
}
</script>

<style scoped>
p {
    color: black;

}
</style>