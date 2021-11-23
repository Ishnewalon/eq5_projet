<template>
    <div>
        <p>{{this.id}}</p>
    </div>
</template>

<script>
export default {
    name: "DetailsOffer",
    data() {
        return {
            id: "",
        }
    },
    created() {
        this.id = this.$route.query.prop;
    }
}
</script>

<style scoped>

</style>