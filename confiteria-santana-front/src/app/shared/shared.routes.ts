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
            }
        ]
    },
    {
        path: 'admin',
        component: AdminComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent },
            { path: 'product', component: AdminProductosComponent },
            { path: 'clientes', component: ClientesComponent },
            { path: 'categorias', component: CategoryComponent },
            { path: 'tpv', component: TpvComponent },
            { path: 'ventas', component: VentasComponent },
            { path: 'pedidos', component: PedidosComponent },
            { path: 'almacen', component: AlmacenComponent },
            { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
        ]

    },
    {

        path: 'tpv',
        component: TpvComponent
    },
    { path: 'pedidos', component: PedidosComponent },
    { path: 'almacen', component: AlmacenComponent },
    { path: '**', redirectTo: '' }


];

export default sharedRoutes;
