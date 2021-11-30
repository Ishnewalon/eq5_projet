<template>
  <div class='d-flex align-items-center justify-content-center flex-column'>
    <h2>Contrat de {{ studentFullName(contract.student) }}</h2>
    <div v-if="this.pdf_file">
      <pdf :src="this.url"></pdf>
    </div>
  </div>
</template>

<script>
import {toPdfBlob} from "@/services/utility";
import pdf from "vue-pdf";

export default {
  name: "ContractSigned",
  data() {
    return {
      pdf_file: null,
      url: null
    };
  },
  components: {
    pdf
  },
  props: {
    contract: {
      type: Object,
      required: true
    }
  },
  created() {
    this.pdf_file = toPdfBlob(contract.contractPDF);
    this.url = window.URL.createObjectURL(this.pdf_file);
    window.URL.revokeObjectURL(this.url);
  },
  methods: {
    studentFullName(student) {
      return `${student.firstName} ${student.lastName}`;
    }
  }
}
</script>

<style scoped>

</style>
