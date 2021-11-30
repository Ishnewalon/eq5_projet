<template>
  <ul>
    <li v-for="(child, index) in contracts" :key="index">
      <ContractSignature :contract="child" :userType="userType"
                         v-on:remove="removeContract(index)"/>
    </li>
  </ul>
</template>
<script>
import {UserType} from "@/models/RegisterVars";
import {getAllContractsToBeSignedForMonitor, getAllContractsToBeStarted} from "@/services/contrat-service";
import authService from "@/services/auth-service";
import ContractSignature from "@/views/Contract/ContractSignature";

export default {
  name: "ContractsToSign",
  data() {
    return {
      contracts: [],
      id: null
    }
  },
  props: {
    userType: {
      type: String,
      required: true
    }
  },
  methods: {
    removeContract(contractId) {
      this.contracts = this.contracts.filter(contract => contract.id !== contractId);
    }
  },
  components: {ContractSignature},
  created() {
    this.id = authService.getUserId();
    if (this.userType === UserType.MANAGER[0])
      getAllContractsToBeStarted().then(contracts => (this.contracts = contracts));
    if (this.userType === UserType.MONITOR[0])
      getAllContractsToBeSignedForMonitor(this.id).then(contracts => (this.contracts = contracts));
    console.log(this.contracts);
  }
}
</script>

<style scoped>

</style>
