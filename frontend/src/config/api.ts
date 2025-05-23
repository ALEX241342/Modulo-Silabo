import axios from 'axios';

export const API_BASE_URL = 'http://localhost:8080';

export const API_ENDPOINTS = {
  SILABO: {
    CREAR: '/api/silabo/nuevo',
    EDITAR: '/api/silabo/modificar',
    IMPRIMIR: '/api/formato/plantilla',
    BUSCAR_POR_CODIGO: '/api/silabo/buscar/codigo',
    BUSCAR_POR_NOMBRE: '/api/silabo/buscar/nombre',
  },
  CURSO: {
    BUSCAR_POR_NOMBRE: '/api/busqueda-curso/nombre',
    BUSCAR_POR_ID: '/api/busqueda-curso/id'
  }
};

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
}); 