import { api } from '../config/api';

export interface Silabo {
  id: number;
  nombre: string;
  // ... otros campos del silabo
}

export const silaboService = {
  crear: async (data: any) => {
    const response = await api.post('/silabo/nuevo', data);
    return response.data;
  },
  
  editar: async (id: number, data: any) => {
    const response = await api.put(`/silabo/modificar/${id}`, data);
    return response.data;
  },
  
  buscarPorCodigo: async (codigo: string) => {
    const response = await api.get(`/silabo/buscar/codigo/${codigo}`);
    return response.data;
  },
  
  buscarPorNombre: async (nombre: string) => {
    const response = await api.get(`/silabo/buscar/nombre/${nombre}`);
    return response.data;
  },

  imprimirSilabo: async (idSilabo: number) => {
    try {
      const response = await api.post(`/formato/plantilla/${idSilabo}/docx`, {}, {
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
  }
};