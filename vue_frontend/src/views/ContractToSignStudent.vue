<template>
  <ContractSignature :contract="contract" :userType="'student'"/>
</template>
<script>
import authService from "@/services/auth-service";
import {getContractForStudent} from "@/services/contrat-service";
import ContractSignature from "@/views/ContractSignature";

export default {
  name: "ContractToSignStudent",
  data() {
    return {
      contract: null,
      id: null
    };
  },
  components: {ContractSignature},
  created() {
    this.id = authService.getUserId();
    getContractForStudent(this.id).then(contract => {
      this.contract = contract;
    });
  }
}
</script>

<style scoped>

</style>
