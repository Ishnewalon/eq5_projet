<template>
  <div class="shadow-lg rounded-top p-3 border-left border-right border-light bg-dark">
    <div class="d-flex align-items-center flex-column">
      <h2 class="d-inline-block text-dark display-6 fst-italic">
        <span class="badge bg-secondary">{{ offer.title }}</span></h2>
      <div class="row mb-0">
        <span class="col badge bg-dark rounded-pill px-2 py-1">{{ offer.department }}</span>
        <span class="ms-2 col badge bg-success px-2 py-1">{{ `${offer.salary}$/h` }}</span>
      </div>
      <p>Adresse : {{ offer.address }}</p>
      <p class="mb-0">Description: <br/> {{ offer.description }}</p>
    </div>
  </div>
</template>

<script>

import Offer from "@/models/Offer";

export default {
  name: "PreviewOffer",
  props: {
    offer: Offer
  }
}
</script>

<style scoped>

</style>
