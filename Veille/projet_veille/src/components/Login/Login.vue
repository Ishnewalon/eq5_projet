<template>
  <div>
    <div>
      <div>
        <h1>Connexion</h1>
      </div>
      <div>
        <button v-bind:class="getCurrentRegister()==='LoginStudent' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('student')">Étudiant
        </button>
        <button v-bind:class="getCurrentRegister()==='LoginMonitor' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('monitor')">Moniteur
        </button>
        <button v-bind:class="getCurrentRegister()==='LoginSupervisor' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('supervisor')">Superviseur
        </button>
      </div>
      <div>
        <form>
          <div class="form-group">
            <input type="email" class="form-control" id="email" v-model="email" placeholder="Email">
          </div>
          <div class="form-group">
            <input type="password" class="form-control" id="password" v-model="password" placeholder="Mot de passe">
          </div>
          <br/>
          <button type="submit" class="btn btn-primary">Connexion</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'login',
  data: function () {
    return {currentRegister: 'LoginStudent'}
  },
  methods: {
    getCurrentRegister() {
      return this.currentRegister
    },
    setCurrentRegister(current) {
      this.currentRegister = current
    },
    handleClick(current) {
      if (current === "student") {
        this.setCurrentRegister('LoginStudent')
        return this.currentRegister
      }
      if (current === "monitor") {
        this.setCurrentRegister('LoginMonitor')
        return this.currentRegister
      }
      if (current === "supervisor") {
        this.setCurrentRegister('LoginSupervisor')
        return this.currentRegister
      }
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
button {
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

/*form button {*/
/*  background-color: transparent;*/
/*  border: 1px solid black;*/
/*  box-sizing: border-box;*/
/*  color: #00132C;*/
/*  font-family: "Avenir Next LT W01 Bold", sans-serif;*/
/*  font-size: 16px;*/
/*  font-weight: 700;*/
/*  line-height: 24px;*/
/*  padding: 16px 23px;*/
/*  position: relative;*/
/*  text-decoration: none;*/
/*  user-select: none;*/
/*  -webkit-user-select: none;*/
/*  touch-action: manipulation;*/
/*}*/

/*form button:hover,*/
/*form button:active {*/
/*  outline: 0;*/
/*}*/

/*form button:hover {*/
/*  background-color: transparent;*/
/*  cursor: pointer;*/
/*}*/

/*form button:before {*/
/*  background-color: #73807DBC;*/
/*  content: "";*/
/*  height: calc(100% + 3px);*/
/*  position: absolute;*/
/*  right: -7px;*/
/*  top: -9px;*/
/*  transition: background-color 300ms ease-in;*/
/*  width: 100%;*/
/*  z-index: -1;*/
/*}*/

/*form button:hover:before {*/
/*  background-color: #40FFA9BC;*/
/*}*/

/*@media (min-width: 768px) {*/
/*  form button {*/
/*    padding: 16px 32px;*/
/*  }*/
/*}*/
</style>