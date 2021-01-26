<template>
  <div class="row map">


    <l-map :zoom="zoom" :center="center" @click="anadirMarcador">
      <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
      <l-marker :key="index" v-for="(marker, index) in markers" :lat-lng="marker"></l-marker>
    </l-map>


  </div>

</template>

<script>
/*MAPA QUE EMITE LAS COORDENADAS DE UN UNICO MARCADOR QUE SE PONE AL CLIQUEAR CON NOMBRE latitud Y longitud*/
import L from 'leaflet';
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';

export default {
  name: "LeafletMap",
  data: function() {
    return {
      /*Mapa*/
      zoom:5,
      center: L.latLng(-36.2259369,-72.1954552),
      url:'https://tile.thunderforest.com/outdoors/{z}/{x}/{y}.png?apikey=510a2be1d00749cfb30d4c0217fd2379',
      attribution:'&copy; <a href="www.thunderforest.com">Thunderforest</a>',
      markers: [],
      /*Datos*/
      latitude: 0,
      longitude: 0,
    }
  },
  components: {
    LMap,
    LTileLayer,
    LMarker,
  },
  methods: {
    anadirMarcador(event) {
      this.markers.splice(0,1);
      this.markers.push(event.latlng);
      this.$emit('getCoordenadas',{latitud:event.latlng.lat,longitud:event.latlng.lng});
    }
  }
}
</script>

<style scoped>
  .map{
    height: 400px;
  }
</style>