<template>
  <div class="container">
    <ul class="list-inline">
      <li v-for="(offer, index) in offers" :key="index" class="mb-4">
        <div>
          <PreviewOffer :offer="offer"/>
          <button class="btn btn-success text-dark w-100 fw-bold" @click.prevent="apply(offer.id)">Appliquer</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import PreviewOffer from "@/views/PreviewOffer";
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
