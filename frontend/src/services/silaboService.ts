import { api } from '../config/api';

const API_URL = 'http://localhost:8080/api';

export interface Silabo {
  id: number;
  nombre: string;
  // ... otros campos del silabo
}

export const silaboService = {
  crear: async (data: any) => {
    const response = await api.post('/api/silabo/nuevo', data);
    return response.data;
  },
  
  editar: async (id: number, data: any) => {
    const response = await api.put(`/silabo/modificar/${id}`, data);
    return response.data;
  },
  
  buscarPorCodigo: async (codigo: string) => {
    const response = await api.get(`api/silabo/buscar/codigo/${codigo}`);
    // Mapear los campos del backend al formato esperado por el frontend
    return {
      id: response.data.idSilabo,
      nombre: response.data.nombreDocumentoSilabo
    };
  },
  
  buscarPorNombre: async (nombre: string) => {
    const response = await api.get(`api/silabo/buscar/nombre/${nombre}`);
    // Mapear los campos del backend al formato esperado por el frontend
    return {
      id: response.data.idSilabo,
      nombre: response.data.nombreDocumentoSilabo
    };
  },

  imprimirSilabo: async (idSilabo: number) => {
    try {
      const response = await api.post(`/api/formato/plantilla/${idSilabo}/docx`, {}, {
        responseType: 'blob'
      });
      
      // Crear un blob con la respuesta
      const blob = new Blob([response.data], { 
        type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' 
      });
      
      // Crear URL del blob
      const url = window.URL.createObjectURL(blob);
      
      // Crear link temporal y simular clic
      const link = document.createElement('a');
      link.href = url;
      link.download = `Silabo-${idSilabo}.docx`;
      document.body.appendChild(link);
      link.click();
      
      // Limpiar
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
      
      return true;
    } catch (error) {
      console.error('Error al imprimir silabo:', error);
      throw error;
    }
  },

  obtenerSilaboCompleto: async (idSilabo: number) => {
    try {
      const response = await api.get(`/api/silabo/${idSilabo}`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener silabo completo:', error);
      throw error;
    }
  },

  obtenerCursoPorSilabo: async (idSilabo: number) => {
    try {
      const response = await api.get(`/api/silabo/${idSilabo}/curso`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener curso por silabo:', error);
      throw error;
    }
  },

  obtenerCompetenciasPorSilabo: async (idSilabo: number) => {
    try {
      const response = await api.get(`/api/silabo/${idSilabo}/competencias`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener competencias del silabo:', error);
      throw error;
    }
  },

  obtenerPrerrequisitosPorSilabo: async (idSilabo: number) => {
    try {
      const response = await api.get(`/api/silabo/${idSilabo}/prerequisitos`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener prerrequisitos del silabo:', error);
      throw error;
    }
  },

  obtenerLogrosPorCompetencia: async (idCurso: number, idCompetencia: number) => {
    try {
      const response = await api.get(`/api/curso/logros/${idCurso}/${idCompetencia}`);
      return response.data;
    } catch (error) {
      console.error('Error al obtener logros por competencia:', error);
      throw error;
    }
  },

  async guardarInformacionGeneral(idSilabo: number, areaEstudios: string, nombreDocente: string, emailDocente: string) {
    try {
      const response = await api.put(`/api/silabo/${idSilabo}/IG`, {
        areaDeEstudio: areaEstudios,
        nombreDocente: nombreDocente,
        emailDocente: emailDocente
      });
      return response.data;
    } catch (error) {
      console.error('Error al guardar información general:', error, areaEstudios, nombreDocente, emailDocente);
      throw error;
    }
  },

  async guardarEstrategiaDidactica(idSilabo: number, estrategiaDidactica: string) {
    try {
      const response = await api.put(`/api/silabo/${idSilabo}/estrategiaDidactica`, estrategiaDidactica);
      return response.data;
    } catch (error) {
      console.error('Error al guardar estrategia didáctica:', error);
      throw error;
    }
  },

  async guardarBibliografia(idSilabo: number, bibliografia: string) {
    try {
      const response = await api.put(`/api/silabo/${idSilabo}/bibliografia`, bibliografia);
      return response.data;
    } catch (error) {
      console.error('Error al guardar bibliografía:', error);
      throw error;
    }
  },

  async guardarNuevoPrerrequisito(idSilabo: number, data: Array<{ codigoCurso: string, nombreCurso: string }>) {
    try {
      const response = await api.post(`/api/silabo/${idSilabo}/nuevoPrerrequisito`, data);
      return response.data;
    } catch (error) {
      console.error('Error al guardar nuevo prerrequisito:', error);
      console.log('Enviando prerrequisitos:', JSON.stringify(data));
      console.log(Array.isArray(data));
      throw error;
    }
  },

  async borrarPrerrequisito(idSilabo: number, ids: number[] ) {
    try {
      const response = await api.post(`/api/silabo/${idSilabo}/borrarPrerrequisito`, ids);
      return response.data;
    } catch (error) {
      console.error('Error al borrar prerrequisito:', error);
      throw error;
    }
  },

  async guardarNuevoLogro(idSilabo: number, idCompetencia: number, data: string[]) {
    try {
      const response = await api.post(`/api/silabo/${idSilabo}/competencias/${idCompetencia}/nuevoLogro`, data);
      return response.data;
    } catch (error) {
      console.error('Error al guardar nuevo logro:', error);
      console.log('Enviando logros:', JSON.stringify(data));
      console.log(Array.isArray(data));
      throw error;
    }
  },

  async borrarLogro(idSilabo: number, ids: number[] ) {
    try {
      const response = await api.post(`/api/silabo/${idSilabo}/borrarLogro`, ids);
      return response.data;
    } catch (error) {
      console.error('Error al borrar logro:', error);
      throw error;
    }
  }
};