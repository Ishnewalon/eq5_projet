<template>
  <div class="container">
    <form action="" class="bg-dark p-4 rounded" @submit.prevent="login">
      <div class="form-group">
        <label>Type d'utilisateur</label>
        <select v-model="userType" id="" class="form-select">
          <option disabled selected>Choissisez un type d'utilisateur</option>
          <option>monitor</option>
          <option>student</option>
          <option>supervisor</option>
          <option>manager</option>
        </select>
      </div>
      <div class="form-group mt-2">
        <label for="email">Email</label>
        <input type="email" class="form-control" id="email" v-model="email">
      </div>
      <div class="form-group mt-2">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" v-model="password">
      </div>
      <button type="submit" class="btn bg-primary btn-primary mt-4 rounded w-100 fw-bold" @click.prevent="login">Login
      </button>
    </form>
  </div>
</template>

<script>
import AuthService from '../services/auth-service';
import Swal from "sweetalert2";

export default {
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
      userType: "",
      service: AuthService,
    };
  },
  methods: {
    login() {
      let allFieldsFilled = true;
      for (const prop in this) {
        if (prop === '' || this[prop] === '' || this[prop] === null) {
          Swal.fire({
            title: 'Oops...',
            text: 'Veuillez remplir tous les champs',
            icon: 'error'
          });
          allFieldsFilled = false;
          break;
        }
      }
      if (allFieldsFilled)
        this.service.signIn(this.userType, this.email, this.password);
    }
  }
}
</script>

<style scoped>

</style>
