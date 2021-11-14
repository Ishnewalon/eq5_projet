<template>
    <div>
        <h3>Cliquez ici pour televerser votre cv</h3>
        <input accept="application/pdf" type="file" v-on:change="setFile($event)">
        <button v-on:click="uploadFile">Envoyez votre cv</button>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "TeleverserCv",
    data() {
        return {
            file: null,
            user: JSON.parse(sessionStorage.getItem("currentUser"))
        };
    },
    methods: {
        setFile: function (event) {
            this.file = event.target.files[0];
        },
        uploadFile: function () {
            if (this.file) {
                let file = new FormData();
                file.append('cv', this.file);
                let id = this.user.id;
                axios.post(`http://localhost:8181/curriculum/upload?id=${id}`, file)
                    .then(() => alert("Cv téléversé avec succès"));
            } else {
                alert('Veuillez choisir votre Cv');
            }
        }
    }
}
</script>

<style scoped>
</style>