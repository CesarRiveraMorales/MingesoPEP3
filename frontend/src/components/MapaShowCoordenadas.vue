<template>
  <div class="row map">

    <l-map :zoom="zoom" :center="center">
      <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
      <l-marker :lat-lng="marker" ></l-marker>
    </l-map>


  </div>

</template>

<script>
/*MAPA QUE AL ENTREGARLE UN PROP objeto CON LATITUDE Y LONGITUDE LO MUESTRA*/
import L from 'leaflet';
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';

export default {
  name: "LeafletMap",
  props: ['objeto'],
  data: function() {
    return {
      /*Mapa*/
      zoom:7,
      center: L.latLng(parseInt(this.objeto.latitude),parseInt(this.objeto.longitude)),
      url:'https://tile.thunderforest.com/outdoors/{z}/{x}/{y}.png?apikey=510a2be1d00749cfb30d4c0217fd2379',
      attribution:'&copy; <a href="www.thunderforest.com">Thunderforest</a>',
      /*Datos de padre*/
      marker: L.latLng(parseInt(this.objeto.latitude),parseInt(this.objeto.longitude)),
    }
  },
  components: {
    LMap,
    LTileLayer,
    LMarker,
  }
}
</script>

<style scoped>
  .map{
    height: 400px;
  }
</style>