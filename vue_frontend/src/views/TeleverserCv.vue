<template>
  <div class="container">
    <div class="bg-secondary p-3 rounded">
      <label>Fichier</label>
      <input type="file" class="form-control" v-on:change="setFile($event)" accept="application/pdf">
      <button v-on:click="uploadCv" class="fw-bold btn btn-primary w-100 mt-2 rounded">Submit</button>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";

import {uploadFile} from "../services/curriculum-service";
import authService from "../services/auth-service";

export default {
  name: "TeleverserCv",
  data(){
    return {
      cv: null
    };
  },
  methods:{
    setFile:function(event){
      this.cv = event.target.files[0];
    },
    uploadCv:function(){
      if(this.cv !== undefined && this.cv != null) {
        uploadFile(this.cv, authService.getUserId()).then(() => {
          Swal.fire({
            title: "Fichier téléversé",
            text: "Votre fichier a bien été téléversé",
            icon: "success"
          });
        });
      }else{
        Swal.fire({
          title: "Erreur",
          text: "Vous devez choisir un fichier",
          icon: "error"
        });
      }
    }

  }
}
</script>

<style scoped>

</style>
