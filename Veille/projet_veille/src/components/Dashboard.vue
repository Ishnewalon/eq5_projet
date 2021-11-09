<template>
  <h1>VOUS ETES CONNECTE EN TANT QUE <span> {{this.user.firstName}} {{this.user.lastName}}</span> </h1>
  <button v-on:click="logout"> Se déconnecter</button><br><br>
  <router-link to="/monitor-view">Liste étudiants</router-link>

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
    }
  }
}
</script>

<style scoped>

span{
  color: red;
  text-transform: uppercase;
}
</style>