<template>
  <div>
    <div>
      <div>
        <h1>Connexion</h1>
      </div>
      <div id="div">
        <button v-bind:class="getCurrentLogin()==='student' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('student')">Étudiant
        </button>
        <button v-bind:class="getCurrentLogin()==='monitor' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('monitor')">Moniteur
        </button>
        <button v-bind:class="getCurrentLogin()==='supervisor' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('supervisor')">Superviseur
        </button>
      </div>
      <div>
        <div class="form-group">
          <input type="email" class="form-control" id="email" v-model.lazy="this.user.email" placeholder="Email">
        </div>
        <div class="form-group">
          <input type="password" class="form-control" id="password" v-model.lazy="this.user.password"
                 placeholder="Mot de passe">
        </div>
        <br/>
        <button type="submit" v-on:click="login" class="btn btn-primary">Connexion</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {createRouter, createWebHistory} from "vue-router";
import router from "../../router";
import Dashboard from "../Dashboard";

export default {
  name: 'login',
  data: function () {
    return {currentLogin: 'student', user: {email: '', password: ''}}
  },
  methods: {
    getCurrentLogin() {
      return this.currentLogin
    },
    setCurrentLogin(current) {
      this.currentLogin = current
    },
    handleClick(current) {
      if (current === "student") {
        this.setCurrentLogin('student')
        return this.currentLogin
      }
      if (current === "monitor") {
        this.setCurrentLogin('monitor')
        return this.currentLogin
      }
      if (current === "supervisor") {
        this.setCurrentLogin('supervisor')
        return this.currentLogin
      }
    },
    login() {
      axios.get("http://localhost:8181/" + this.getCurrentLogin() + "/" + this.user.email + "/" + this.user.password)
          .then((response) => {
            if (response.status !== 200) {
              alert("Identifiants incorrects")
            }
            console.log(response)
            createRouter({
              history: createWebHistory,
              routes: [{path: `/dashboard`, component: Dashboard}]
            })
            router.push({path: `/dashboard`})
          })
          .catch((error) => {
            console.log(error)
          })
    }
  }
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

#div button {
  font-size: 15px;
  border: 2px solid black;
  padding: 15px;
  border-radius: 50%;
  text-decoration: none;
  cursor: pointer;
  margin-left: 10px;
}

.btnSelected {
  background: rgba(64, 255, 169, 0.74);
}

.btnSimple {
  background: rgba(115, 128, 125, 0.74);
}

.btn {
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

.btn:hover,
.btn:active {
  outline: 0;
}

.btn:hover {
  background-color: transparent;
  cursor: pointer;
}

.btn:before {
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

.btn:hover:before {
  background-color: #40FFA9BC;
}

@media (min-width: 768px) {
  .btn {
    padding: 16px 32px;
  }
}
</style>