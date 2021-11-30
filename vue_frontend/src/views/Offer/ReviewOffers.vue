<template>
  <div class="container" v-if="offers.length > 0">
    <ul class="list-inline">
      <li v-for="(offer, index) in offers" :key="index" class="mb-4">
        <ValidateOffer :offer="offer"/>
      </li>
    </ul>
  </div>
  <div v-else class="container">
    <div class="alert alert-info text-center">
      <h3>Aucune offre à valider...</h3>
    </div>
  </div>
</template>
<script>

import offerService from "../../services/offer-service";
import Swal from "sweetalert2";
import ValidateOffer from "./ValidateOffer";

export default {
  name: "ReviewOffers",
  data() {
    return {
      offers: []
    }
  },
  components: {
    ValidateOffer
  },
  created() {
    this.loadOffers();
  },
  methods: {
    loadOffers() {
      offerService.getAllOffersNotValid().then(offers => (this.offers = offers))
          .catch(e => {
            console.trace(e);
            Swal.fire({
              title: e,
              icon: 'error'
            });
          })
    },
  }
}
</script>

<style scoped>

</style>
