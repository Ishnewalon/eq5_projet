<template>
  <div></div>
</template>

<script>
import axios from "axios";

export default {
  name: "ViewAppliedStudents",
  data: function(){
    return {
      offers: []
    }
  },
  created(){
    this.loadData();
  },
  methods: {
    loadData: async function(){
      await axios.get('http://localhost:8181/offers').then()
    }
  }
}
</script>

<style scoped>

</style>
