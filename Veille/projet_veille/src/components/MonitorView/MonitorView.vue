<template>
  <div>
    <ul>
      <li v-for="(item, i) in this.liste" :key="i">
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
  data() {
    return {
      liste: [{}],
    }
  },
  methods: {
    getList: function () {
      axios.get("http://localhost:8181/student").then((response) => {
        this.liste = response.data;
      }).catch(e => {
        console.log(e);
      });
    },
  },
  mounted: function () {
    this.getList();
  },
  created() {
    this.getList();
  },
}
</script>

<style scoped>

</style>