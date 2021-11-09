<template>
  <div class="container" >
    <div class="bg-secondary p-4 rounded">
      <h3 class="text-white">Choisissez votre cv</h3>
      <input type="file" class="form-control" v-on:change="setFile($event)" accept="application/pdf">
      <div @dragover.prevent @drop.prevent >
        <div class="bg-dark mt-2 p-3 rounded" @drop.prevent="dragAndDropResume">
          <h3 class="fw-bold text-center">Ou glissez votre cv ici...</h3>
          <div class="bg-dark border border-white mt-2 rounded">
            <p class="text-center my-2">{{ cv ? cv.name : 'Glissez votre cv ici'}}</p>
          </div>
        </div>
      </div>
      <button v-on:click="uploadCv" class="fw-bold btn btn-primary w-100 mt-2 rounded">Envoyez</button>
    </div>

    </div>
</template>

<script>
import Swal from "sweetalert2";

import {uploadFile} from "../services/curriculum-service";
import authService from "../services/auth-service";

export default {
  name: "TeleverserCv",
  data() {
    return {
      cv: null
    };
  },
  methods: {
    dragAndDropResume: function(event){
      alert('drag');
      this.cv = event.dataTransfer.files[0];
    },
    setFile: function (event) {
      this.cv = event.target.files[0];
    },
    uploadCv: function () {
      if (this.cv !== undefined && this.cv != null) {
        uploadFile(this.cv, authService.getUserId()).then(() => {
          Swal.fire({
            title: "Curriculum téléversé",
            text: "Votre curriculum vitae a bien été téléversé",
            icon: "success"
          });
        });
      } else {
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
