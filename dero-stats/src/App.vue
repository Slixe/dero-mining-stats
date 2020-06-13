<template>
  <div id="app">
    <v-app>
      <header>
        <v-toolbar color="secondary">
          <v-avatar size="60" tile>
            <v-img src="/logo.png"> 
            </v-img>
          </v-avatar>
          <v-btn to="/" text>
            <v-toolbar-title>{{ $name }}</v-toolbar-title>
          </v-btn>
          <v-toolbar-items>
            <v-btn @click="changeTheme()" text><span><v-icon>invert_colors</v-icon></span></v-btn>
          </v-toolbar-items>
        </v-toolbar>
      </header>
      <v-main>
        <Main></Main>
      </v-main>
      <v-footer elevation="10" padless>
        <v-card flat tile width="100%" class="text-center" color="primary">
          <v-card-text v-if="$donations">
            dERokevAZEZVJ2N7o39VH81BXBqX9ojtncnPTDMyiVbmYiTXQY93AUCLcor9xsWCKWhYy25ja89ikZWXWab9kXRB7LYfUmbQyS
          </v-card-text>
          <v-divider></v-divider>
          <v-card-text>
            {{ new Date().getFullYear() }} — <strong>{{ $name }}</strong>
          </v-card-text>
        </v-card>
      </v-footer>
    </v-app>
  </div>
</template>

<script>
import Main from './views/Main'

export default {
  name: 'app',
  components: {
    Main
  },
  mounted() {
    if (localStorage.theme) {
      this.$vuetify.theme.dark = localStorage.theme == "dark"
    } else {
      this.$vuetify.theme.dark = true
    }
  },
  methods: {
    changeTheme() {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark
      if (this.$vuetify.theme.dark) {
        localStorage.theme = "dark"
      } else {
        localStorage.theme = "light"
      }
    }
  }
}
</script>

<style>
#app {
  color: white;
  text-align: center;
  background: var(--v-anchor-base);
}

* {
    transition: background-color 200ms ease, color 150ms ease-in-out;
}
</style>
