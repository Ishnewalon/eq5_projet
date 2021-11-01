<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/register">Inscription</router-link>
  </div>
  <router-view/>
</template>

<style>
#app {
  font-family: 'Roboto', 'Times New Roman', sans-serif;
  text-align: center;
}
#nav {
  padding: 30px;
}
#nav a {
  font-weight: bold;
  font-size: 30px;
  color: black;
}
#nav a.router-link-exact-active {
  color: #42b983;
}
</style>