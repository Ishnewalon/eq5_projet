<template>
  <form @submit.prevent="submit">
    <div class="form-group">
      <label for="firstName">First Name</label>
      <input type="text" class="form-control" id="firstName" v-model="firstName" placeholder="Enter first name">
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
      <label>Departement</label>
      <select id="dep" v-model="department">
        <option>informatique</option>
        <option>Arts et cinéma</option>
      </select>
    </div>
    <button type="submit" @click.prevent="register">Submit</button>
  </form>
</template>

<script>
import {Supervisor} from "@/models/User";
import authService from "@/services/auth-service";
import router from "@/router";

export default {
  name: "RegisterSupervisor",
  data(){
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
    submit(){
      let allFieldsFilled = true;
      Object.values(this.data).forEach(value => {
        if(value === ''){
          alert('Please fill all the fields');
          allFieldsFilled = false;
        }
      });

      if(this.matricule.length !== 5){
        alert('Matricule must be 5 characters long');
        allFieldsFilled = false;
        return;
      }

      if(allFieldsFilled){
        let supervisor = new Supervisor(this.email, this.password, this.lastName, this.firstName, this.phone, this.matricule, this.department);

        authService.signupSupervisor(supervisor);
        router.push("/login")
      }
    }
  }
}
</script>

<style scoped>

</style>
