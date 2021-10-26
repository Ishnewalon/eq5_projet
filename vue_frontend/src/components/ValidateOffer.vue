<template>

</template>

<script>
import axios from "axios";

export default {
  name: "ValidateOffer",
  props: ['offer'],
  data(){
    return {
      valid: false
    }
  },
  methods: {
    validateOffer: function(){
      axios.post('http://localhost:8181/offers/validate', this.offer).then(response => (this.valid = response));
    }
  }
}
</script>

<style scoped>

</style>
