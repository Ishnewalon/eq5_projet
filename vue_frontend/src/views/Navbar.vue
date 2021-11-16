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
    <div v-if="role === 'monitor'" class="d-inline">
      <a>
        <router-link to="/monitor/create_offer">Créer une offre</router-link>
      </a>
    </div>
    <div v-else-if="role === 'manager'" class="d-inline">
      <a>
        <router-link to="/manager/create_offer">Créer une offre</router-link>
      </a>
      <a>
        <router-link to="/manager/review_offers">Valider les offres</router-link>
      </a>
    </div>
  </nav>
</template>

<script>

export default {
  name: "Navbar",
  data(){
    return {
      role:''
    }
  },
  created() {
    this.role = localStorage.getItem('role') || '';
  }
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
