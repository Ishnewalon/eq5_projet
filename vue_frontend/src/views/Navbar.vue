<template>
  <nav class="bg-secondary mb-5 py-4">
    <a v-if="connected" href="javascript:void(0)" v-on:click="this.disconnect()">Déconnexion</a>
    <div v-else>
      <a>
        <router-link to="/login">Se connecter</router-link>
      </a>
      <a>
        <router-link to="/register">Créer un compte</router-link>
      </a>
    </div>
    <div v-if="role === 'monitor'" class="d-inline">
      <a>
        <router-link to="/monitor/create_offer">Créer une offre</router-link>
      </a>
      <a>
        <router-link to="/monitor/view_applied_students">Voir applicants</router-link>
      </a>
    </div>
    <div v-else-if="role === 'manager'" class="d-inline">
      <a>
        <router-link to="/manager/create_offer">Créer une offre</router-link>
      </a>
      <a>
        <router-link to="/manager/review_offers">Valider les offres</router-link>
      </a>
      <a>
        <router-link to="/manager/curriculums/validate">Valider les cv</router-link>
      </a>
      <a>
        <router-link to="/manager/start_contracts">Démarrage des contrats</router-link>
      </a>
    </div>
    <div v-else-if="role === 'student'" class="d-inline">
      <a>
        <router-link to="/student/view_offers">Voir les offres</router-link>
      </a>
      <a>
        <router-link to="/student/upload_resume">Téléverser cv</router-link>
      </a>
    </div>
  </nav>
</template>

<script>
import router from '../router';
import authService from "@/services/auth-service";

export default {
  name: "Navbar",
  data() {
    return {
      role: '',
      connected: false
    }
  },
  created() {
    this.role = localStorage.getItem('role') || '';
    this.connected = authService.isAuthenticated();
  },
  methods: {
    disconnect() {
      localStorage.clear();
      router.push('/login');
    }
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
