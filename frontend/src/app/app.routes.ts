import { Routes } from '@angular/router';
import { ClienteComponent } from './telas/cliente/cliente';

export const routes: Routes = [
  { path: 'cliente', component: ClienteComponent },
  { path: '', redirectTo: 'cliente', pathMatch: 'full' }
];