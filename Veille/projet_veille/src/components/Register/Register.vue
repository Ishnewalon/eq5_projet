<template>
  <div class="text-center ">
    <div>
      <h2>
        <ins>Inscription</ins>
      </h2>
      <div>
        <button v-bind:class="getCurrentRegister()==='RegisterStudent' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('student')">Étudiant
        </button>
        <button v-bind:class="getCurrentRegister()==='RegisterMonitor' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('monitor')">Moniteur
        </button>
        <button v-bind:class="getCurrentRegister()==='RegisterSupervisor' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('supervisor')">Superviseur
        </button>
        <component v-bind:is="getCurrentRegister()"></component>
      </div>
    </div>
  </div>
</template>

<style scoped>

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
</style>
<script>

import RegisterStudent from './RegisterStudent'
import RegisterMonitor from './RegisterMonitor'
import RegisterSupervisor from './RegisterSupervisor'

export default {
  name: 'register',
  data: function () {
    return {currentRegister: 'RegisterStudent'}
  },
  components: {
    RegisterStudent,
    RegisterMonitor,
    RegisterSupervisor
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
        this.setCurrentRegister('RegisterStudent')
        return this.currentRegister
      }
      if (current === "monitor") {
        this.setCurrentRegister('RegisterMonitor')
        return this.currentRegister
      }
      if (current === "supervisor") {
        this.setCurrentRegister('RegisterSupervisor')
        return this.currentRegister
      }
    }
  }
}
</script>

