import http from "../http-common";

class EmergenciasDataService {
  getAll() {
    return http.get("/emergencias");
  }

  create(data) {
    return http.post("/emergencias", data);
  }

  update(id, data) {
    return http.put(`/emergencias/${id}`, data);
  }

  deleteAll() {
    return http.delete(`/emergencias`);
  }

}

export default new EmergenciasDataService();
