import { Typography, Box, Paper, TextField, Alert, CircularProgress, Autocomplete, Button } from '@mui/material';
import { useState, useEffect } from 'react';
import { cursoService, type Curso, type DTOBusquedaDatosCurso } from '../services/cursoService';
import debounce from 'lodash/debounce';

export const Crear = () => {
  const [cursos, setCursos] = useState<Curso[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [cursoSeleccionado, setCursoSeleccionado] = useState<DTOBusquedaDatosCurso | null>(null);
  const [nombreSilabo, setNombreSilabo] = useState('');
  const [cursoIdSeleccionado, setCursoIdSeleccionado] = useState<number | null>(null);
  const [codigoCurso, setCodigoCurso] = useState('');

  const buscarCursos = debounce(async (nombre: string) => {
    if (nombre.length < 2) {
      setCursos([]);
      return;
    }
    
    setLoading(true);
    setError(null);
    try {
      const resultados = await cursoService.buscarPorNombre(nombre);
      setCursos(resultados);
    } catch (err) {
      setError('Error al buscar cursos');
      setCursos([]);
    } finally {
      setLoading(false);
    }
  }, 300);

  const obtenerDetallesCurso = async (id: number) => {
    try {
      const response = await cursoService.buscarPorId(id);
      setCursoSeleccionado(response);
      setCursoIdSeleccionado(id);
      setCodigoCurso(response.codigoCurso || '');
    } catch (err) {
      setError('Error al obtener detalles del curso');
    }
  };

  const crearSilabo = async () => {
    if (!nombreSilabo || !cursoIdSeleccionado) {
      setError('Por favor ingrese un nombre para el silabo y seleccione un curso');
      return;
    }

    try {
      const response = await cursoService.crearSilabo({
        nombreDocumento: nombreSilabo,
        idCurso: Number(cursoIdSeleccionado)
      });
      setError(null);
      console.log('Silabo creado:', response);
    } catch (err) {
      setError('Error al crear el silabo');
      console.error('Error detallado:', err);
    }
  };

  useEffect(() => {
    buscarCursos(searchTerm);
    return () => {
      buscarCursos.cancel();
    };
  }, [searchTerm]);

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" sx={{ mb: 3 }}>Crear Silabo</Typography>
      
      <Paper 
        elevation={3} 
        sx={{ 
          p: 4, 
          bgcolor: 'white',
          borderRadius: 2,
          minHeight: '70vh'
        }}
      >
        <Typography variant="h6" sx={{ mb: 4, color: '#1a237e' }}>
          Seleccione un Curso
        </Typography>

        <Box sx={{ display: 'flex', gap: 4 }}>
          {/* Columna de búsqueda */}
          <Box sx={{ width: '45%', display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Nombre de Silabo
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                value={nombreSilabo}
                onChange={(e) => setNombreSilabo(e.target.value)}
                placeholder="Ingrese el nombre del silabo"
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '&:hover fieldset': {
                      borderColor: '#1a237e',
                    },
                  },
                }}
              />
            </Box>

            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Buscar Curso por nombre
              </Typography>
              <Autocomplete
                freeSolo
                options={cursos}
                getOptionLabel={(option) => 
                  typeof option === 'string' ? option : option.nombreCurso
                }
                loading={loading}
                onChange={(_, newValue) => {
                  if (typeof newValue === 'object' && newValue !== null) {
                    obtenerDetallesCurso(newValue.id);
                  }
                }}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    fullWidth
                    variant="outlined"
                    placeholder="Ingrese el nombre del curso"
                    onChange={(e) => setSearchTerm(e.target.value)}
                    InputProps={{
                      ...params.InputProps,
                      endAdornment: (
                        <>
                          {loading ? <CircularProgress color="inherit" size={20} /> : null}
                          {params.InputProps.endAdornment}
                        </>
                      ),
                    }}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        '&:hover fieldset': {
                          borderColor: '#1a237e',
                        },
                      },
                    }}
                  />
                )}
              />
              {error && (
                <Alert severity="error" sx={{ mt: 1 }}>
                  {error}
                </Alert>
              )}
            </Box>
          </Box>

          {/* Columna de etiquetas */}
          <Box sx={{ width: '45%', display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Código de Curso
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                value={codigoCurso}
                disabled
                sx={{
                  bgcolor: '#f5f5f5',
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#e0e0e0',
                    },
                  },
                }}
              />
            </Box>

            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Tipo de Curso
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                disabled
                value={cursoSeleccionado?.tipoDeCurso || ''}
                sx={{
                  bgcolor: '#f5f5f5',
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#e0e0e0',
                    },
                  },
                }}
              />
            </Box>

            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Plan de Estudios
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                disabled
                value={cursoSeleccionado?.planDeEstudios || ''}
                sx={{
                  bgcolor: '#f5f5f5',
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#e0e0e0',
                    },
                  },
                }}
              />
            </Box>

            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Ciclo
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                disabled
                value={cursoSeleccionado?.ciclo || ''}
                sx={{
                  bgcolor: '#f5f5f5',
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#e0e0e0',
                    },
                  },
                }}
              />
            </Box>

            {/* Campos numéricos en una fila */}
            <Box sx={{ display: 'flex', gap: 2 }}>
              <Box sx={{ width: '30%' }}>
                <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                  Periodo
                </Typography>
                <TextField
                  fullWidth
                  variant="outlined"
                  disabled
                  value={cursoSeleccionado?.numeroPeriodo || ''}
                  sx={{
                    bgcolor: '#f5f5f5',
                    '& .MuiOutlinedInput-root': {
                      '& fieldset': {
                        borderColor: '#e0e0e0',
                      },
                    },
                  }}
                />
              </Box>

              <Box sx={{ width: '30%' }}>
                <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                  Año
                </Typography>
                <TextField
                  fullWidth
                  variant="outlined"
                  disabled
                  value={cursoSeleccionado?.anio || ''}
                  sx={{
                    bgcolor: '#f5f5f5',
                    '& .MuiOutlinedInput-root': {
                      '& fieldset': {
                        borderColor: '#e0e0e0',
                      },
                    },
                  }}
                />
              </Box>

              <Box sx={{ width: '30%' }}>
                <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                  Créditos
                </Typography>
                <TextField
                  fullWidth
                  variant="outlined"
                  disabled
                  value={cursoSeleccionado?.numeroDeCreditos || ''}
                  sx={{
                    bgcolor: '#f5f5f5',
                    '& .MuiOutlinedInput-root': {
                      '& fieldset': {
                        borderColor: '#e0e0e0',
                      },
                    },
                  }}
                />
              </Box>
            </Box>

            <Box sx={{ mt: 2 }}>
              <Button
                variant="contained"
                color="primary"
                fullWidth
                onClick={crearSilabo}
                disabled={!nombreSilabo || !cursoIdSeleccionado}
                sx={{
                  bgcolor: '#1a237e',
                  '&:hover': {
                    bgcolor: '#0d47a1',
                  },
                }}
              >
                Crear Silabo
              </Button>
            </Box>
          </Box>
        </Box>
      </Paper>
    </Box>
  );
}; 