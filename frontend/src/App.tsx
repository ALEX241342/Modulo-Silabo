import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Box, CssBaseline } from '@mui/material';
import { Sidebar } from './components/Sidebar';
import { Crear } from './pages/Crear';
import { Editar } from './pages/Editar';
import { Imprimir } from './pages/Imprimir';

function App() {
  return (
    <BrowserRouter>
      <CssBaseline />
      <Box sx={{ display: 'flex', minHeight: '100vh', bgcolor: '#e3f2fd' }}>
        <Sidebar />
        <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
          <Routes>
            <Route path="/" element={<Navigate to="/crear" replace />} />
            <Route path="/crear" element={<Crear />} />
            <Route path="/editar" element={<Editar />} />
            <Route path="/imprimir" element={<Imprimir />} />
          </Routes>
        </Box>
      </Box>
    </BrowserRouter>
  );
}

export default App;
