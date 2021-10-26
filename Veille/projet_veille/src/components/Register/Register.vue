<template>
  <div>
    <form>
      <h1> {{ titre }}</h1>
      <div class="form-group row">

        <div class="col-md-6">
          <label>Prénom</label>
          <div class="input-group">
            <input name="firstName" placeholder="Prenom" class="form-control" type="text"/>
          </div>
        </div>

        <div class="col-md-6">
          <label>Nom</label>
          <div>
            <div class="input-group">
              <input name="lastName" placeholder="Nom" class="form-control" type="text"/>
            </div>
          </div>
        </div>
      </div>

      <div class="form-group">
        <label>E-Mail</label>
        <div class="input-group">
          <input name="email" placeholder="Adresse E-mail" class="form-control" type="email"/>
        </div>
      </div>

      <div class="form-group">
        <label>Téléphone</label>
        <div class="input-group">
          <input name="contact_no" placeholder="000 000 000" class="form-control" type="tel" />
        </div>
      </div>

      <div class="form-group text-center">
        <label/>
        <div>
          <button class="btn btn-primary" type="button" >S'inscrire</button>
        </div>
      </div>

    </form>
  </div>
</template>


<script>
export default {
  name: 'Register',
  props: {
    titre: String
  }
}
</script>


<style scoped>
label {
  margin-top: 20px;
  margin-bottom: 10px;
}

button {
  margin-right: 10px;
}

</style>
