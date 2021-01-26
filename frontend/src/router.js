import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      alias: "/home",
      name: "home",
      component: () => import("./views/Home.vue")
    },
    {
      path: "/emergencias",
      name: "emergencias",
      component: () => import("./views/Emergencias.vue")
    },
    {
      path: "/equipo",
      name: "equipo",
      component: () => import("./views/Equipo.vue")
    },
    {
      path: "/contacto",
      name: "contacto",
      component: () => import("./views/Contacto.vue")
    },
    {
      path: "/about",
      name: "about",
      component: () => import("./views/About.vue")
    },
    {
      path: "/tareas",
      name: "tareas",
      props: true,
      component: () => import("./components/Tareas.vue")
    },
    {
      path: "/estadisticas",
      name: "estadisticas",
      component: () => import("./views/Estadisticas.vue")
    }
  ]
});