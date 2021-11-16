<template>
  <div class=" p-3 mt-5 border-left border-right border-light">
    <div class="row">
      <div class="col-12 col-sm-6">
        <div class="d-flex justify-content-center align-items-center flex-column p-3 shadow h-100">
          <h4 class="p-2 rounded bg-secondary fw-bold text-white">{{
              `${student.firstName}, ${student.lastName}`
            }} </h4>
          <div class="d-flex justify-content-center align-items-center badge bg-primary text-wrap h2">
            <a class="btn fw-bold ms-2 text-white" target="_blank" v-on:click="openFile">{{ student.fileName }}</a>
          </div>
        </div>
      </div>
      <div class="col-12 col-sm-6">
        <PreviewOffer :offer="student.offerDTO"/>
      </div>
    </div>
  </div>
</template>

<script>
import CurriculumDto from "../models/CurriculumDto";
import PreviewOffer from "../views/PreviewOffer";

export default {
  name: "PreviewStudent",
  props: {
    student: {
      type: CurriculumDto
    }
  },
  components: {
    PreviewOffer
  },
  methods: {
    openFile() {
      const decodedChars = atob(this.student.file);
      const byteNums = new Array(decodedChars.length);
      for (let i = 0; i < decodedChars.length; i++)
        byteNums[i] = decodedChars.charCodeAt(i);

      const blob = new Blob([new Uint8Array(byteNums), {type: 'application/pdf'}]);

      let url = window.URL.createObjectURL(blob);

      const a = document.createElement('a')
      a.href = url
      a.download = this.student.fileName;
      a.click();
      URL.revokeObjectURL(url)
    }
  }
}
</script>

<style scoped>

</style>
