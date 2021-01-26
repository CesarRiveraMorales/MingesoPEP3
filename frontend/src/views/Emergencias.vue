<template>
  <v-app>
  <!--Modo Observar Emergencias-->
    <div id="app" data-app v-if="controlador === 'Observar'">
      <v-btn 
        class="mb-3"     
        depressed
        color="grey lighten-3"
        @click="setControlador('Crear')">
        Crear
      </v-btn>
      <v-btn 
        class="mb-3"     
        depressed
        color="grey lighten-3"
        @click="setControlador('Estadistica1')">
        Ver voluntarios por región
      </v-btn>
      <v-app>
        <v-carousel
        hide-delimiters
        show-arrows-on-hover
        progress
        >
          <v-carousel-item 
            class="list-group-item"
            v-for="(emergencia, index) in sortedArray"
            :key="index"
          >

              <v-hover>
                <template v-slot:default="{ hover }">
                  <v-card
                    class="mx-auto"
                    max-width="344"
                  >
                    <v-img src="https://cdn.vuetifyjs.com/images/cards/forest-art.jpg"></v-img>

                    <v-card-text>
                      <h4>
                        {{ emergencia.nombre}}
                      </h4>
                      <v-icon>mdi-map-marker</v-icon> {{ emergencia.estado}}
                      <div>
                        {{ emergencia.descrip}}
                      </div>
                    </v-card-text>
                    <v-fade-transition>
                      <v-overlay
                        v-if="hover"
                        absolute
                        color="#000000"
                      >
                            <v-btn 
                              @click="setEmergenciaActual(emergencia); setEstadoActual(emergencia); 
                              setControlador('Detalle')">
                              Ver más información
                            </v-btn>
                        </v-overlay>
                      </v-fade-transition>
                    </v-card>
                  </template>
                </v-hover>
              </v-carousel-item>
        </v-carousel>
      </v-app>
    </div>

    <!--Modo Detalle Emergencia-->
    <div id="app" data-app v-else-if="controlador === 'Detalle'">
      <v-btn 
        @click="setControlador('Observar')"
        depressed
        color="grey lighten-3">
        Regresar
      </v-btn>

      <h3 style="text-align:center"> {{ emergenciaActual.nombre }}</h3>
      <ul class="list-group mt-4">
        <li class="list-group-item">
          <h4 style="text-align:center"> Descripción </h4>
          <h5 style="text-align:center"> {{ emergenciaActual.descrip}}</h5>
        </li>

        <li class="list-group-item">
          <div align="center">
            <v-btn @click = "setControlador('verTareas')">
                Ver Tareas
            </v-btn>
          </div>
        </li>

        <li class="list-group-item">
          <v-row justify="space-around">
            <h4 style="text-align:center"> Fecha de inicio </h4>
            <h4 style="text-align:center"> Fecha de termino </h4>
          </v-row>
          <v-row justify="space-around mt-3">
            <h5 style="text-align:center"> {{ emergenciaActual.finicio }}</h5>
            <h5 style="text-align:center"> {{ emergenciaActual.ffin }}</h5>
          </v-row>
        </li>

        <li style="text-align: center" class="list-group-item">
          <h4> Ubicación </h4>
          <div class="my-6">
            <MapaShowCoordenadas v-bind:objeto="emergenciaActual"> </MapaShowCoordenadas>
          </div>
        </li>

        <li class="list-group-item">
          <h4 style="text-align:center"> Estado </h4>
          <div align="center">
            <v-select
              v-model="estadoActual"
              :items="estados"
              outlined
              class="v-select__selection"
              ></v-select>
          </div>
        </li>

      </ul>
      <div style="text-align:center;" class="mt-4">
      <v-btn @click="updateEmergencia()">Aplicar</v-btn>
      </div>
      <p>{{ mensaje }}</p>
    </div>

    <!--Modo ver tareas de la emergencia-->
    <div id="app" data-app v-else-if="controlador === 'verTareas'">
      <v-btn 
        @click="setControlador('Detalle')"
        depressed
        color="grey lighten-3">
        Regresar
      </v-btn>
      <Tareas :emergency="emergenciaActual"></Tareas>
    </div>

    <!--Modo Crear Emergencia-->
    <div id="app" data-app v-else-if="controlador === 'Crear'">
      <v-btn 
        @click="setControlador('Observar')"
        depressed
        color="grey lighten-3"
        href="/emergencias"
        >
        Regresar
      </v-btn>

      <h3 style="text-align:center"> Crear Emergencia</h3>
      
      <v-form
          ref="form"
          v-model="valid"
          lazy-validation
        >
          <ul class="list-group mt-4">
            <li class="list-group-item">
              <v-text-field
                v-model="nuevaEmergencia.nombre"
                :counter="100"
                label="Nombre de la emergencia"
                required
              ></v-text-field>
            </li>

            <li class="list-group-item">
              <v-textarea
                solo
                v-model="nuevaEmergencia.descrip"
                label="Descripción"
                no-resize
              ></v-textarea>
            </li>

            <li class="list-group-item mt-4">
              <v-row justify="space-around">
                <h4> Fecha inicio </h4>
                <h4> Fecha fin </h4>
              </v-row>
              <v-row justify="space-around">
                  <v-date-picker
                    v-model="nuevaEmergencia.finicio"
                    class="mt-6 mb-2">       
                  </v-date-picker>
                  <v-date-picker
                    v-model="nuevaEmergencia.ffin"
                    class="mt-6 mb-2">       
                  </v-date-picker>
              </v-row>
            </li>

            <li style="text-align: center" class="list-group-item">
              <h4> Ubicación </h4>
              <div class="my-6">
                <MapaGetCoordenadas @getCoordenadas="getCoordenadas"> </MapaGetCoordenadas>
              </div>
            </li>

            <li class="list-group-item">
              <h4 style="text-align:center"> Institución </h4>
              <div align="center">
                <v-select
                  :items="instituciones"
                  v-model="nuevaEmergencia.id_institucion"
                  label="Escoger"
                  single-line
                  item-text="nombre"
                  item-value="id"
                  @click="getInstituciones"
                ></v-select>
              </div>
            </li>
          </ul>

      </v-form>

      <div style="text-align:center;" class="mt-7">
      <v-btn @click="crearEmergencia"
              depressed
              color="grey lighten-3">
        Crear</v-btn>
      </div>
      {{ mensaje }}

    </div>

    <!--Modo ver voluntarios por region-->
    <div id="app" data-app v-else-if="controlador === 'Estadistica1'">
      <v-btn 
        @click="setControlador('Observar')"
        depressed
        color="grey lighten-3"
        class="mb-13">
        Regresar
      </v-btn>
      <MapaRegionesChile></MapaRegionesChile>
    </div>

  </v-app>
