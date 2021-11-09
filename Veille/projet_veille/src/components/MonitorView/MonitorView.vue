<template>
  <div>
    <ul>
      <li v-for="(item, i) in list" :key="i">
        <p>test</p>
        {{ item.firstName }} {{ item.lastName }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MonitorView",
  data: function () {
    return {
      list: [{
      }]
    }
  },
  methods: {
    getList: function () {
      axios.get("http://localhost:8181/student").then(function (response) {
        console.log(response.data);
        this.list = response.data;
      });
    },
  },
  mounted: function () {
    this.getList();
  },
}
</script>

<style scoped>

</style>