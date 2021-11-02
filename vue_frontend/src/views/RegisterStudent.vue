<template>
  <div class="form-group">
    <label for="firstName">First Name</label>
    <input type="text" class="form-control" id="firstName" v-model="firstName" placeholder="Enter first name">
  </div>
  <div class="form-group">
    <label>Matricule</label>
    <input type="number" class="form-control" v-model="matricule" placeholder="Enter matricule">
  </div>
  <div class="form-group">
    <label for="lastName">Last Name</label>
    <input type="text" class="form-control" id="lastName" v-model="lastName" placeholder="Enter last name">
  </div>
  <div class="form-group">
    <label for="email">Email address</label>
    <input type="email" class="form-control" id="email" v-model="email" placeholder="Enter email">
  </div>
  <div class="form-group">
    <label for="password">Password</label>
    <input type="password" class="form-control" id="password" v-model="password" placeholder="Password">
  </div>
  <div class="form-group">
    <label for="phone">Phone</label>
    <input type="text" class="form-control" id="phone" v-model="phone" placeholder="Phone">
  </div>
  <div class="form-group">
    <label for="address">Address</label>
    <input type="text" class="form-control" id="address" v-model="address" placeholder="Address">
  </div>
  <div class="form-group">
    <label for="city">City</label>
    <input type="text" class="form-control" id="city" v-model="city" placeholder="City">
  </div>
  <div class="form-group">
    <label>Departement</label>
    <select id="dep" v-model="department">
      <option>informatique</option>
      <option>Arts et cinéma</option>
    </select>
  </div>
  <div class="form-group">
    <label for="zip">Code postal</label>
    <input type="text" class="form-control" id="zip" v-model="postalCode" placeholder="Code postal">
  </div>
  <button type="button" @click.prevent="submit">Submit</button>

</template>

<script>
import router from "@/router";
import authService from "@/services/auth-service";
import {Student} from "@/models/User";

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
    submit(){
      let allFieldsFilled = true;
      for(const prop in this) {
        if (prop === '' || this[prop] === '' || this[prop] === null) {
          alert('Please fill all the fields');
          allFieldsFilled = false;
          break;
        }
      }

      if(allFieldsFilled){
        let student = new Student(this.email, this.password, this.lastName, this.firstName, this.phone, this.matricule, this.department, this.address, this.city, this.postalCode);

        authService.signupStudent(student);
        router.push("/login")
      }
    }
  }
}
</script>

<style scoped>

</style>
