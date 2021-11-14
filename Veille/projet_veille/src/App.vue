<template>
    <div id="nav">
        <router-link to="/home">Home</router-link>
        <span v-if="getUserRole() === 'etudiant'">
            |
            <router-link to="/upload-cv"> Televerser Cv</router-link>
        </span>
        <span v-if="!getUserRole()">
            |
            <router-link to="/register"> Inscription</router-link>
            |
            <router-link to="/login"> Se connecter</router-link>
        </span>
        <span v-else>
            |
            <router-link v-on:click="logout" to="/register"> Se déconnecter</router-link>
        </span>

    </div>
    <router-view/>

</template>

<script>
export default {
    name: 'App',
    data() {
    },
    methods: {
        logout: function () {
            sessionStorage.removeItem("currentUser");
            this.$router.push('/register');
            // location.reload();
        },
        getUserRole: function () {
            if (sessionStorage.getItem("currentUser") == null) {
                return false;
            }
            return JSON.parse(sessionStorage.getItem("currentUser")).role;
        }
    }

}
</script>

<style>
#app {
    font-family: 'Roboto', 'Times New Roman', sans-serif;
    text-align: center;
}

#nav {
    padding: 30px;
    font-weight: bold;
    font-size: 30px;
    color: black;
    text-decoration: none;
}

#nav a {
    font-weight: bold;
    font-size: 30px;
    color: black;
}

#nav a.router-link-exact-active {
    color: #42b983;
}
</style>