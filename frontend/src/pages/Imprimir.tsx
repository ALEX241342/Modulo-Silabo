import { Typography, Box, TextField, Button, Alert } from '@mui/material';
import { useState } from 'react';
import { silaboService } from '../services/silaboService';

export const Imprimir = () => {
  const [idSilabo, setIdSilabo] = useState<string>('');
  const [error, setError] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);

  const handleImprimir = async () => {
    if (!idSilabo) {
      setError('Por favor ingrese un ID de silabo');
      return;
    }

    try {
      setLoading(true);
      setError('');
      await silaboService.imprimirSilabo(Number(idSilabo));
    } catch (error) {
      setError('Error al generar el documento. Por favor intente nuevamente.');
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    if (value === '' || /^\d+$/.test(value)) {
      setIdSilabo(value);
      setError('');
    }
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" sx={{ mb: 3 }}>Imprimir Silabo</Typography>
      
      <Box sx={{ maxWidth: 400 }}>
        <TextField
          fullWidth
          label="ID Silabo"
          value={idSilabo}
          onChange={handleInputChange}
          error={!!error}
          helperText={error}
          sx={{ mb: 2 }}
        />
        
        <Button 
          variant="contained" 
          onClick={handleImprimir}
          disabled={loading}
        >
          {loading ? 'Generando...' : 'Imprimir Silabo'}
        </Button>
      </Box>
    </Box>
  );
}; 