<template>
  <div v-if="contract">
    <ContractSignature :contract="contract" :userType="'student'"/>
  </div>
  <div v-else>
    <div class="alert alert-primary container">
      <h3 class="text-center fw-bold">
        Aucun contrat à signer.
      </h3>
    </div>
  </div>
</template>
<script>
import authService from "@/services/auth-service";
import {getContractForStudent} from "@/services/contrat-service";
import ContractSignature from "@/views/Contract/ContractSignature";

export default {
  name: "ContractToSignStudent",
  data() {
    return {
      contract: null
    };
  },
  components: {ContractSignature},
  created() {
    console.log("ContractToSignStudent created");
    getContractForStudent(authService.getUserId()).then(contract => {
      this.contract = contract;
    });
  }
}
</script>

<style scoped>

</style>
