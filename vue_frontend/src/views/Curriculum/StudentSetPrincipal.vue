<template>
  <table>
    <thead>
      <th>Principal</th>
      <th>Nom</th>
      <th>Supprimer</th>
    </thead>

    <tr v-for="(cv, index) in this.dto.curriculumList" :key="index">
      <td v-html="getIcon(cv)"></td>
      <td v-bind:class="{'text-danger' : !cv.isValid,  '':''}">
      <button class="link-button" @click.prevent="downloadCurriculum(cv.data, cv.name)">
      {{cv.name}}
      </button>
      </td>
      <td>
        <button class="link-button" v-on:click="deleteCv(cv)">
          Delete
        </button>
      </td>
    </tr>)}
  </table>
</template>

<script>
import {downloadFile, toPdfBlob} from "@/services/utility";
import {
  deleteCurriculumById,
  getAllCurriculumsByStudentWithPrincipal,
  setPrincipalCurriculum
} from "@/services/curriculum-service";
import authService from "@/services/auth-service";

export default {
  name: "StudentSetPrincipal",
  data(){
    return {
      curriculumsWithPrincipal: null,
    };
  },
  created() {
    getAllCurriculumsByStudentWithPrincipal(authService.getUserId())
        .then(curriculums => {
          curriculums.curriculumList.sort((a, b) => {
            if (b.isValid === null || (a.isValid === false && b.isValid === true))
              return 1;
            if (a.isValid === null || (a.isValid === true && b.isValid === false))
              return -1;
            return 0
          });
          this.dto = curriculums;
        })
        .catch(e => {
          this.dto = null;
          console.error(e);
        });
  },
  methods: {
    downloadCurriculum: function (data, name) {
      downloadFile(toPdfBlob(data), name);
    },
    getIcon : function(cv){
      if (this.isPrincipal(cv)) {
        return "cv par défaut"
      } else {
        if (cv.isValid)
          return <button class="link-button" v-on:click="this.setPrincipal(cv)">
            set principal
          </button>
        else if (cv.isValid === null)
          return <span class="bg-info fw-bold px-2">En attente de validation</span>
        else
          return <span class="bg-danger px-2">Invalide</span>
      }
    },
    setPrincipal: function(cv) {
     setPrincipalCurriculum(authService.getUserId(), cv.id).then(
          (success) => {
            if (success)
              this.dto.principal = cv;
          }
      );
    },
    isPrincipal: function(cv){
      return this.dto.principal &&
          cv.id === this.dto.principal.id;
    },
    deleteCv: function(cv){
      deleteCurriculumById(cv.id).then(
          (success) => {
            if (success)
              this.dto.curriculumList.splice(this.dto.curriculumList.indexOf(cv), 1);
          }
      );
    }
  }
}
</script>

<style scoped>

</style>
