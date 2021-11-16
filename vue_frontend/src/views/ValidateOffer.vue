<template>
  <div class="border-left bg-dark rounded" v-bind:class="{'border-success': this.valid, 'border-danger': !this.valid }">
    <PreviewOffer :offer="this.offer"/>
    <div class="d-flex justify-content-between align-items-center">
      <button id="validateBtn" class="btn btn-success fw-bold text-white w-50"
              v-on:click="this.validateOffer(this.offer, true)">Valide
      </button>
      <button id="invalidateBtn" class="btn btn-danger fw-bold text-white w-50"
              v-on:click="this.validateOffer(this.offer, false)">Invalide
      </button>
    </div>
  </div>
</template>

<script>
import Offer from '../models/Offer';
import offerService from "../services/offer-service";
import Swal from "sweetalert2";
import PreviewOffer from './PreviewOffer';

export default {
  name: "ValidateOffer",
  props: {
    offer: Offer
  },
  data() {
    return {
      valid: false
    }
  },
  components: {
    PreviewOffer
  },
  methods: {
    validateOffer(offer, valid){
      offer.valid = valid;
      offerService.validateOffer(this.offer).then(response => {
        if (response.message) {
          console.trace(response.message);
          Swal.fire({
            title: response.message,
            icon: 'error'
          });
          return;
        }

        this.valid = valid;
        Swal.fire(valid ? 'Offre validée!' : 'Offre invalidée!', '', valid ? 'success' : 'error').then();

        setTimeout(() => {
          this.$destroy();
          this.$el.parentNode.removeChild(this.$el);
        }, 1000);
      });
    }
  }
}
</script>

<style scoped>

</style>
