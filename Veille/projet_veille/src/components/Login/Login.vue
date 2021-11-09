<template>
  <div>
    <div>
      <div>
        <h1>Connexion</h1>
      </div>
      <div>
        <form>
          <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" v-model="email" placeholder="Email">
          </div>
          <div class="form-group">
            <label for="password">Mot de passe</label>
            <input type="password" class="form-control" id="password" v-model="password" placeholder="Mot de passe">
          </div>
          <button type="submit" class="btn btn-primary">Connexion</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login"
}
</script>

<style scoped>

</style>