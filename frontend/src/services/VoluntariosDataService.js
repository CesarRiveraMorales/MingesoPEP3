import http from "../http-common";

class VoluntariosDataService {
  getAll() {
    return http.get("/voluntarios");
  }

  getFilter(finicio,ffin){
    return http.get(`/voluntarios/dias/${finicio}to${ffin}`)
  }

  getVoluntariosEmergencias(){
    return http.get("/voluntarios/emergencias/activas/region")
  }

  create(data) {
    return http.post("/voluntarios", data);
  }

  update(id, data) {
    return http.put(`/voluntarios/${id}`, data);
  }

  deleteAll() {
    return http.delete(`/voluntarios`);
  }

}

export default new VoluntariosDataService();