</template>

<script>
import MapaGetCoordenadas from "../components/MapaGetCoordenadas.vue"
import MapaShowCoordenadas from "../components/MapaShowCoordenadas.vue"
import MapaRegionesChile from "../components/MapaRegionesChile.vue"
import EmergenciasDataService from "../services/EmergenciasDataService";
import InstitucionesDataService from "../services/InstitucionesDataService";
import Tareas from "../components/Tareas.vue";

export default {
  name: "emergencias",
  data() {
    return {
      /*Modo de vista*/
      controlador: "Observar",
      /*Ver Emergencia*/
      emergencias: [],
      /*Detalle Emergencia*/
      emergenciaActual: null,
      estadoActual: null,
      estados: ["Creada","Iniciada","Cancelada","Finalizada"],
      mensaje: '',
      /*Crear Emergencia*/
      nuevaEmergencia: {
        nombre: '',
        descrip: '',
        finicio: new Date().toISOString().substr(0, 10),
        ffin: new Date().toISOString().substr(0, 10),
        id_institucion: '',
        estado: "Creada",
        latitude: '',
        longitude: ''

      },
      instituciones: [],
    };
  },
  components: {
    MapaGetCoordenadas,
    MapaShowCoordenadas,
    MapaRegionesChile,
    Tareas
  },
  methods: {
    setControlador(nuevavista) {
      this.controlador = nuevavista;
    },
    /*Mostrar emergencias*/
    getEmergencias() {
      EmergenciasDataService.getAll()
      .then(response => {
        this.emergencias = response.data;
        this.console = response.data;
      })
      .catch(e => {
        this.console = e;
      });
    },
    setEmergenciaActual(emergencia) {
      this.emergenciaActual = emergencia;
    },
    /*Detalle emergencias*/
    setEstadoActual(emergencia) {
      this.estadoActual = emergencia.estado;
    },
    updateEmergencia() {
      this.emergenciaActual.estado = this.estadoActual;
      EmergenciasDataService.update(this.emergenciaActual.id,this.emergenciaActual)
        .then(response => {
        this.console = response.data;
        this.mensaje = "Actualizado correctamente";
      })
      .catch(e => {
        this.console = e;
      })
    },
    /*Crear emergencia*/
    getCoordenadas({latitud,longitud}) {
      this.nuevaEmergencia.latitude = latitud;
      this.nuevaEmergencia.longitude = longitud;
    },
    getInstituciones(){
      InstitucionesDataService.getAll()
      .then(response => {
        this.instituciones = response.data;
        this.console = response.data;
      })
      .catch(e => {
        this.console = e;
      });
    },
    crearEmergencia() {
      if(this.nuevaEmergencia.nombre != '' &&
          this.nuevaEmergencia.descrip != '' &&
          this.nuevaEmergencia.id_institucion != '' &&
          this.nuevaEmergencia.latitude != '' &&
          this.nuevaEmergencia.longitude != '') {
        EmergenciasDataService.create(this.nuevaEmergencia)
          .then(response => {
          this.console = response.data;
          this.mensaje = "Agregado correctamente";
        })
        .catch(e => {
          this.console = e;
        })
      }else{
        this.mensaje = "Ingrese todos los datos para crear emergencia por favor";
      }
    }
  },
  mounted() {
    this.getEmergencias();
  },
  computed: {
    sortedArray: function() {
      function compare(a, b) {
        if (a.nombre < b.nombre)
          return -1;
        if (a.nombre > b.nombre)
          return 1;
        return 0;
      }
      return this.emergencias.slice().sort(compare);
    }
  }
};

</script>


<style scoped>
.v-card {
  margin: 100px;
}
.v-select__selection {
    width: 15%;
    justify-content: center;
}

.mt-7 {
    margin-top: 80px !important;
}

.mt-6 {
    margin-top: 35px !important;
}

</style>