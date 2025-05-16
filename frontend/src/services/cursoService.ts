import { api } from '../config/api';

export interface Curso {
  id: number;
  codigoCurso: string;
  nombreCurso: string;
  planDeEstudios: string;
}

export interface DTOBusquedaDatosCurso {
  codigoCurso: string;
  nombreCurso: string;
  numeroPeriodo: string;
  anio: string;
  ciclo: string;
  numeroDeCreditos: number;
  planDeEstudios: string;
  tipoDeCurso: string;
}

export interface DTONuevoSilaboNombre {
  nombreDocumento: string;
  idCurso: number;
}

export const cursoService = {
  buscarPorNombre: async (nombre: string): Promise<Curso[]> => {
    try {
      const response = await api.get(`/busqueda-curso/nombre?parte=${nombre}`);
      return response.data;
    } catch (error) {
      console.error('Error al buscar cursos:', error);
      return [];
    }
  },

  buscarPorId: async (id: number): Promise<DTOBusquedaDatosCurso> => {
    try {
      const response = await api.get(`/busqueda-curso/id/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error al buscar curso por ID:', error);
      throw error;
    }
  },

  crearSilabo: async (data: DTONuevoSilaboNombre) => {
    try {
      const response = await api.post('/silabo/nuevo', {
        nombreDocumento: data.nombreDocumento,
        idCurso: Number(data.idCurso)
      });
      return response.data;
    } catch (error) {
      console.error('Error al crear silabo:', error);
      throw error;
    }
  }
}; 