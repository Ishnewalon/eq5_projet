import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import VSwitch from 'v-switch-case'
import 'bootstrap';

createApp(App).use(router).use(VSwitch).mount('#app')
