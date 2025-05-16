import axios from 'axios';

export const API_BASE_URL = 'http://localhost:8080';

export const API_ENDPOINTS = {
  SILABO: {
    CREAR: '/api/silabo/crear',
    EDITAR: '/api/silabo/editar',
    IMPRIMIR: '/api/silabo/imprimir',
    BUSCAR_POR_CODIGO: '/api/silabo/buscar/codigo',
    BUSCAR_POR_NOMBRE: '/api/silabo/buscar/nombre',
  }
};

export const api = axios.create({
  baseURL: API_BASE_URL + '/api',
  headers: {
    'Content-Type': 'application/json',
  },
}); 