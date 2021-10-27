<template>
  <div class="container">
    <ul>
      <li v-for="(offer, index) in offers" :key="index">
        <ValidateOffer :offer="offer" />
      </li>
    </ul>
  </div>
</template>
<script>
import offerService from "../services/offer-service";
import Swal from "sweetalert2";

export default {
  name: "ReviewOffers",
  data() {
    return {
      offers: []
    }
  },
  created: function(){
    this.loadOffers();
  },
  methods: {
    loadOffers: function(){
      offerService.getAllOffers().then(offers => (this.offers = offers))
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
