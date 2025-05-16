import { Typography, Box, Paper, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions, IconButton } from '@mui/material';
import { useState } from 'react';
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

export const Editar = () => {
  const [openDialog, setOpenDialog] = useState(false);
  const [currentSection, setCurrentSection] = useState(0);

  const sections = [
    "Información General",
    "Sumilla",
    "Capacidades",
    "Estrategia Didáctica",
    "Evaluación",
    "Bibliografía"
  ];

  const handleOpenDialog = () => {
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
                Buscar Curso por código
              </Typography>
              <TextField
                fullWidth
                variant="outlined"
                placeholder="Ingrese el código del curso"
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
              <TextField
                fullWidth
                variant="outlined"
                placeholder="Ingrese el nombre del curso"
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

          {/* Columna del botón */}
          <Box sx={{ width: '45%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <Button 
              variant="contained" 
              onClick={handleOpenDialog}
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
        maxWidth="md"
        fullWidth
        PaperProps={{
          sx: {
            minHeight: '60vh',
            maxHeight: '80vh'
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
          {sections[currentSection]}
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
          <Typography>
            Contenido de {sections[currentSection]}
          </Typography>
        </DialogContent>
        <DialogActions sx={{ p: 2 }}>
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
        </DialogActions>
      </Dialog>
    </Box>
  );
}; 