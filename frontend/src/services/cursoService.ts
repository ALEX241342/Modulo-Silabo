import { api, API_ENDPOINTS } from '../config/api';

export interface Curso {
  idCurso: number;
  codigoCurso: string;
  nombreCurso: string;
  planDeEstudios: string;
}

export interface DTOBusquedaDatosCurso {
  idCurso: number;
  codigoCurso: string;
  nombreCurso: string;
  numeroPeriodo: string; //no esta en curso
  anio: string; //no
  ciclo: string; 
  numCreditos: number;
  planDeEstudios: string; //no esta
  tipoCurso: string;
}



export interface DTONuevoSilaboNombre {
  nombreDocumento: string;
  idCurso: number;
}

export interface DTONuevoSilaboResponse {
  idSilabo: number;
  nombreDocumentoSilabo: string;
  idCurso: number;
}

export const cursoService = {
  buscarPorNombre: async (nombre: string): Promise<Curso[]> => {
    try {
      const response = await api.get(`${API_ENDPOINTS.CURSO.BUSCAR_POR_NOMBRE}?parte=${nombre}`);
      return response.data;
    } catch (error) {
      console.error('Error al buscar cursos:', error);
      return [];
    }
  },

  buscarPorId: async (id: number): Promise<DTOBusquedaDatosCurso> => {
    try {
      const response = await api.get(`${API_ENDPOINTS.CURSO.BUSCAR_POR_ID}/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error al buscar curso por ID:', error);
      throw error;
    }
  },

  crearSilabo: async (data: DTONuevoSilaboNombre): Promise<DTONuevoSilaboResponse> => {
    try {
      const response = await api.post(API_ENDPOINTS.SILABO.CREAR, {
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