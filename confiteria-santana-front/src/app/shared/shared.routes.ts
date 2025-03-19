import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LayoutComponent } from './components/layout/layout.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProductosComponent } from './components/productos/productos.component';

export const sharedRoutes: Routes = [

    {
        path: '',
        component: LayoutComponent,
        children: [
            {
                path: '',
                component: HomeComponent
            },
            {
                path: 'iniciosesion',
                component: LoginComponent
            },
            {
                path: 'registro',
                component: RegisterComponent
            },
            {
                path: 'productos',
                component: ProductosComponent
            }
        ]
    }

];

export default sharedRoutes;
