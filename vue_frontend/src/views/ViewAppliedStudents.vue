<template>
  <div class="container bg-dark p-3 rounded">
    <div class="d-flex justify-content-center align-items-center">
      <h2 class="text-center">Applicants</h2>
      <h2 class="ms-2">
        <span class="badge bg-secondary">{{ students.length }}</span>
      </h2>
    </div>
    <div v-if="students.length > 0">
      <div v-for="(student, index) in students" :key="index">
        <PreviewStudent :student="student"/>
      </div>
    </div>
    <div v-else class="d-flex justify-content-center align-items-center">
      <p class="text-center border border-white rounded p-2 mt-3">Aucun applicant à voir pour le moment</p>
    </div>
  </div>
</template>

<script>


import offerAppService from "../services/offerAppService";
import PreviewStudent from "../views/PreviewStudent";

export default {
  name: "ViewAppliedStudents",
  data: function () {
    return {
      students: []
    }
  },
  created() {
    const email = JSON.parse(localStorage.getItem('user')).email;
    offerAppService.getAllApplicants(email).then(students => (this.students = students));
    console.log(JSON.stringify(this.students));
  },
  components: {
    PreviewStudent
  }
}
</script>

<style scoped>

</style>
