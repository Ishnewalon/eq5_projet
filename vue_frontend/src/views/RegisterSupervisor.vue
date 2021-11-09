<template>
  <div class="container bg-dark p-4 rounded text-white">
    <h2>Supervisor Register</h2>
    <div class="form-group mt-2">
      <label for="firstName">First Name</label>
      <input id="firstName" v-model="firstName" class="form-control" placeholder="Enter first name" type="text">
    </div>
    <div class="form-group mt-2">
      <label for="lastName">Last Name</label>
      <input id="lastName" v-model="lastName" class="form-control" placeholder="Enter last name" type="text">
    </div>
    <div class="form-group mt-2">
      <label for="matricule">Matricule</label>
      <input id="matricule" v-model="matricule" class="form-control" placeholder="Enter matricule" type="text">
    </div>
    <div class="form-group mt-2">
      <label for="email">Email address</label>
      <input id="email" v-model="email" class="form-control" placeholder="Enter email" type="email">
    </div>
    <div class="form-group mt-2">
      <label for="password">Password</label>
      <input id="password" v-model="password" class="form-control" placeholder="Password" type="password">
    </div>
    <div class="form-group mt-2">
      <label for="phone">Phone</label>
      <input id="phone" v-model="phone" class="form-control" placeholder="Phone" type="text">
    </div>

    <div class="form-group mt-2">
      <label>Departement</label>
      <select v-model="department" class="form-select">
        <option>informatique</option>
        <option>Arts et cinéma</option>
      </select>
    </div>
    <button class="btn rounded bg-success mt-3 text-white fw-bold w-100" type="button" v-on:click="submit">Submit
    </button>
  </div>
</template>

<script>
import {Supervisor} from "@/models/User";
import authService from "@/services/auth-service";
import Swal from "sweetalert2";

export default {
  name: "RegisterSupervisor",
  data() {
    return {
      email: "",
      password: "",
      lastName: "",
      firstName: "",
      phone: "",
      matricule: "",
      department: 'informatique'
    };
  },
  methods: {
    submit() {
      let allFieldsFilled = true;
      for (const prop in this) {
        if (prop === '' || this[prop] === '' || this[prop] === null) {
          Swal.fire({
            title: 'Oops...',
            text: 'Veuillez remplir tous les champs',
            icon: 'error'
          });
          allFieldsFilled = false;
          console.log(this);
          break;
        }
      }

      if (allFieldsFilled) {
        let supervisor = new Supervisor(this.email, this.password, this.lastName, this.firstName, this.phone, this.matricule, this.department);
        authService.signupSupervisor(supervisor);
      }
    }
  }
}
</script>

<style scoped>

</style>
