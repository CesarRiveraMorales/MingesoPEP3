<template>
    <v-card
      class="overflow-hidden"
    >
        <v-app-bar
          app
          hide-on-scroll
          src="https://images2.alphacoders.com/774/774364.jpg"
          dark
        >
          <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
          <v-spacer></v-spacer>
          <v-toolbar-title>
            <h5>
              VoluntariApp
            </h5>  
          </v-toolbar-title>
          <v-spacer></v-spacer>

          <v-btn icon>
            <v-icon>mdi-heart</v-icon>
          </v-btn>
        </v-app-bar>

        <v-navigation-drawer
          v-model="drawer"
          dark
          absolute
          temporary
          src="https://images2.alphacoders.com/774/774364.jpg"
        >
          <v-list
            nav
            dense
          >
        <v-list-item-group>
          <v-list-item to="/home">
            <v-list-item-title>Inicio</v-list-item-title>
          </v-list-item>
          <v-list-item to="/emergencias">
            <v-list-item-title>Emergencias</v-list-item-title>
          </v-list-item>
          <v-list-item to="/estadisticas">
            <v-list-item-title>Estadísticas</v-list-item-title>
          </v-list-item>
          <v-list-item to="/equipo">
            <v-list-item-title>Equipo</v-list-item-title>
          </v-list-item>
          <v-list-item to="/contacto">
            <v-list-item-title>Contacto</v-list-item-title>
          </v-list-item>
          <v-list-item to="/about">
            <v-list-item-title>Sobre nosotros</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
        </v-list>
      </v-navigation-drawer>

      <v-container class="mt-7" style="width: 2000px;">
          <router-view />
      </v-container>

      <v-footer
      :padless="padless"
      dark
      height = 160
      width = "100%"
      >
        <v-card
          flat
          tile
          width="100%"
          class="red lighten-1 text-center"
        >
          <v-card-text>
            <v-btn to = "/home" icon>
              <v-icon>
                mdi-home
              </v-icon>
            </v-btn>
          </v-card-text>

          <v-divider></v-divider>

          <v-card-text class="white--text">
            {{ new Date().getFullYear() }} — <strong>VoluntariApp</strong>
          </v-card-text>
        </v-card>
      </v-footer>
    </v-card>

</template>

<script>
  export default {
    data: () => ({
      drawer: true,
      group: null,
      padless: true,
      variant: 'default',
    }),

    watch: {
      group () {
        this.drawer = false
      },
    },
  }
</script>

<style>

  .mt-7 {
    margin-top: 80px !important;
  }

</style>