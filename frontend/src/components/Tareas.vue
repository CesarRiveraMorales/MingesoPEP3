<template>

  <!--Vista de todas las tareas de la emergencia-->
  <div id="app" data-app v-if = "controladorTarea === 'Tareas'">
      <v-card
        class="mx-auto"
        max-width="700"
        max-height="900"
      >
        <v-card-title>
          {{emergency.nombre}}

          <v-spacer></v-spacer>

          <v-btn
            color="white"
            class="text--primary"
            fab
            small
            @click="setControlador('CrearTarea')"
          >
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-card-title>

        <v-card-text class="pt-4">
          {{emergency.descrip}}
        </v-card-text>

        <v-divider></v-divider>

        <v-virtual-scroll
          :items="tareas"
          :item-height="50"
          height="300"
        >
          <template v-slot:default="{ item }">
            <v-list-item>
              <v-list-item-icon >
                <v-icon medium>mdi-clock</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title >{{item.nombre}}</v-list-item-title>
                <v-list-item-subtitle>Desde: {{item.finicio}} hasta: {{item.ffin}}</v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-btn
                  depressed
                  small
                  @click = " setTareaActual(item) ; generarDetalle() ; setControlador('VerTarea');" 
                >
                  Ver tarea

                  <v-icon
                    color="orange darken-4"
                    right
                  >
                    mdi-open-in-new
                  </v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </template>
        </v-virtual-scroll>
      </v-card>
  </div>

  <!--Vista de detalla de cierta tarea-->
  <div id="app" data-app v-else-if="controladorTarea === 'VerTarea'">
      <v-card
        class="mx-auto"
        max-width="700"
        max-height="900"
      >
        <v-card-title>
          {{tareaActual.nombre}}
        </v-card-title>

        <v-card-text class="pt-4">
          {{tareaActual.descrip}}
        </v-card-text>

        <v-divider></v-divider>

        <v-list>
          <v-list-group
            v-for="detalle in descripciones"
            :key="detalle.nombre"
          >
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title v-text="detalle.nombre"></v-list-item-title>
              </v-list-item-content>
            </template>

            <v-list-item
              v-for="elemento in detalle.valor"
              :key="elemento"
            >
              <v-list-item-content>
                <v-list-item-title>{{elemento}}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
          <li style="text-align: center" class="list-group-item">
            Ubicación de la tarea
            <div class="my-6">
              <MapaShowCoordenadas v-bind:objeto="tareaActual"> </MapaShowCoordenadas>
            </div>
          </li>
        </v-list>

      </v-card>
  </div>

  <!--Vista de creación de tarea-->
  <div id="app" data-app v-else-if="controladorTarea === 'CrearTarea'">
    <h3 style="text-align:center"> Crear Tarea</h3>
    <v-form
          ref="form"
          v-model="valid"
          lazy-validation
        >
          <ul class="list-group mt-4">
            <li class="list-group-item">
              <v-text-field
                v-model="nuevaTarea.nombre"
                :counter="100"
                label="Nombre de la tarea"
                required
              ></v-text-field>
            </li>

            <li class="list-group-item">
              <v-textarea
                solo
                v-model="nuevaTarea.descrip"
                label="Descripción de la tarea"
                no-resize
              ></v-textarea>
            </li>

            <li class="list-group-item">
              <v-text-field
                v-model="nuevaTarea.cant_vol_requeridos"
                label="Cantidad de voluntarios requeridos"
                required
              ></v-text-field>
            </li>

            <li class="list-group-item mt-4">
              <v-row justify="space-around">
                <h4> Fecha inicio </h4>
                <h4> Fecha fin </h4>
              </v-row>
              <v-row justify="space-around">
                  <v-date-picker
                    v-model="nuevaTarea.finicio"
                    class="mt-6 mb-2">       
                  </v-date-picker>
                  <v-date-picker
                    v-model="nuevaTarea.ffin"
                    class="mt-6 mb-2">       
                  </v-date-picker>
              </v-row>
            </li>

            <li style="text-align: center" class="list-group-item">
              <h4> Ubicación </h4>
              <div class="my-6">
                <MapaGetCoordenadas @getCoordenadas="getCoordenadasTarea"> </MapaGetCoordenadas>
              </div>
            </li>
          </ul>
      </v-form>
      <div style="text-align:center;" class="mt-7">
      <v-btn @click="crearTarea(); cambiarEstadoCreacion('si');"
              depressed
              color="grey lighten-3">
        Crear</v-btn>
      </div>
      <div style="text-align:center;" class="mt-7" v-if="creado === 'si'">
        <v-alert
          dense
          prominent
          type="success"
        >{{ mensaje }}</v-alert>
      </div>
      
  </div>
