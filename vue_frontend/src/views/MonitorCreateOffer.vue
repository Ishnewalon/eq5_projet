<template>
  <h2 class="text-center">Ajouter une offre de stage</h2>
  <div class="form-group row">
    <div class="col-md-6">
      <label>Titre</label>
      <div>
        <div class="input-group">
          <input name="title" placeholder="Titre" class="form-control" type="text"
                 v-model="this.title"/>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label>Département</label>
      <div class="input-group">
        <select v-model="departement" class="form-control" name="choice" id="userTypes">
          <option v-bind:value="Departement.info">{{Departement.info}}</option>
          <option v-bind:value="Departement.art">{{Departement.art}}</option>
        </select>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label>Description</label>
    <div class="input-group">
      <input name="description" placeholder="Description" class="form-control" type="text"
      v-model="this.description" />
    </div>
  </div>
  <div class="form-group">
    <label>Adresse ou le stage se situe</label>
    <div class="input-group">
      <input type="text" class="form-control"/>
    </div>
  </div>
  <div class="form-group">
    <label>Salaire</label>
    <div class="input-group">
      <input name="salaire" placeholder="Salaire" class="form-control" type="number"  v-model="this.salary"/>
    </div>
  </div>
 <div v-if="service.isManager()">
   <div class="col-md-6">
     <div class="input-group">
       <label>Email</label>
       <input name="email" placeholder="Email" class="form-control" type="text" v-model="this.creator_email"/>
     </div>
   </div>
 </div>
<div class="form-group text-center">
<label/>
<div>
  <button class="btn btn-primary" type="button" @click.prevent="this.addOffer">Ajouter</button>
</div>
</div>
</template>

<script>
import authService from '../services/auth-service';
import {DepartmentEnum} from '../models/departement';
import OfferDTO from "../models/OfferDTO";
import offerService from "../services/offer-service";
import Swal from "sweetalert2";
export default {
  name: "MonitorCreateOffer",
  data(){
    return {
      service: authService,
      title: '',
      creator_email: '',
      salary: 0,
      description: '',
      departement: '',
      Departement: DepartmentEnum
    };
  },
  methods:{
    addOffer(){
      let allFieldsFilled = true;
      this.creator_email = this.service.getEmail();
      for (const prop in this) {
        if (prop === '' || this[prop] === '' || this[prop] === null) {
          Swal.fire({
            title: 'Oops...',
            text: 'Veuillez remplir tous les champs',
            icon: 'error'
          });
          allFieldsFilled = false;
          break;
        }
      }

      if (allFieldsFilled){
        let offer = new OfferDTO(this.title, this.department, this.description, this.address, this.salary, this.creator_email);
        offerService.createOffer(offer);
      }
    }
  }
}
</script>

<style scoped>

</style>
