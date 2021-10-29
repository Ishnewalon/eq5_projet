<template>
  <div class="form-container">
    <form class="bg-dark px-3 py-4 rounded shadow-lg mt-5" id="contact_form">
      <fieldset>
        <legend>
          <h2 class="d-inline-block mx-auto text-center">Inscription</h2>
        </legend>
        <br/>
          <div v-if="step === this.steps.CEGEP">
            <StepCegep  :prevStep="prevStep" :nextStep="nextStep" :updateUserType="updateUserType"
                       :handleChange="handleChange"
                       :matricule="matricule"/>
          </div>
          <div v-else-if="step === this.steps.GENERAL">
            <StepInformationGeneral :prevStep="prevStep" :nextStep="nextStep"
                                    :handleChange="handleChange"
                                    :values="{email, firstName, lastName, phone}"/>
          </div>
        <div v-else-if="step === this.steps.MONITOR">
          <StepMonitor  :prevStep="prevStep" :nextStep="nextStep" :updateUserType="updateUserType"
                       :handleChange="handleChange"
                       :values="{companyName, city, address, codePostal}"/>
        </div>
        <div v-else-if="step === this.steps.PASSWORD">
          <StepPassword :prevStep="prevStep" :finish="finish" :handleChange="handleChange"
                        :password="password"/>
        </div>
        <div v-else-if="step === this.steps.CHOICE">
          <Choice :prevStep="prevStep" :nextStep="nextStep"/>
        </div>
      </fieldset>
    </form>
  </div>
</template>

<script>

import StepCegep from "./StepCegep";
import StepMonitor from "./StepMonitor";
import StepPassword from "./StepPassword";
import StepInformationGeneral from './StepInformationGeneral';
import Choice from './Choice';
import {Step, UserType} from "../models/RegisterVars";
import {MonitorModel, Student, Supervisor} from "../models/User";
import authService from "../services/auth-service";
import router from "../router";

export default {
  name: "Register",
  created(){
    this.steps = Step;
    this.UserType = UserType;
  },
  data() {
    return {
      steps: Step,
      UserType: UserType,
      service: authService,
      hideFields: true,
      step: Step.CHOICE,
      previousStep: [],
      userType: null,
      matricule: '',
      email: '',
      password: '',
      lastName: '',
      firstName: '',
      phone: '',
      companyName: '',
      address: '',
      codePostal: '',
      city: '',
      prevStep() {
        this.step = this.previousStep[this.previousStep.length - 1];
        this.previousStep.pop()
      },
      nextStep(val) {
        this.previousStep.push(this.step)
        this.lastStep = this.previousStep;
        this.step = val;
      },
      updateUserType(type) {
        this.userType = type;
      },
      handleChange(input, e) {
        this[input] = e.target.value;
      },
      finish() {
        const {
          email, password, firstName, lastName, phone, companyName, address, codePostal, city, matricule
        } = this;
        switch (this.userType) {
          case UserType.STUDENT:
            this.service.signupStudent(new Student(email, password, lastName, firstName, phone, matricule)).then(value => {
              console.log(value)
              router.push("login")
            });
            break;
          case UserType.MONITOR:
            this.service.signupMonitor(new MonitorModel(email, password, lastName, firstName, phone, companyName, address, city, codePostal)).then(value => {
              console.log(value)
              router.push("login")
            })
            break;
          case UserType.SUPERVISOR:
            this.service.signupSupervisor(new Supervisor(email, password, lastName, firstName, phone, matricule)).then(value => {
              console.log(value)
              router.push("login")
            })
            break;
        }
      }
    }
  },
  components: {
    StepCegep,
    StepMonitor,
    StepPassword,
    StepInformationGeneral,
    Choice
  }
}
</script>

<style scoped>
.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

label {
  margin-top: 20px;
  margin-bottom: 10px;
}

button {
  margin-right: 10px;
}

</style>
