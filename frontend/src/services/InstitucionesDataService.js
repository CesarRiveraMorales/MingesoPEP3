import http from "../http-common";

class InstitucionesDataService {
  getAll() {
    return http.get("/instituciones");
  }

  create(data) {
    return http.post("/instituciones", data);
  }

  update(id, data) {
    return http.put(`/instituciones/${id}`, data);
  }

  deleteAll() {
    return http.delete(`/instituciones`);
  }

}

export default new InstitucionesDataService();
