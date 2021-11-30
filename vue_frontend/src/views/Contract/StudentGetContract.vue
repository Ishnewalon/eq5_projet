<template>
  <div>
    <div v-if="!contract">
      <div class="alert alert-primary">
        <h4 class="fw-bold text-center">Aucune offre disponible pour le moment...</h4>
      </div>
    </div>
    <div v-else>
      <ContractSigned :contract="contract"/>
    </div>
  </div>
</template>

<script>
import authService from "@/services/auth-service";
import {getSignedContractForStudent} from "@/services/contrat-service";
import ContractSigned from "@/views/Contract/ContractSigned";

export default {
  name: "StudentGetContract",
  data() {
    return {
      contract: null
    };
  },
  components: {
    ContractSigned
  },
  created() {
    getSignedContractForStudent(authService.getUserId()).then(contract => this.contract = contract);
  }
}
</script>

<style scoped>

</style>
