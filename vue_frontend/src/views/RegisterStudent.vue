<template>
  <div class="container bg-dark p-4 rounded text-white">
    <h2>Student Register</h2>
    <div class="form-group mt-2">
      <label for="firstName">First Name</label>
      <input id="firstName" v-model="firstName" class="form-control" placeholder="Enter first name" type="text">
    </div>
    <div class="form-group mt-2">
      <label for="matricule">Matricule</label>
      <input id="matricule" v-model="matricule" class="form-control" placeholder="Enter matricule" type="text">
    </div>
    <div class="form-group mt-2">
      <label for="lastName">Last Name</label>
      <input id="lastName" v-model="lastName" class="form-control" placeholder="Enter last name" type="text">
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
      <label for="address">Address</label>
      <input id="address" v-model="address" class="form-control" placeholder="Address" type="text">
    </div>
    <div class="form-group mt-2">
      <label for="city">City</label>
      <input id="city" v-model="city" class="form-control" placeholder="City" type="text">
    </div>
    <div class="form-group mt-2">
      <label>Departement</label>
      <select v-model="department" class="form-select">
        <option>informatique</option>
        <option>Arts et cinéma</option>
      </select>
    </div>
    <div class="form-group mt-2">
      <label for="zip">Code postal</label>
      <input id="zip" v-model="postalCode" class="form-control" placeholder="Code postal" type="text">
    </div>
    <button class="btn rounded bg-success mt-3 text-white fw-bold w-100" type="button" v-on:click="submit">Submit
    </button>
  </div>
</template>

<script>
import authService from "@/services/auth-service";
import {Student} from "@/models/User";
import Swal from "sweetalert2";

export default {
  name: "RegisterStudent",
  data() {
    return {
      email: '',
      password: '',
      lastName: '',
      firstName: '',
      phone: '',
      matricule: '',
      department: '',
      address: '',
      city: '',
      postalCode: '',
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
          break;
        }
      }

      if (allFieldsFilled) {
        let student = new Student(this.email, this.password, this.lastName, this.firstName, this.phone, this.matricule, this.department, this.address, this.city, this.postalCode);
        authService.signupStudent(student);
      }
    }
  }
}
</script>

<style scoped>

</style>
