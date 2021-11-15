<template>
    <div>
        <h3>Cliquez ici pour televerser votre cv</h3>
        <input accept="application/pdf" type="file" v-on:change="setFile($event)">
        <button v-on:click="uploadFile">Envoyez votre cv</button>
    </div>
</template>

<script>

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
        uploadFile: async function () {
            if (this.file) {
                let file = new FormData();
                file.append('file', this.file);
                const response = await fetch(`http://localhost:8181/curriculum/upload?id=${this.user.id}`, {
                    mode: 'cors',
                    method: "POST",
                    body: file
                });
                return response.json();
            } else {
                alert('Veuillez choisir votre Cv');
            }
        }
    }
}
</script>

<style scoped>
</style>