</template>

<script>
import TareasDataService from "../services/TareasDataService";
import MapaGetCoordenadas from "../components/MapaGetCoordenadas.vue";
import MapaShowCoordenadas from "../components/MapaShowCoordenadas.vue";

export default {
    name: 'tareas',
    data() {
      return {
        /*Modo de vista*/
        controladorTarea: "Tareas",
        /*Ver tareas*/
        tareas: [],
        tareaActual: null,
        estadoTareaActual: null,
        iconos: 'mdi-clock',
        /*Ver descripcion de una tarea*/
        descripciones: [],
        /*Crear nueva tarea*/
        mensaje: '',
        creado: 'no',
        count: 0,
        nuevaTarea: {
          nombre: '',
          descrip: '',
          cant_vol_requeridos: null,
          cant_vol_inscritos: 0,
          id_emergencia: this.emergency.id,
          finicio: new Date().toISOString().substr(0, 10),
          ffin: new Date().toISOString().substr(0, 10),
          id_estado: 0,
          latitude: '',
          longitude: ''

        },
      };
    },
    props:{
      emergency:{
        type: Object
      },
    },
    components:{
      MapaGetCoordenadas,
      MapaShowCoordenadas,
    },
    methods: {
        setControlador(nuevavista) {
          this.controladorTarea = nuevavista;
        },
        setTareaActual(tarea) {
          this.tareaActual = tarea;
        },
        setEstadoTareaActual(tarea) {
          this.estadoTareaActual = tarea.id_estado;
        },
        obtenerTareas() {
          TareasDataService.getAll()
          .then(response => {
            response.data.forEach((tarea) =>{
              if (tarea.id_emergencia == this.emergency.id){
                this.tareas.push(tarea);
                this.console = tarea;
              }
            })
        })
        .catch(e => {
            this.console = e;
        });
        },
        generarDetalle() {
          let estado = ''
          if (this.tareaActual.id_estado == 0){
            estado = 'Creado'
          }
          else if (this.tareaActual.id_estado == 1){
            estado = 'Iniciado'
          }
          else{
            estado = 'Finalizado'
          }
          this.descripciones = [
            {
              nombre: 'Voluntarios requeridos',
              valor: [this.tareaActual.cant_vol_requeridos],
            },
            {
              nombre: 'Voluntarios inscritos',
              valor: [this.tareaActual.cant_vol_inscritos],
            },
            {
              nombre: 'Fecha de inicio y fin de la tarea',
              valor: [this.tareaActual.finicio,this.tareaActual.ffin],
            },
            {
              nombre: 'Estado actual de la tarea',
              valor: [estado],
            }
          ]  
        },
        getCoordenadasTarea({latitud,longitud}) {
          this.nuevaTarea.latitude = latitud;
          this.nuevaTarea.longitude = longitud;
        },
        crearTarea() {
          if(this.nuevaTarea.nombre != '' &&
              this.nuevaTarea.descrip != '' &&
              this.nuevaTarea.cant_vol_requeridos != null &&
              this.nuevaTarea.latitude != '' &&
              this.nuevaTarea.longitude != '') {
            TareasDataService.create(this.nuevaTarea)
              .then(response => {
              this.console = response.data;
              this.mensaje = "Agregado correctamente";
            })
            .catch(e => {
              this.console = e;
            })
          }else{
            this.mensaje = "Ingrese todos los datos para crear tarea por favor";
          }
        },
        cambiarEstadoCreacion(nuevo){
          this.creado = nuevo;
        },
    },
    mounted() {
      this.obtenerTareas();
    },
}
</script>

<style scoped>
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