import { Routes } from '@angular/router';
import { ClienteComponent } from './telas/cliente/cliente';
import { AdminComponent } from './telas/admin/admin';
import { Login } from './telas/login/login';

export const routes: Routes = [
  { 
    path: '',
    redirectTo: 'login',
    pathMatch: 'full' 
  },

  { 
    path: 'cliente',
    title: 'Página do cliente',
    component: ClienteComponent 
  },

  {
    path: 'admin',
    title: 'Página do administrador',
    component: AdminComponent
  },

  {
    path: 'login',
    title: 'Página de login',
    component: Login
  }
  
];