<template>
  <div className='d-flex align-items-center justify-content-center flex-column'>
    <h2>Contrat de {{ studentFullName(contract.student) }}</h2>
    <div v-if="this.pdf">
      <pdf :src="this.url"></pdf>
    </div>
  </div>
</template>

<script>
import {toPdfBlob} from "@/services/utility";

export default {
  name: "ContractSigned",
  data() {
    return {
      pdf: null,
      url: null
    };
  },
  props: {
    contract: {
      type: Object,
      required: true
    }
  },
  created() {
    this.pdf = toPdfBlob(contract.contractPDF);
    this.url = window.URL.createObjectURL(this.pdf);
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
