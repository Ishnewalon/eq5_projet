<template>
  <div class="container">
    <ul>
      <li v-for="(offer, index) in offers" :key="index">
        <ValidateOffer :offer="offer"></ValidateOffer>
      </li>
    </ul>
  </div>
</template>
<script>

import axios from "axios";

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
      axios.get('http://localhost:8181/offers').then(response => {
        const {message} = response;
        if(!message)
          this.offers = response;
        else
          alert(response.message);
      });
    },
  }
}
</script>

<style scoped>

</style>
