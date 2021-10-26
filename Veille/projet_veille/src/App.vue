<template>
  <img alt="Vue logo" src="./assets/logo.png">
<!--  <HelloWorld msg="Welcome to Your Vue.js App"/>-->
  <Register msg="Bienvenue dans la page du l'inscription "/>
</template>

<script>
import Register from "./components/Register/Register";

export default {
  name: 'App',
  components: {
    // HelloWorld,
    Register
  }
}
</script>

<style>
#app {
  text-align: center;
}
</style>
