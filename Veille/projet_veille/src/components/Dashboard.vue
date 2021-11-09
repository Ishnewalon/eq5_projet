<template>
  <h1>VOUS ETES CONNECTE EN TANT QUE {{this.user.firstName}} {{this.user.lastName}} </h1>
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
}
</script>

<style scoped>

</style>