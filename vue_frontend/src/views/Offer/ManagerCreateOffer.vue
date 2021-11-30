<template>
  <div class="container bg-secondary text-white p-3 rounded shadow">
    <h2 class="text-center">Ajouter une offre de stage</h2>
    <div class="form-group row mb-2">
      <div class="col-md-6">
        <label>Titre</label>
        <div class="input-group">
          <input v-model="this.title" class="form-control" name="title" placeholder="Titre" type="text"/>
        </div>
      </div>
      <div class="col-md-6">
        <label>Département</label>
        <div class="input-group">
          <select v-model="dep" class="form-select">
            <option>informatique</option>
            <option>Art et Cinemas</option>
          </select>
        </div>
      </div>
    </div>
    <div class="form-group mb-2">
      <label>Sessions</label>
      <select class="form-select" v-model="this.session_id">
        <option v-for="(session, index) in sessions" :key="index" :value="session.id">
          {{ session.typeSession + ' ' + session.year }}
        </option>
      </select>
    </div>
    <div class="form-group mb-2">
      <label>Description</label>
      <div class="input-group">
        <input v-model="this.description" class="form-control" name="description" placeholder="Description"
               type="text"/>
      </div>
    </div>
    <div class="row">
      <div class="row mb-2">
        <div class="col">
          <label>Horaire de travail</label>
          <input type="text" class="form-control" v-model="this.horaireDeTravail" name="">
        </div>
        <div class="col">
          <label>Nombre heures semaine</label>
          <input type="text" class="form-control" v-model="this.nbHeureSemaine"></div>
      </div>
      <div class="row mb-2">
        <div class="col">
          <label>Date début</label>
          <input type="date" class="form-control" v-model="this.dateDebut">
        </div>
        <div class="col">
          <label>Date fin</label>
          <input type="date" class="form-control" v-model="this.dateFin">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label>Adresse ou le stage se situe</label>
      <div class="input-group mb-2">
        <input v-model="this.address" class="form-control" type="text"/>
      </div>
      <div class="form-group mb-2">
        <label>Email du moniteur</label>
        <div class="input-group">
          <input v-model="this.creator_email" class="form-control" placeholder="Entrez le email du moniteur"
                 type="email"/>
        </div>
      </div>
      <div class="form-group">
        <label>Salaire</label>
        <div class="input-group">
          <input v-model="this.salary" class="form-control" name="salaire" placeholder="Salaire" type="number"/>
        </div>
      </div>
      <div class="form-group">
        <label>Nombre semaines</label>
        <div class="input-group">
          <input v-model="this.nbSemaine" class="form-control" placeholder="Nombre semaines" type="text"/>
        </div>
      </div>
      <div class="form-group text-center">
        <div>
          <button class="btn btn-primary  mt-2" type="button" @click.prevent="this.addOffer">Créer offre</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import OfferDTO from "../../models/OfferDTO";
import offerService from "../../services/offer-service";
import Swal from "sweetalert2";
import {getCurrentAndFutureSession} from "@/services/session-service";

export default {
  name: "ManagerCreateOffer",
  data() {
    return {
      title: '',
      creator_email: '',
      salary: 0,
      description: '',
      dep: '',
      address: '',
      sessions: [],
      session_id: null,
      dateDebut: '',
      dateFin: '',
      horaireDeTravail: '',
      nbHeureSemaine: '',
      nbSemaine : ''
    };
  },
  created() {
    getCurrentAndFutureSession().then(sessions => {
      this.sessions = sessions;
    });
  },
  methods: {
    addOffer: function () {
      let allFieldsFilled = true;

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

      if (allFieldsFilled) {
        let offer = new OfferDTO(this.title,
            this.dep,
            this.description,
            this.address,
            this.salary,
            this.creator_email,
            this.nbSemaine,
            this.dateDebut,
            this.dateFin,
            this.horaireDeTravail,
            this.nbHeureSemaine,
            this.session_id);
        offerService.createOffer(offer);
      }
    }
  }
}
</script>

<style scoped>

</style>
