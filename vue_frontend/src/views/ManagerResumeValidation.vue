<template>
  <div class='container'>
    <div v-if="curriculumList.length > 0">
      <h2 class="text-center">Liste des étudiants</h2>
      <table class="table table-light bg-white table-striped table-borderless text-center rounded-3 shadow-lg">
        <thead class="table-dark bg-dark">
        <th>Nom</th>
        <th>Prénom</th>
        <th>Télécharger CV</th>
        <th>Validation</th>
        </thead>
        <tbody>
        <tr v-for="(cv, index) in curriculumList" :key="index">
          <td>{{ cv.student.lastName }}</td>
          <td>{{ cv.student.firstName }}</td>
          <td>
            <button class="btn btn-primary" v-on:click="downloadStudentCv(cv)">Télécharger Cv</button>
          </td>
          <td>
            <div class="btn-group">
              <button class="btn btn-outline-success"
                      v-on:click="validateCv(cv.id, true)">Valide
              </button>
              <button class="btn btn-outline-danger"
                      v-on:click="validateCv(cv.id, false)">Invalide
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <h2 class="text-center text-white bg-secondary p-3">Aucun c.v étudiant à valider...</h2>
    </div>
  </div>
</template>

<script>
import {downloadCV, getCurriculumWithInvalidCV, validateCV} from "@/services/curriculum-service";
import Swal from "sweetalert2";

export default {
  name: "ManagerResumeValidation",
  data() {
    return {
      curriculumList: [],
    }
  },
  created() {
    this.getCurriculumList();
  },
  methods: {
    getCurriculumList() {
      getCurriculumWithInvalidCV().then(response => {
        this.curriculumList = response;
      });
    },
    validateCv(id, isValid) {
      validateCV(id, isValid);
    },
    getFullName(student) {
      const {lastName, firstName} = student;
      return `${lastName}_${firstName}`;
    },
    downloadStudentCv(cv) {
      const id = cv.id;
      downloadCV(id).then(
          blob => {
            let myUrl = URL.createObjectURL(blob);

            let myFilename = `${this.getFullName(cv.student)}_${id}.pdf`;

            const a = document.createElement('a')
            a.href = myUrl
            a.download = myFilename;
            a.click();
            URL.revokeObjectURL(myUrl);
            Swal.fire({title: 'Téléchargement en cours', icon: 'success'}).then()
          }
      );
    }
  }
}
</script>

<style scoped>

</style>
