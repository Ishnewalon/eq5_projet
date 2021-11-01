<template>
  <div class="text-center ">
    <div>
      <h2><ins>Inscription</ins></h2>
      <div>
        <button :class = "getCurrentRegister()==='RegisterStudent' ? 'btnSelected': 'btnSimple'"
                v-on:click="handleClick('student')">Étudiant</button>
        <component v-bind:is="getCurrentRegister()"></component>
      </div>
    </div>
  </div>
</template>

<style scoped>

button{
  font-size: 15px;
  border: 2px solid black;
  padding: 15px;
  border-radius: 50%;
  text-decoration: none;
  cursor: pointer;
}
.btnSelected {
  background: rgba(64, 255, 169, 0.74);
}
.btnSimple {
  background: rgba(115, 128, 125, 0.74);
}
</style>
<script>
import RegisterStudent from './RegisterStudent.vue'
export default{
  name: 'register',
  data: function(){
    return { currentRegister: 'RegisterStudent'}
  },
  components: {
    RegisterStudent
  },
  methods: {
    getCurrentRegister(){
      return this.currentRegister
    },
    setCurrentRegister(current){
      this.currentRegister = current
    },
    handleClick(current){
      if(current === "student"){
        this.setCurrentRegister('RegisterStudent')
        return this.currentRegister
      }
    }
  }
}
</script>

