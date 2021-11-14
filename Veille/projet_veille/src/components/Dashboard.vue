<template>
    <h1>VOUS ETES CONNECTE EN TANT QUE <span> {{ this.user.role }}</span></h1>
    <h2>Bienvenu <span> {{ this.user.lastName }} {{ this.user.firstName }}</span></h2>
    <button v-on:click="logout"> Se déconnecter</button>
    <br><br>
    <div v-if="checkRole() === 'moniteur'">
        <router-link to="/monitor-view">Liste étudiants</router-link>
    </div>
</template>

<script>
export default {
    name: "Dashboard",
    data: function () {
        return {
            user: sessionStorage.getItem("currentUser") != null ? JSON.parse(sessionStorage.getItem("currentUser")) : {},
        }
    },
    methods: {
        logout: function () {
            sessionStorage.removeItem("currentUser");
            this.$router.push("/register");
        },
        checkRole: function () {
            return this.user.role;
        }
    }
}
</script>

<style scoped>
h2 span {
    text-transform: capitalize;
}

h1 span {
    color: rgba(66, 222, 152, 0.74);
    text-transform: uppercase;
}
</style>