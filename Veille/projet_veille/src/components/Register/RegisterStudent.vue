<template>
  <div>
    <form>
      <div>
        <h2>Inscription Étudiant</h2>
      </div>
      <div>
        <input name="matricule" v-model.lazy="student.matricule" type="text" placeholder="Matricule" required/>
        <input name="firstName" v-model.lazy="student.firstName" type="text" placeholder="Prénom" required/>
        <input name="lastName" v-model.lazy="student.lastName" type="text" placeholder="Nom" required/>
        <input name="email" v-model.lazy="student.email" type="email" placeholder="E-mail" required/>
        <input name="phone" v-model.lazy="student.phone" type="text" placeholder="Téléphone" required/>
        <input name="password" v-model.lazy="student.password" type="password" placeholder="Mot de passe" required/>
      </div>
      <div>
        <br/>
        <button v-on:click='registerStudent()'>S'inscrire</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
import {createRouter, createWebHistory} from "vue-router";
import router from "../../router";
import Home from "../HelloWorld"

export default {
  name: 'RegisterStudent',
  data: function () {
    return {
      student: {
        email: '',
        password: '',
        lastName: '',
        firstName: '',
        phone: '',
        matricule: '',
        department: 'informatique',
      }
    }
  },
  methods: {
    registerStudent(error) {
      if (this.verification()) {
        error.preventDefault();
        axios.post("http://localhost:8181/student/signup", this.student)
            .then((response) => {
              sessionStorage.setItem(this.student.email, JSON.stringify(response.data));
              createRouter({
                history: createWebHistory,
                routes: [{path: `/home`, component: Home}]
              })
              router.push({path: `/home`})
            })
            .catch((error) => {
              console.log(error)
            });
      }
    },
    verification() {
      if (this.student.matricule.length !== 7) {
        alert("Le matricule doit contenir 7 chiffres");
        return false;
      }
      if (!this.student.firstName) {
        alert("Le champs prénom est vide")
        return false
      }
      if (!this.student.lastName) {
        alert("Le champs nom est vide")
        return false
      }
      if (!this.student.email) {
        alert("Le champs courriel est vide")
        return false
      }
      if (!this.student.phone) {
        alert("Le champs numéro de téléphone est vide")
        return false
      }
      if (!this.student.firstName.match(/^[a-zA-Z\-\s]+$/)) {
        alert("Le champs prénom est invalide")
        return false;
      }
      if (!this.student.lastName.match(/^[a-zA-Z\-\s]+$/)) {
        alert("Le champs nom est invalide")
        return false;
      }
      return true;
    }
  },

}
</script>

<style scoped>
input {
  width: 80%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  background-color: transparent;
  border: 1px solid black;
  box-sizing: border-box;
  color: #00132C;
  font-family: "Avenir Next LT W01 Bold", sans-serif;
  font-size: 16px;
  font-weight: 700;
  line-height: 24px;
  padding: 16px 23px;
  position: relative;
  text-decoration: none;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

button:hover,
button:active {
  outline: 0;
}

button:hover {
  background-color: transparent;
  cursor: pointer;
}

button:before {
  background-color: #73807DBC;
  content: "";
  height: calc(100% + 3px);
  position: absolute;
  right: -7px;
  top: -9px;
  transition: background-color 300ms ease-in;
  width: 100%;
  z-index: -1;
}

button:hover:before {
  background-color: #40FFA9BC;
}

@media (min-width: 768px) {
  button {
    padding: 16px 32px;
  }
}
</style>