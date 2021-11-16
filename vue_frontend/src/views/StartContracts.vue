<template>
  <div class="container bg-dark p-3 rounded">
    <h1 class="text-center mt-5 mb-3 text-decoration-underline">Liste des applications prêtes à être signer</h1>
    <div v-if="offerApplications.length > 0">
      <table class="table table-light table-striped table-borderless text-center rounded-3 shadow-lg">
        <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Étudiant</th>
          <th scope="col">Offre</th>
          <th scope="col">Commencer le processus de signature</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(offerApplication, index) in offerApplications" :key="index">
          <th scope="row">{{offerApplication.id}}</th>
          <td>{{`${offerApplication?.curriculum?.student.firstName} ${offerApplication?.curriculum?.student.lastName}`}}</td>
          <td>{{offerApplication.offer.title}}</td>
          <td>
            <div class="btn-group">
              <button class="btn btn-outline-success"
                      v-on:click="startSigner(offerApplication.id)">Lancer
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <h3 class="bg-dark text-center p-3 rounded">Aucun contrat à démarrer pour le moment</h3>
    </div>
  </div>
</template>

<script>
import authService from "@/services/auth-service";
import {getAllOfferAppReadyToSign, startSignerFetch} from "@/services/contrat-service";

export default {
  name: "StartContracts",
  data() {
    return {
      offerApplications: [],
      userId: authService.getUserId()
    }
  },
  created() {
    getAllOfferAppReadyToSign(this.userId).then(
        data => {
          this.offerApplications = data
        })
  },
  methods: {
    startSigner(offerApplicationId) {
      startSignerFetch(offerApplicationId, this.userId).then((ok) => {
        if (ok)
            this.offerApplications = this.offerApplications.filter(offApp => offApp.id !== offerApplicationId)
      })
    }
  },
}
</script>

<style scoped>

</style>
