<template>
  <div class="container">
    <div class="bg-black text-white rounded p-3">
      <h4 class="text-center">Créer un compte</h4>
      <div class="d-flex flex-column align-items-center justify-content-center">
        <a>
          <router-link to="/register/supervisor">Superviseur</router-link>
        </a>
        <a>
          <router-link to="/register/monitor">Monitor</router-link>
        </a>
        <a>
          <router-link to="/register/student">Student</router-link>
        </a>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Register"
}
</script>

<style scoped>

</style>
