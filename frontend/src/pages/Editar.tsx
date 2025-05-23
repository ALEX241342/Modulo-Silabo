import { Typography, Box, Paper, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions, IconButton, Alert } from '@mui/material';
import { useState, useEffect } from 'react';
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { silaboService } from '../services/silaboService';

export const Editar = () => {
  const [openDialog, setOpenDialog] = useState(false);
  const [currentSection, setCurrentSection] = useState(0);
  const [idSilabo, setIdSilabo] = useState<string>('');
  const [nombreSilabo, setNombreSilabo] = useState<string>('');
  const [error, setError] = useState<string>('');
  const [silaboSeleccionado, setSilaboSeleccionado] = useState<{id: number, nombre: string} | null>(null);
  
  // Estados para los datos del silabo
  const [datosSilabo, setDatosSilabo] = useState<any>(null);
  const [datosCurso, setDatosCurso] = useState<any>(null);
  const [correoProfesor, setCorreoProfesor] = useState('');
  const [nombreProfesor, setNombreProfesor] = useState('');
  const [areaEstudios, setAreaEstudios] = useState('');
  const [competenciaSeleccionada, setCompetenciaSeleccionada] = useState('');
  const [logros, setLogros] = useState<any[]>([]);
  const [openConfirmDialog, setOpenConfirmDialog] = useState(false);
  const [prerrequisitosSeleccionados, setPrerrequisitosSeleccionados] = useState<number[]>([]);
  const [logrosSeleccionados, setLogrosSeleccionados] = useState<number[]>([]);
  const [nuevoPrerrequisito, setNuevoPrerrequisito] = useState({ codigo: '', nombre: '' });
  const [nuevoLogro, setNuevoLogro] = useState('');
  const [nuevosPrerrequisitos, setNuevosPrerrequisitos] = useState<Array<{id: number, codigo: string, nombre: string}>>([]);
  const [nuevosLogros, setNuevosLogros] = useState<Array<{id: number, descripcion: string}>>([]);

  const sections = [
    "Información General",
    "Prerrequisitos",
    "Sumilla",
    "Competencias",
    "Logros",
    "Estrategia Didáctica",
    "Bibliografía"
  ];

  const handleOpenDialog = () => {
    if (!silaboSeleccionado) {
      setError('Por favor seleccione un silabo primero');
      return;
    }
    setOpenDialog(true);
    setCurrentSection(0);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handlePrevious = () => {
    setCurrentSection(prev => Math.max(0, prev - 1));
  };

  const handleNext = () => {
    setCurrentSection(prev => Math.min(sections.length - 1, prev + 1));
  };

  const buscarPorId = async () => {
    if (!idSilabo) {
      setError('Por favor ingrese un ID de silabo');
      return;
    }
    try {
      const silabo = await silaboService.buscarPorCodigo(idSilabo);
      setSilaboSeleccionado(silabo);
      setError('');
      // Cargar datos completos del silabo y curso
      await cargarDatosCompletos(silabo.id);
    } catch (error) {
      setError('Error al buscar el silabo por ID');
      setSilaboSeleccionado(null);
    }
  };

  const buscarPorNombre = async () => {
    if (!nombreSilabo) {
      setError('Por favor ingrese un nombre de silabo');
      return;
    }
    try {
      const silabo = await silaboService.buscarPorNombre(nombreSilabo);
      setSilaboSeleccionado(silabo);
      setError('');
      // Cargar datos completos del silabo y curso
      await cargarDatosCompletos(silabo.id);
    } catch (error) {
      setError('Error al buscar el silabo por nombre');
      setSilaboSeleccionado(null);
    }
  };

  const cargarDatosCompletos = async (silaboId: number) => {
    try {
      const [silaboData, cursoData, competenciasData, prerrequisitosData] = await Promise.all([
        silaboService.obtenerSilaboCompleto(silaboId),
        silaboService.obtenerCursoPorSilabo(silaboId),
        silaboService.obtenerCompetenciasPorSilabo(silaboId),
        silaboService.obtenerPrerrequisitosPorSilabo(silaboId)
      ]);
      
      setDatosSilabo({
        ...silaboData,
        competencias: competenciasData,
        prerrequisitos: prerrequisitosData
      });
      setDatosCurso(cursoData);
      
      // Corregir nombres de propiedades
      setCorreoProfesor(silaboData.emailDocente || '');
      setNombreProfesor(silaboData.nombreCompletoDocente || '');
      setAreaEstudios(silaboData.areaEstudios || '');
    } catch (error) {
      setError('Error al cargar los datos completos');
    }
  };

  const cargarLogros = async (idCurso: number, idCompetencia: number) => {
    try {
      const response = await silaboService.obtenerLogrosPorCompetencia(idCurso, idCompetencia);
      setLogros(response);
    } catch (error) {
      console.error('Error al cargar logros:', error);
      setLogros([]);
    }
  };

  const handleCompetenciaChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const competenciaId = e.target.value;
    setCompetenciaSeleccionada(competenciaId);
    setNuevosLogros([]); // Limpiar nuevos logros al cambiar de competencia
    if (competenciaId && datosCurso?.idCurso) {
      cargarLogros(datosCurso.idCurso, Number(competenciaId));
    } else {
      setLogros([]);
    }
  };

  const handleAñadirPrerrequisito = () => {
    if (nuevoPrerrequisito.codigo && nuevoPrerrequisito.nombre) {
      const nuevoId = Date.now(); // Generar ID temporal
      setNuevosPrerrequisitos([...nuevosPrerrequisitos, {
        id: nuevoId,
        codigo: nuevoPrerrequisito.codigo,
        nombre: nuevoPrerrequisito.nombre
      }]);
      setNuevoPrerrequisito({ codigo: '', nombre: '' }); // Limpiar inputs
    }
  };

  const handleAñadirLogro = () => {
    if (!competenciaSeleccionada || competenciaSeleccionada === '') {
      // Mostrar mensaje de error o no permitir la acción
      return;
    }
    if (nuevoLogro) {
      const nuevoId = Date.now();
      setNuevosLogros([...nuevosLogros, {
        id: nuevoId,
        descripcion: nuevoLogro
      }]);
      setNuevoLogro('');
    }
  };

  const handleDobleClickPrerrequisito = (id: number) => {
    setNuevosPrerrequisitos(nuevosPrerrequisitos.filter(p => p.id !== id));
  };

  const handleDobleClickLogro = (id: number) => {
    setNuevosLogros(nuevosLogros.filter(l => l.id !== id));
  };

  const handleGuardar = () => {
    setOpenConfirmDialog(true);
  };

  const handleConfirmarGuardar = async () => {
    if (!silaboSeleccionado) return;

    try {
      switch (currentSection) {
        case 0: // Información General
          await silaboService.guardarInformacionGeneral(
            silaboSeleccionado.id,
            areaEstudios,
            nombreProfesor,
            correoProfesor
          );
          break;
        case 1: // Prerrequisitos
          // Enviar lista completa de nuevos prerrequisitos
          if (nuevosPrerrequisitos.length > 0) {
            const prerrequisitosList = nuevosPrerrequisitos.map(p => ({
              codigoCurso: p.codigo,
              nombreCurso: p.nombre
            }));
            
            await silaboService.guardarNuevoPrerrequisito(
              silaboSeleccionado.id,
              prerrequisitosList
            );
            
          }
          // Enviar lista completa de prerrequisitos a borrar
          if (prerrequisitosSeleccionados.length > 0) {
            await silaboService.borrarPrerrequisito(
              silaboSeleccionado.id,
              prerrequisitosSeleccionados 
            );
          }
          break;
        case 4: // Logros
          if (competenciaSeleccionada) {
            // Enviar lista completa de nuevos logros
            if (nuevosLogros.length > 0) {
              const logrosList = nuevosLogros.map(l => (
                l.descripcion
              ));
              await silaboService.guardarNuevoLogro(
                silaboSeleccionado.id,
                Number(competenciaSeleccionada),
                logrosList 
              );
            }
            // Enviar lista completa de logros a borrar
            if (logrosSeleccionados.length > 0) {
              await silaboService.borrarLogro(
                silaboSeleccionado.id,
                logrosSeleccionados 
              );
            }
          }
          break;
        case 5: // Estrategia Didáctica
          await silaboService.guardarEstrategiaDidactica(
            silaboSeleccionado.id,
            datosSilabo?.estrategiaDidactica || ''
          );
          break;
        case 6: // Bibliografía
          await silaboService.guardarBibliografia(
            silaboSeleccionado.id,
            datosSilabo?.bibliografia || ''
          );
          break;
      }
      setOpenConfirmDialog(false);
      setOpenDialog(false);
    } catch (error) {
      console.error('Error al guardar:', error);
      // Aquí podrías mostrar un mensaje de error al usuario
    }
  };

  const handlePrerrequisitoClick = (id: number) => {
    setPrerrequisitosSeleccionados(prev => 
      prev.includes(id) 
        ? prev.filter(p => p !== id)
        : [...prev, id]
    );
  };

  const handleLogroClick = (id: number) => {
    setLogrosSeleccionados(prev => 
      prev.includes(id) 
        ? prev.filter(l => l !== id)
        : [...prev, id]
    );
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" sx={{ mb: 3 }}>Editar Silabo</Typography>
      
      <Paper 
        elevation={3} 
        sx={{ 
          p: 4, 
          bgcolor: 'white',
          borderRadius: 2,
          minHeight: '70vh'
        }}
      >
        <Box sx={{ display: 'flex', gap: 4 }}>
          {/* Columna de búsqueda */}
          <Box sx={{ width: '45%', display: 'flex', flexDirection: 'column', gap: 3 }}>
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Buscar Silabo por código
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                placeholder="Ingrese el id del silabo"
                value={idSilabo}
                onChange={(e) => setIdSilabo(e.target.value)}
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '&:hover fieldset': {
                      borderColor: '#1a237e',
                    },
                  },
                }}
              />
              <Button 
                variant="contained" 
                onClick={buscarPorId}
                sx={{ mt: 1, bgcolor: '#1a237e', '&:hover': { bgcolor: '#0d47a1' } }}
              >
                Buscar por ID
              </Button>
            </Box>

            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>
                Buscar Silabo por nombre
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                placeholder="Ingrese el nombre del silabo"
                value={nombreSilabo}
                onChange={(e) => setNombreSilabo(e.target.value)}
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '&:hover fieldset': {
                      borderColor: '#1a237e',
                    },
                  },
                }}
              />
              <Button 
                variant="contained" 
                onClick={buscarPorNombre}
                sx={{ mt: 1, bgcolor: '#1a237e', '&:hover': { bgcolor: '#0d47a1' } }}
              >
                Buscar por Nombre
              </Button>
            </Box>

            {error && (
              <Alert severity="error" sx={{ mt: 2 }}>
                {error}
              </Alert>
            )}

            {silaboSeleccionado && (
              <Alert severity="success" sx={{ mt: 2 }}>
                Silabo seleccionado: {silaboSeleccionado.nombre} (ID: {silaboSeleccionado.id})
              </Alert>
            )}
          </Box>

          {/* Columna del botón */}
          <Box sx={{ width: '45%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <Button 
              variant="contained" 
              onClick={handleOpenDialog}
              disabled={!silaboSeleccionado}
              sx={{
                bgcolor: '#1a237e',
                '&:hover': {
                  bgcolor: '#0d47a1',
                },
                px: 4,
                py: 2
              }}
            >
              Abrir Ventana
            </Button>
          </Box>
        </Box>
      </Paper>

      {/* Ventana emergente */}
      <Dialog 
        open={openDialog} 
        onClose={handleCloseDialog}
        maxWidth="lg"
        fullWidth
        PaperProps={{
          sx: {
            minHeight: '80vh',
            maxHeight: '90vh'
          }
        }}
      >
        <DialogTitle sx={{ 
          bgcolor: '#1a237e', 
          color: 'white',
          fontSize: '1.5rem',
          fontWeight: 'bold',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          px: 3
        }}>
          <IconButton 
            onClick={handlePrevious}
            disabled={currentSection === 0}
            sx={{ 
              color: 'white',
              '&:hover': {
                bgcolor: 'rgba(255, 255, 255, 0.1)'
              }
            }}
          >
            <ArrowBackIosNewIcon />
          </IconButton>
          {sections[currentSection]} - Silabo: {silaboSeleccionado?.nombre}
          <IconButton 
            onClick={handleNext}
            disabled={currentSection === sections.length - 1}
            sx={{ 
              color: 'white',
              '&:hover': {
                bgcolor: 'rgba(255, 255, 255, 0.1)'
              }
            }}
          >
            <ArrowForwardIosIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent sx={{ p: 3 }}>
          {currentSection === 0 ? (
            <Box sx={{ display: 'flex', gap: 4 }}>
              {/* Columna izquierda con inputs */}
              <Box sx={{ width: '30%', display: 'flex', flexDirection: 'column', gap: 3 }}>
                <Box>
                  <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }} >
                    Correo del Profesor
                  </Typography>
                  <TextField
                    fullWidth
                    variant="outlined"
                    placeholder="Ingrese el correo del profesor"
                    size="small"
                    value={correoProfesor}
                    onChange={(e) => setCorreoProfesor(e.target.value)}
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
                    Nombre del Profesor
                  </Typography>
                  <TextField
                    fullWidth
                    variant="outlined"
                    placeholder="Ingrese el nombre del profesor"
                    size="small"
                    value={nombreProfesor}
                    onChange={(e) => setNombreProfesor(e.target.value)}
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
                    Área de Estudios
                  </Typography>
                  <TextField
                    fullWidth
                    variant="outlined"
                    placeholder="Ingrese el área de estudios"
                    size="small"
                    value={areaEstudios}
                    onChange={(e) => setAreaEstudios(e.target.value)}
                    sx={{
                      '& .MuiOutlinedInput-root': {
                        '&:hover fieldset': {
                          borderColor: '#1a237e',
                        },
                      },
                    }}
                  />
                </Box>
              </Box>

              {/* Columna derecha con etiquetas */}
              <Box sx={{ width: '70%', display: 'flex', flexDirection: 'column', gap: 2 }}>
                <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 3 }}>
                  <Box sx={{ width: '45%' }}>
                    <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Nombre</Typography>
                    <Typography variant="body1" sx={{ p: 1, border: '1px solid #e0e0e0', borderRadius: 1 }}>
                      {datosCurso?.nombreCurso || ''}
                    </Typography>
                  </Box>
                  <Box sx={{ width: '30%' }}>
                    <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Código</Typography>
                    <Typography variant="body1" sx={{ p: 1, border: '1px solid #e0e0e0', borderRadius: 1 }}>
                      {datosCurso?.codigoCurso || ''}
                    </Typography>
                  </Box>
                  <Box sx={{ width: '30%' }}>
                    <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Plan de Estudios</Typography>
                    <Typography variant="body1" sx={{ p: 1, border: '1px solid #e0e0e0', borderRadius: 1 }}>
                      {datosCurso?.planDeEstudios || ''}
                    </Typography>
                  </Box>
                  <Box sx={{ width: '30%' }}>
                    <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Semestre</Typography>
                    <Typography variant="body1" sx={{ p: 1, border: '1px solid #e0e0e0', borderRadius: 1 }}>
                      {datosCurso?.numeroPeriodo || ''}
                    </Typography>
                  </Box>
                  <Box sx={{ width: '30%' }}>
                    <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Ciclo</Typography>
                    <Typography variant="body1" sx={{ p: 1, border: '1px solid #e0e0e0', borderRadius: 1 }}>
                      {datosCurso?.ciclo || ''}
                    </Typography>
                  </Box>
                </Box>
              </Box>
            </Box>
          ) : currentSection === 1 ? (
            <Box sx={{ display: 'flex', gap: 3 }}>
              <Box sx={{ width: '50%' }}>
                <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Prerrequisitos</Typography>
                <Box sx={{ 
                  p: 2, 
                  border: '1px solid #e0e0e0', 
                  borderRadius: 1, 
                  minHeight: '100px',
                  maxHeight: '200px',
                  overflowY: 'auto'
                }}>
                  {datosSilabo?.prerrequisitos?.map((prerrequisito: any, index: number) => (
                    <Box 
                      key={index} 
                      sx={{ 
                        mb: 1, 
                        p: 1, 
                        bgcolor: prerrequisitosSeleccionados.includes(prerrequisito.idPrerrequisito) ? '#ffebee' : '#f5f5f5', 
                        borderRadius: 1,
                        cursor: 'pointer',
                        '&:hover': {
                          bgcolor: prerrequisitosSeleccionados.includes(prerrequisito.idPrerrequisito) ? '#ffcdd2' : '#eeeeee'
                        }
                      }}
                      onClick={() => handlePrerrequisitoClick(prerrequisito.idPrerrequisito)}
                    >
                      <Typography variant="body1">
                        {prerrequisito.codigoCurso} - {prerrequisito.nombreCurso}
                      </Typography>
                    </Box>
                  ))}
                  {nuevosPrerrequisitos.map((prerrequisito) => (
                    <Box 
                      key={prerrequisito.id} 
                      sx={{ 
                        mb: 1, 
                        p: 1, 
                        bgcolor: '#e3f2fd',
                        borderRadius: 1,
                        cursor: 'pointer',
                        '&:hover': {
                          bgcolor: '#bbdefb'
                        }
                      }}
                      onDoubleClick={() => handleDobleClickPrerrequisito(prerrequisito.id)}
                    >
                      <Typography variant="body1">
                        {prerrequisito.codigo} - {prerrequisito.nombre}
                      </Typography>
                    </Box>
                  ))}
                </Box>
              </Box>

              {/* Nuevo Prerrequisito */}
              <Box sx={{ width: '50%' }}>
                <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Nuevo Prerrequisito</Typography>
                <TextField
                  fullWidth
                  variant="outlined"
                  placeholder="Código"
                  size="small"
                  value={nuevoPrerrequisito.codigo}
                  onChange={(e) => setNuevoPrerrequisito({...nuevoPrerrequisito, codigo: e.target.value})}
                  sx={{ mb: 2 }}
                />
                <TextField
                  fullWidth
                  variant="outlined"
                  placeholder="Nombre"
                  size="small"
                  value={nuevoPrerrequisito.nombre}
                  onChange={(e) => setNuevoPrerrequisito({...nuevoPrerrequisito, nombre: e.target.value})}
                  sx={{ mb: 2 }}
                />
                <Button 
                  variant="contained" 
                  onClick={handleAñadirPrerrequisito}
                  sx={{ 
                    bgcolor: '#1a237e',
                    '&:hover': { bgcolor: '#0d47a1' }
                  }}
                >
                  Añadir
                </Button>
              </Box>
            </Box>
          ) : currentSection === 2 ? (
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Sumilla - Solo Vista </Typography>
              <Typography variant="body1" sx={{ p: 2, border: '1px solid #e0e0e0', borderRadius: 1, minHeight: '200px' }}>
                {datosCurso?.sumilla || ''}
              </Typography>
            </Box>
          ) : currentSection === 3 ? (
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Competencias</Typography>
              <Box sx={{ 
                p: 2, 
                border: '1px solid #e0e0e0', 
                borderRadius: 1, 
                minHeight: '200px',
                maxHeight: '400px',
                overflowY: 'auto'
              }}>
                {datosSilabo?.competencias?.map((competencia: any, index: number) => (
                  <Box key={index} sx={{ mb: 2, p: 2, bgcolor: '#f5f5f5', borderRadius: 1 }}>
                    <Typography variant="subtitle2" sx={{ color: '#1a237e', fontWeight: 'bold' }}>
                      {competencia.codigo}
                    </Typography>
                    <Typography variant="body1">
                      {competencia.descripcion}
                    </Typography>
                  </Box>
                ))}
              </Box>
            </Box>
          ) : currentSection === 4 ? (
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Logros</Typography>
              <Box sx={{ mb: 2 }}>
                <Typography variant="subtitle2" sx={{ mb: 1, color: '#1a237e' }}>Seleccione una Competencia</Typography>
                <TextField
                  select
                  fullWidth
                  variant="outlined"
                  value={competenciaSeleccionada || ''}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) => handleCompetenciaChange(e as any)}
                  SelectProps={{
                    native: true,
                  }}
                  sx={{
                    '& .MuiOutlinedInput-root': {
                      '&:hover fieldset': {
                        borderColor: '#1a237e',
                      },
                    },
                  }}
                >
                  <option value="">Seleccione una competencia</option>
                  {datosSilabo?.competencias?.map((competencia: any) => (
                    <option key={competencia.id} value={competencia.id}>
                      {competencia.codigo} - {competencia.nombre}
                    </option>
                  ))}
                </TextField>
              </Box>
              
              {/* Lista de Logros */}
              <Box sx={{ 
                p: 2, 
                border: '1px solid #e0e0e0', 
                borderRadius: 1, 
                height: '200px',
                overflowY: 'auto',
                mb: 2
              }}>
                {logros.length > 0 ? (
                  logros.map((logro: any, index: number) => (
                    <Box 
                      key={index} 
                      sx={{ 
                        mb: 2, 
                        p: 2, 
                        bgcolor: logrosSeleccionados.includes(logro.id) ? '#ffebee' : '#f5f5f5', 
                        borderRadius: 1,
                        cursor: 'pointer',
                        '&:hover': {
                          bgcolor: logrosSeleccionados.includes(logro.id) ? '#ffcdd2' : '#eeeeee'
                        }
                      }}
                      onClick={() => handleLogroClick(logro.id)}
                    >
                      <Typography variant="subtitle2" sx={{ color: '#1a237e', fontWeight: 'bold' }}>
                        {logro.codigoLogro}
                      </Typography>
                      <Typography variant="body1">
                        {logro.descripcionLogro}
                      </Typography>
                    </Box>
                  ))
                ) : (
                  <Typography variant="body1" sx={{ color: 'text.secondary', textAlign: 'center', py: 4 }}>
                    {competenciaSeleccionada ? 'No hay logros para esta competencia' : 'Seleccione una competencia para ver sus logros'}
                  </Typography>
                )}
                {nuevosLogros.map((logro) => (
                  <Box 
                    key={logro.id} 
                    sx={{ 
                      mb: 2, 
                      p: 2, 
                      bgcolor: '#e3f2fd',
                      borderRadius: 1,
                      cursor: 'pointer',
                      '&:hover': {
                        bgcolor: '#bbdefb'
                      }
                    }}
                    onDoubleClick={() => handleDobleClickLogro(logro.id)}
                  >
                    <Typography variant="body1">
                      {logro.descripcion}
                    </Typography>
                  </Box>
                ))}
              </Box>

              {/* Input para nuevo logro */}
              <Box sx={{ display: 'flex', gap: 2 }}>
                <TextField
                  fullWidth
                  variant="outlined"
                  placeholder="Ingrese un nuevo logro"
                  size="small"
                  value={nuevoLogro}
                  onChange={(e) => setNuevoLogro(e.target.value)}
                  sx={{
                    '& .MuiOutlinedInput-root': {
                      '&:hover fieldset': {
                        borderColor: '#1a237e',
                      },
                    },
                  }}
                />
                <Button 
                  variant="contained" 
                  onClick={handleAñadirLogro}
                  sx={{ 
                    bgcolor: '#1a237e',
                    '&:hover': { bgcolor: '#0d47a1' }
                  }}
                >
                  Añadir
                </Button>
              </Box>
            </Box>
          ) : currentSection === 5 ? (
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Estrategia Didáctica</Typography>
              <TextField
                multiline
                fullWidth
                rows={10}
                variant="outlined"
                placeholder="Ingrese la estrategia didáctica"
                value={datosSilabo?.estrategiaDidactica || ''}
                onChange={(e) => setDatosSilabo({...datosSilabo, estrategiaDidactica: e.target.value})}
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '&:hover fieldset': {
                      borderColor: '#1a237e',
                    },
                  },
                }}
              />
            </Box>
          ) : currentSection === 6 ? (
            <Box>
              <Typography variant="subtitle1" sx={{ mb: 1, color: '#1a237e' }}>Bibliografía</Typography>
              <TextField
                multiline
                fullWidth
                rows={10}
                variant="outlined"
                placeholder="Ingrese la bibliografía"
                value={datosSilabo?.bibliografia || ''}
                onChange={(e) => setDatosSilabo({...datosSilabo, bibliografia: e.target.value})}
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '&:hover fieldset': {
                      borderColor: '#1a237e',
                    },
                  },
                }}
              />
            </Box>
          ) : (
            <Typography>
              Contenido de {sections[currentSection]}
            </Typography>
          )}
        </DialogContent>
        <DialogActions sx={{ p: 2, display: 'flex', justifyContent: 'space-between' }}>
          <Button 
            onClick={handleCloseDialog} 
            variant="contained"
            sx={{
              bgcolor: '#1a237e',
              '&:hover': {
                bgcolor: '#0d47a1',
              }
            }}
          >
            Cerrar
          </Button>
          <Button 
            variant="contained"
            onClick={handleGuardar}
            sx={{
              bgcolor: '#1a237e',
              '&:hover': {
                bgcolor: '#0d47a1',
              }
            }}
          >
            Guardar
          </Button>
        </DialogActions>
      </Dialog>

      {/* Diálogo de confirmación */}
      <Dialog
        open={openConfirmDialog}
        onClose={() => setOpenConfirmDialog(false)}
      >
        <DialogTitle>Confirmar</DialogTitle>
        <DialogContent>
          <Typography>¿Estás seguro de que deseas guardar los cambios?</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenConfirmDialog(false)}>Cancelar</Button>
          <Button onClick={handleConfirmarGuardar} variant="contained" color="primary">
            Confirmar
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}; 