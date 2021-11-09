<template>
  <nav class="bg-secondary mb-5 py-4">
    <a>
      <router-link to="/home">Home</router-link>
    </a>
    <a>
      <router-link to="/login">Se connecter</router-link>
    </a>
    <a>
      <router-link to="/register">Créer un compte</router-link>
    </a>
    <a>
      <router-link to="/dashboard">Dashboard</router-link>
    </a>
    <a>
      <router-link to="/monitor/create_offer">Créer une offre[moniteur]</router-link>
    </a>
    <a>
      <router-link to="/manager/create_offer">Créer une offre [Gestionnaire]</router-link>
    </a>
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
  padding: 10px 30px;
}

nav a {
  margin: 0 10px;
}
</style>
