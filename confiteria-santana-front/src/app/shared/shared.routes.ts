import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LayoutComponent } from './components/layout/layout.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProductosComponent } from './components/productos/productos.component';
import { SobrenosotrosComponent } from './pages/sobrenosotros/sobrenosotros.component';
import { ProductoComponent } from './pages/producto/producto.component';
import { AdminComponent } from './pages/admin/admin.component';
import { DashboardComponent } from '../administration/components/dashboard/dashboard.component';
import { AdminProductosComponent } from '../administration/components/admin-productos/admin-productos.component';
import { ClientesComponent } from '../administration/components/clientes/clientes.component';
import { CategoryComponent } from '../administration/components/category/category.component';
import { TpvComponent } from '../administration/components/tpv/tpv.component';
import { VentasComponent } from '../administration/components/ventas/ventas.component';
import { PedidosComponent } from '../administration/components/pedidos/pedidos.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { CartComponent } from './components/cart/cart.component';
import { AlmacenComponent } from '../administration/components/almacen/almacen.component';
import { AlergenoComponent } from '../administration/components/alergeno/alergeno.component';
import { adminGuard } from './guards/Admin.guard';
import { TpvGuard } from './guards/Tpv.guard';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';


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
            },
            {
                path: 'sobrenosotros',
                component: SobrenosotrosComponent
            },
            {
                path: 'producto',
                component: ProductoComponent
            },
            {
                path: 'perfil',
                component: UserProfileComponent,
            },
            {
                path: 'carrito',
                component: CartComponent,
            },
            {
                path: 'producto/:id',
                loadComponent: () => import('./pages/producto/producto.component').then(m => m.ProductoComponent)
            }

        ]
    },
    {
        path: 'admin',
        component: AdminComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent, canMatch: [adminGuard] },
            { path: 'product', component: AdminProductosComponent, canMatch: [adminGuard] },
            { path: 'clientes', component: ClientesComponent, canMatch: [adminGuard] },
            { path: 'categorias', component: CategoryComponent, canMatch: [adminGuard] },
            { path: 'tpv', component: TpvComponent, canMatch: [adminGuard] },
            { path: 'ventas', component: VentasComponent, canMatch: [adminGuard] },
            { path: 'pedidos', component: PedidosComponent, canMatch: [adminGuard] },
            { path: 'almacen', component: AlmacenComponent, canMatch: [adminGuard] },
            { path: 'alergen', component: AlergenoComponent, canMatch: [adminGuard] },
            { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
        ],
        canMatch: [adminGuard]

    },
    {

        path: 'tpv',
        component: TpvComponent,
        canMatch: [TpvGuard]
    },
    { path: 'pedidos', component: PedidosComponent },
    { path: 'almacen', component: AlmacenComponent },
    { path: '401', component: UnauthorizedComponent },
    { path: '**', redirectTo: '' }


];

export default sharedRoutes;
