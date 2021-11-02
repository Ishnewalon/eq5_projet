<template>
<div>
  {{user}}
</div>
</template>

<script>
import AuthService from '../services/auth-service';
export default {
  name: "LoggedIn",
  data() {
    return {
      user: AuthService.user
    }
  }
}
</script>

<style scoped>

</style>
