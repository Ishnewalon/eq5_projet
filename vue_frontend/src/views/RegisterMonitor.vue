<template>
  <div class="container bg-dark p-4 rounded text-white">
    <h2>Monitor Register</h2>
    <div class="form-group mt-2">
      <label for="firstName">First Name</label>
      <input type="text" class="form-control" id="firstName" v-model="firstName" placeholder="Enter first name">
    </div>
    <div class="form-group mt-2">
      <label for="lastName">Last Name</label>
      <input type="text" class="form-control" id="lastName" v-model="lastName" placeholder="Enter last name">
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
      <label for="address">Address</label>
      <input type="text" class="form-control" id="address" v-model="address" placeholder="Address">
    </div>
    <div class="form-group mt-2">
      <label for="city">City</label>
      <input type="text" class="form-control" id="city" v-model="city" placeholder="City">
    </div>
    <div class="form-group mt-2">
      <label>Departement</label>
      <select id="dep" class="form-select" v-model="department">
        <option>informatique</option>
        <option>Arts et cinéma</option>
      </select>
    </div>
    <div class="form-group">
      <label for="zip">Code postal</label>
      <input type="text" class="form-control" id="zip" v-model="postalCode" placeholder="Code postal">
    </div>
    <button class="btn rounded bg-success mt-3 text-white fw-bold w-100" type="button" v-on:click="register">Submit
    </button>
  </div>
</template>

<script>
import authService from "@/services/auth-service";
import {MonitorModel} from "@/models/User";
import Swal from "sweetalert2";

export default {
  name: "RegisterMonitor",
  data() {
    return {
      email: '',
      password: '',
      lastName: '',
      firstName: '',
      phone: '',
      address: '',
      city: '',
      postalCode: '',
      department: ''
    };
  },
  methods: {
    register() {
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

      if (allFieldsFilled) {
        let monitor = new MonitorModel(this.email, this.password, this.lastName, this.firstName, this.phone, this.address, this.city, this.postalCode, this.department);
        authService.signupMonitor(monitor);
      }

    }
  }
}
</script>

<style scoped>

</style>
