import Vue from 'vue'
import App from './App.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.config.productionTip = false

Vue.prototype.$name = "DERO Mining Stats"
Vue.prototype.$api = "http://localhost:8080/api"
Vue.prototype.$donations = false
Vue.use(Vuetify)
new Vue({
  render: h => h(App),
  vuetify: new Vuetify({
    icons: {
      iconfont: 'md',
    },
    theme: {
      options: {
        customProperties: true,
      },
      themes: {
        dark: {
          primary: '#2A2D2F',
          secondary: '#383B3E',
          anchor: '#45484B',
        },
        light: {
          primary: '#DCDCDC',
          secondary: '#DCDCDC',
          anchor: '#BDBDBD',
        }
      },
      dark: true
    },
  }),
}).$mount('#app')