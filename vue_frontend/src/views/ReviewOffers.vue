<template>
  <div class="container">
    <ul class="list-inline">
      <li v-for="(offer, index) in offers" :key="index" class="mb-4">
        <ValidateOffer :offer="offer"/>
      </li>
    </ul>
  </div>
</template>
<script>

import offerService from "../services/offer-service";
import Swal from "sweetalert2";
import ValidateOffer from "../views/ValidateOffer";

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
    loadOffers(){
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
