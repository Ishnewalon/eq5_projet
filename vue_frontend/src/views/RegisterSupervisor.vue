<template>
  <div class="container bg-dark p-4 rounded text-white">
    <h2>Supervisor Register</h2>
    <div class="form-group mt-2">
      <label for="firstName">First Name</label>
      <input type="text" class="form-control" id="firstName" v-model="firstName" placeholder="Enter first name">
    </div>
    <div class="form-group mt-2">
      <label for="lastName">Last Name</label>
      <input type="text" class="form-control" id="lastName" v-model="lastName" placeholder="Enter last name">
    </div>
    <div class="form-group mt-2">
      <label for="matricule">Matricule</label>
      <input type="text" class="form-control" id="matricule" v-model="matricule" placeholder="Enter matricule">
    </div>
    <div class="form-group mt-2">
      <label for="email">Email address</label>
      <input type="email" class="form-control" id="email" v-model="email" placeholder="Enter email">
    </div>
    <div class="form-group mt-2">
      <label for="password">Password</label>
      <input type="password" class="form-control" id="password" v-model="password" placeholder="Password">
    </div>
    <div class="form-group mt-2">
      <label for="phone">Phone</label>
      <input type="text" class="form-control" id="phone" v-model="phone" placeholder="Phone">
    </div>

    <div class="form-group mt-2">
      <label>Departement</label>
      <select id="dep" class="form-select" v-model="department">
        <option>informatique</option>
        <option>Arts et cinéma</option>
      </select>
    </div>
    <button class="btn rounded bg-success mt-3 text-white fw-bold w-100" type="button"  v-on:click="submit">Submit</button>
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
