<template>
<div>
  <div class="form-group row">
    <div class="col-md-12">
      <label>Matricule</label>
      <div class="input-group">
        <input name="matricule" placeholder="Matricule" class="form-control" type="number"
               :value="matricule" v-on:change="this.handleChange('matricule')"/>
      </div>
    </div>
  </div>
  <div class="form-group text-center">
    <label/>
    <div>
      <button class="btn btn-primary" type="button" v-on:click="this.previous">Précédent</button>
      <button class="btn btn-primary" type="button" v-on:click="this.continue(Step.GENERAL)">Suivant</button>
    </div>
  </div>
</div>
  )
</template>

<script>
import {UserType} from "../models/RegisterVars";

export default {
  name: "StepCegep",
  props:{
    prevStep: Function,
    nextStep: Function,
    updateUserType: Function,
    handleChange: Function,
    matricule: String
  },
  data(){
    return {
      previous(e){
        e.preventDefault()
        this.prevStep();
      },
      continue(val){
        if (this.matricule.length === 5)
          this.updateUserType(UserType.SUPERVISOR)
        if (this.matricule.length === 7)
          this.props.updateUserType(UserType.STUDENT)
        this.nextStep(val);
      }

    }
  }
}
</script>

<style scoped>

</style>
