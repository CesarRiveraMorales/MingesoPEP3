import http from "../http-common";

class TareasDataService {
  getAll() {
    return http.get("/tareas");
  }

  create(data) {
    return http.post("/tareas", data);
  }

  update(id, data) {
    return http.put(`/tareas/${id}`, data);
  }

  deleteAll() {
    return http.delete(`/tareas`);
  }

}

export default new TareasDataService();