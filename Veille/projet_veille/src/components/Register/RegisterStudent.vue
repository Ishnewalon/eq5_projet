<template>
  <div>
    <form>
      <div>
        <h2>Inscription Étudiant</h2>
      </div>
      <div>
        <input name="firstName" type="text" placeholder="Prénom" required/>
        <input name="lastName" type="text" placeholder="Nom" required/>
        <input name="email" type="email" placeholder="E-mail" required/>
        <input name="username" type="number" placeholder="Téléphone" required/>
        <input name="password" type="password" placeholder="Mot de passe" required/>
      </div>
      <div>
        <button>S'inscrire</button>
      </div>
    </form>
  </div>
</template>

<style>
input{
  width: 80%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
</style>