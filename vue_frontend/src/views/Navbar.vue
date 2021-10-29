<template>
  <nav>
    <a><router-link to="/home">Home</router-link></a>
    <a><router-link to="/login">Se connecter</router-link></a>
    <a><router-link to="/register">Créer un compte</router-link></a>
    <a><router-link to="/dashboard">Dashboard</router-link></a>
  </nav>
</template>

<script>
export default {
  name: "Navbar"
}
</script>

<style scoped>
nav {
  text-align: center;
  padding:10px 30px;
}

nav a {
  font-weight: bold;
  color: #42b983;
  margin: 0 10px;
}
</style>
