import { Drawer, List, ListItem, ListItemIcon, ListItemText, styled, Typography, Box } from '@mui/material';
import { Add, Edit, Print } from '@mui/icons-material';
import { useNavigate, useLocation } from 'react-router-dom';

const StyledDrawer = styled(Drawer)({
  width: 240,
  flexShrink: 0,
  '& .MuiDrawer-paper': {
    width: 240,
    boxSizing: 'border-box',
    backgroundColor: '#1a237e',
    color: 'white',
  },
});

const StyledListItem = styled(ListItem)<{ selected?: boolean }>(({ selected }) => ({
  '&:hover': {
    backgroundColor: 'rgba(255, 255, 255, 0.1)',
  },
  ...(selected && {
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    '&:hover': {
      backgroundColor: 'rgba(255, 255, 255, 0.25)',
    },
  }),
}));

const menuItems = [
  { text: 'Crear Silabo', icon: <Add />, path: '/crear' },
  { text: 'Editar Silabo', icon: <Edit />, path: '/editar' },
  { text: 'Imprimir Silabo', icon: <Print />, path: '/imprimir' },
];

export const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <StyledDrawer variant="permanent">
      <Box sx={{ p: 2, borderBottom: '1px solid rgba(255, 255, 255, 0.12)' }}>
        <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
          Menú
        </Typography>
      </Box>
      <List>
        {menuItems.map((item) => (
          <StyledListItem
            key={item.text}
            onClick={() => navigate(item.path)}
            selected={location.pathname === item.path}
          >
            <ListItemIcon sx={{ color: 'white' }}>
              {item.icon}
            </ListItemIcon>
            <ListItemText primary={item.text} />
          </StyledListItem>
        ))}
      </List>
    </StyledDrawer>
  );
}; 