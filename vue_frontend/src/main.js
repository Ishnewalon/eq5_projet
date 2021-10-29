import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import VSwitch from 'v-switch-case'

createApp(App).use(router).use(VSwitch).mount('#app')
