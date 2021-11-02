<template>
<div>
  {{user}}
</div>
</template>

<script>
export default {
  name: "LoggedIn",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
    }
  }
}
</script>

<style scoped>

</style>
