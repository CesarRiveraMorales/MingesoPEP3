<template>
    <v-app>
        <v-row>
            <v-col>
                <v-card
                    class="mx-auto text-center"
                    img="https://images2.alphacoders.com/774/774364.jpg"
                    dark
                    max-width="900"
                >
                    <v-card-text>
                        <GChart
                            type="LineChart"
                            :data="chartData"
                            title='Voluntarios inscritos entre las fechas dadas'
                        />
                    </v-card-text>
                    <v-card-text>
                    <div class="display-1 font-weight-thin">
                        Voluntarios registrados por fecha
                    </div>
                    </v-card-text>
                    <v-card-text>
                    <div>
                        <v-row>
                            <v-col>
                            </v-col>
                            <v-col
                            cols="12"
                            sm="6"
                            md="4"
                            >
                            <v-menu
                                ref="menu"
                                v-model="menu"
                                :close-on-content-click="false"
                                :return-value.sync="inicio"
                                transition="scale-transition"
                                offset-y
                                min-width="290px"
                            >
                                <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="inicio"
                                    label="Fecha inicial"
                                    prepend-icon="mdi-calendar"
                                    readonly
                                    v-bind="attrs"
                                    v-on="on"
                                ></v-text-field>
                                </template>
                                <v-date-picker
                                v-model="inicio"
                                no-title
                                scrollable
                                >
                                <v-spacer></v-spacer>
                                <v-btn
                                    text
                                    color="primary"
                                    @click="menu = false"
                                >
                                    Cancel
                                </v-btn>
                                <v-btn
                                    text
                                    color="primary"
                                    @click="$refs.menu.save(inicio);getVoluntarios();"
                                >
                                    OK
                                </v-btn>
                                </v-date-picker>
                            </v-menu>
                            </v-col>
                            <v-col
                            cols="12"
                            sm="6"
                            md="4"
                            >
                            <v-menu
                                ref="menu2"
                                v-model="menu2"
                                :close-on-content-click="false"
                                :return-value.sync="fin"
                                transition="scale-transition"
                                offset-y
                                min-width="290px"
                            >
                                <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="fin"
                                    label="Fecha final"
                                    prepend-icon="mdi-calendar"
                                    readonly
                                    v-bind="attrs"
                                    v-on="on"
                                ></v-text-field>
                                </template>
                                <v-date-picker
                                v-model="fin"
                                no-title
                                scrollable
                                >
                                <v-spacer></v-spacer>
                                <v-btn
                                    text
                                    color="primary"
                                    @click="menu2 = false"
                                >
                                    Cancel
                                </v-btn>
                                <v-btn
                                    text
                                    color="primary"
                                    @click="$refs.menu2.save(fin);getVoluntarios();"
                                >
                                    OK
                                </v-btn>
                                </v-date-picker>
                            </v-menu>
                            </v-col>
                            <v-col>
                            </v-col>
                        </v-row>
                    </div>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        {{ show ? 'Menos información' : 'Más información' }}
                        <v-btn
                            icon
                            @click="show = !show; filtrarEmergencias();filtrarTareas();"
                        >
                            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
                        </v-btn>
                    </v-card-actions>

                    <v-expand-transition>
                        <div v-show="show">
                            <v-divider></v-divider>

                            <v-row>
                                <v-col>
                                    <v-card-text>
                                        <h5>Emergencias</h5>
                                        <v-spacer/>
                                        Creadas: {{emergCre}}
                                        <v-spacer/>
                                        Iniciadas: {{emergIni}}
                                        <v-spacer/>
                                        Finalizadas: {{emergFin}}
                                    </v-card-text>
                                </v-col>
                                <v-col>
                                    <v-card-text>
                                        <h5>Tareas</h5>
                                        <v-spacer/>
                                        Creadas: {{tareasCre}}
                                        <v-spacer/>
                                        Iniciadas: {{tareasIni}}
                                        <v-spacer/>
                                        Finalizadas: {{tareasFin}}
                                    </v-card-text>
                                </v-col>
                                <v-col>
                                    <v-card-text>
                                        <h5>Voluntarios en tareas</h5>
                                        <v-spacer/>
                                        Creadas: {{volCre}}
                                        <v-spacer/>
                                        Iniciadas: {{volIni}}
                                        <v-spacer/>
                                        Finalizadas: {{volFin}}
                                    </v-card-text>
                                </v-col>
                            </v-row>
                        </div>
                    </v-expand-transition>
                </v-card>
            </v-col>
        </v-row>
    </v-app>
</template>




<script>
import EmergenciasDataService from "../services/EmergenciasDataService";
import TareasDataService from "../services/TareasDataService";
import VoluntariosDataService from "../services/VoluntariosDataService";
import { GChart } from 'vue-google-charts'

export default {
    data () {
        return {
            inicio: '2020-9-1',
            fin: '2020-11-31',
            emergencias: [],
            tareas: [],
            emergIni: null,
            emergFin: null,
            emergCre: null,
            tareasIni: null,
            tareasFin: null,
            tareasCre: null,
            volIni: null,
            volFin: null,
            volCre: null,
            show: false,
            menu: false,
            menu2: false,
            chartData: null,
        };
    },
    components:{
        GChart
    },
    methods:{
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
        getTareas() {
        TareasDataService.getAll()
        .then(response => {
            this.tareas = response.data;
            this.console = response.data;
        })
        .catch(e => {
            this.console = e;
        });
        },
        getVoluntarios() {
        this.setChartData();    
        VoluntariosDataService.getFilter(this.inicio,this.fin)
        .then(response => {
            response.data.forEach((dato)=>{
                this.chartData.push([dato.fecha.slice(5),dato.voluntarios]);
                this.console = dato;    
            })
        })
        .catch(e => {
            this.console = e;
        });
        },
        setChartData(){
            this.chartData = [['Fecha','Registrados']];
        },
        resetContadores(){
            this.volIni = null;
            this.volFin = null;
            this.volCre = null;
            this.emergIni = null;
            this.emergFin = null;
            this.emergCre = null;
            this.tareasIni = null;
            this.tareasFin = null;
            this.tareasCre = null;
        },
        contarVoluntarios(control,valor){
                if (control == 0){
                    this.volIni = this.volIni + valor;
                }
                else if (control==1){
                    this.volFin = this.volFin + valor;
                }
                else{
                    this.volCre = this.volCre + valor;
                }
        },
        filtrarEmergencias(){
                this.resetContadores();
                this.emergencias.forEach((emerg)=>{
                    if (emerg.estado == 'Iniciada'){
                        this.emergIni++;
                    }
                    else if (emerg.estado == 'Finalizada'){
                        this.emergFin++;
                    }
                    else{
                        this.emergCre++;
                    }
                })
                
        },
        filtrarTareas(){
                this.tareas.forEach((task)=>{
                    if (task.id_estado == 1){
                        this.contarVoluntarios(0,task.cant_vol_inscritos);
                        this.tareasIni++;
                    }
                    else if (task.id_estado == 2){
                        this.contarVoluntarios(1,task.cant_vol_inscritos);
                        this.tareasFin++;
                    }
                    else{
                        this.contarVoluntarios(2,task.cant_vol_inscritos);
                        this.tareasCre++;
                    }
                })
        },
    },
    mounted() {
        this.getEmergencias();
        this.getTareas();
        this.getVoluntarios(this.inicio,this.fin);
    },
}

</script>