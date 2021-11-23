<template>
  <div class="container bg-secondary my-3 rounded">
    <div class="d-flex justify-content-between flex-column text-white p-4">
      <form @submit.prevent="startContract">
        <h1>Contrat de {{contract.student.firstName}} {{contract.student.lastName}}</h1>
        <label>Signature</label>
        <input class="w-100" placeholder="Entrez votre signature" type="text"
               v-on:change="e => setSignature(e.target.value)"/>
        <h6 class="text-white text-center mt-3">En appuyant sur envoyer, vous confirmez avoir lu le
          contrat et que la signature entrée correspond à la votre.</h6>
        <button class="btn btn-primary fw-bold w-100 mb-4 mt-0" type="submit">Signez le contrat</button>
      </form>
    </div>
  </div>
</template>

<script>
import toastErr from "sweetalert2";
import {managerSignContract, monitorSignContract, studentSignContract} from "@/services/contrat-service";
import {UserType} from "@/models/RegisterVars";

export default {
  name: "ContractSignature",
  data: function () {
    return {
      signature: '',
      pdfUrl: ''
    }
  },
  props: {
    contract: {
      type: Object,
      required: true
    },
    userType: {
      type: String,
      default: UserType.STUDENT[0],
      required: true
    }
  },
  methods: {
    setSignature(signature) {
      this.signature = signature;
    },
    startContract() {
      if (!this.signature || this.signature === '') {
        toastErr.fire({
          text: 'Veuillez signer le contrat avant de continuer.',
        }).then();
        return;
      }
      if (this.userType === UserType.MANAGER[0])
        managerSignContract(this.signature, this.contract.id).then(isSigned => {
          if (isSigned)
            this.$emit('remove')
        });
      else if (this.userType === UserType.MONITOR[0])
        monitorSignContract(this.signature, this.contract.id).then(isSigned => {
          if (isSigned)
            this.$emit('remove');
        });
      else if (this.userType === UserType.STUDENT[0])
        studentSignContract(this.signature, this.contract.id).then(isSigned => {
          if (isSigned)
            this.$emit('remove');
        });
    }

  }
}
</script>

<style scoped>

</style>
