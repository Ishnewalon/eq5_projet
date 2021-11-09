<template>
  <div class="container bg-secondary text-white p-3 rounded shadow">
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
          <select v-model="dep" class="form-control"  id="userTypes">
            <option>informatique</option>
            <option>Art et Cinemas</option>
          </select>
        </div>
      </div>
    </div>
    <div class="form-group">
      <label>Description</label>
      <div class="input-group">
        <input name="description" placeholder="Description" class="form-control" type="text"
               v-model="this.description"/>
      </div>
    </div>
    <div class="form-group">
      <label>Adresse ou le stage se situe</label>
      <div class="input-group">
        <input type="text" v-model="this.address" class="form-control"/>
      </div>
    </div>
    <div class="form-group">
      <label>Salaire</label>
      <div class="input-group">
        <input name="salaire" placeholder="Salaire" class="form-control" type="number" v-model="this.salary"/>
      </div>
    </div>
    <div class="form-group text-center">
      <label/>
      <div>
        <button class="btn btn-primary" type="button" @click.prevent="this.addOffer">Créer offre</button>
      </div>
    </div>
  </div>
</template>

<script>
import OfferDTO from "../models/OfferDTO";
import offerService from "../services/offer-service";
import Swal from "sweetalert2";

export default {
  name: "MonitorCreateOffer",
  data() {
    return {
      title: '',
      creator_email: '',
      salary: 0,
      description: '',
      dep: '',
      address: ''
    };
  },
  methods: {
    addOffer: function () {
      let allFieldsFilled = true;
      this.creator_email = JSON.parse(localStorage.getItem('user')).email || '';

      for (const prop in this) {
        console.log(prop, this[prop]);
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

      if (allFieldsFilled) {
        let offer = new OfferDTO(this.title, this.dep, this.description, this.address, this.salary, this.creator_email);
        offerService.createOffer(offer);
      }
    }
  }
}
</script>

<style scoped>

</style>
