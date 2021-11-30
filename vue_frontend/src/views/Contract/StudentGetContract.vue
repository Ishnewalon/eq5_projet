<template>
<div>
  <div v-if="!contract">
  <MessageNothingToShow message="Aucun contrat n'a été signé pour l'instant..."/>
  </div>
  <div v-else>
    <ContractSigned :contract="contract"/>
  </div>
</div>
</template>

<script>
import authService from "@/services/auth-service";
import {getSignedContractForStudent} from "@/services/contrat-service";

export default {
  name: "StudentGetContract",
  data(){
    return {
      contract: null
    };
  },
  created() {
    getSignedContractForStudent(authService.getUserId()).then(contract => this.contract = contract);
  }
}
</script>

<style scoped>

</style>
