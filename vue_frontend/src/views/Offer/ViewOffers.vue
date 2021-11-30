<template>
  <div class="container">
    <div v-if="offers.length > 0">
      <ul class="list-inline">
        <li v-for="(offer, index) in offers" :key="index" class="mb-4">
          <div>
            <PreviewOffer :offer="offer"/>
            <button class="btn btn-success text-dark w-100 fw-bold" @click.prevent="apply(offer.id)">Appliquer</button>
          </div>
        </li>
      </ul>
    </div>
    <div v-else>
      <div class="alert alert-primary">
        <h4 class="fw-bold text-center">Aucune offre disponible pour le moment...</h4>
      </div>
    </div>
  </div>
</template>

<script>
import PreviewOffer from "@/views/Offer/PreviewOffer";
import offerAppService from "@/services/offerAppService";
import authService from "@/services/auth-service";
import offerService from "@/services/offer-service";
import OfferApp from "@/models/OfferApp";

export default {
  name: "ViewOffers",
  components: {PreviewOffer},
  data() {
    return {
      offers: []
    }
  },
  created() {
    offerService.getAllOffersValid().then(offers => {
      this.offers = offers;
    });
  },
  methods: {
    apply(idOffer) {
      const idStudent = authService.getUserId();
      offerAppService.apply(new OfferApp(idOffer, idStudent)).then();
    }
  }
}
</script>

<style scoped>

</style>
