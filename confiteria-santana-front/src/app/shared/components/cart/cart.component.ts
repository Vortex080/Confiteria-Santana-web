import { Component, ElementRef, ViewChild } from '@angular/core';
import { CartItem, CartService } from '../../service/CartService.service';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { User } from '../../interface/user';
import { Address } from '../../interface/address';
import { UserService } from '../../service/User.service';
import { LocalStorageService } from '../../service/LocalStorage.service';
import { PaymentMethod } from '../../interface/PaymentMethod';
import { PaymentMethodService } from '../../service/PaymentMethod.service';
import { Order } from '../../interface/Order';
import { OrderService } from '../../service/Order.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './cart.component.html',
})
export class CartComponent {
  cartItems: CartItem[] = [];

  @ViewChild('modal', { static: true }) modalRef!: ElementRef<HTMLDialogElement>;


  direccion: Address = {
    id: '',
    street: '',
    number: '',
    flat: '',
    door: '',
    city: '',
    state: '',
    country: '',
    postalCode: ''
  };

  nuevoMetodoPago: PaymentMethod = {
    id: 0,
    provider: '',
    token: '',
    type: '',
    last4: '',
    expiryMonth: new Date().getMonth() + 1,
    expiryYear: new Date().getFullYear(),
    brand: '',
    userId: 0
  };

  mostrarFormularioPago = false;

  paymentMethod: any = null;
  paymentMethods: any[] = [];


  constructor(
    private cartService: CartService,
    private userService: UserService,
    private localStorageService: LocalStorageService,
    private paymentMethodService: PaymentMethodService,
    private orderService: OrderService
  ) { }

  ngOnInit() {
    this.cartItems = this.cartService.getItems();

    const user = this.localStorageService.getUser();
    if (user && user.id) {
      this.userService.getUserById(user.id).subscribe((data: User) => {
        this.direccion = { ...data.address };

        // Obtener todos los métodos de pago y filtrar por userId
        this.paymentMethodService.getallMethod().subscribe((metodos: PaymentMethod[]) => {
          this.paymentMethods = metodos.filter(m => m.userId === data.id);
          this.paymentMethod = this.paymentMethods[0] || null;
        });
      });
    }
  }


  get total(): number {
    return this.cartItems.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
  }

  removeItem(index: number) {
    this.cartService.removeItem(index);
    this.cartItems = this.cartService.getItems();
  }

  actualizarCantidad(index: number, cantidad: number) {
    if (cantidad < 1) cantidad = 1;
    this.cartItems[index].quantity = cantidad;
    this.cartService.updateItem(index, cantidad);
  }

  makeOrder() {
    if (!this.cartItems.length) return;
    this.modalRef.nativeElement.showModal();
  }



  confirmarPedido() {
    const user = this.userService.getUserById(this.localStorageService.getUser().id);
    if (!user) return;

    // Convertir cartItems en sale.lines
    const lineasVenta = this.cartItems.map((item, index) => ({
      id: 0,
      product: item.product,
      cuantity: item.quantity,
      price: item.product.price,
      subtotal: item.quantity * item.product.price
    }));

    const venta = {
      id: 0,
      date: new Date().toISOString(),
      total: this.total,
      metodoPago: 'API', // o lo que uses como descripción
      line: lineasVenta
    };

    const order = {
      id: 0,
      user: user,
      total: this.total,
      shipping: this.direccion,
      billingAddress: this.direccion,
      paymentMethod: {
        id: 0,
        provider: '',
        token: '',
        type: '',
        last4: '',
        expiryMonth: 0,
        expiryYear: 0,
        brand: '',
        userId: 0
      },
      sale: venta,
      created_at: new Date().toISOString()
    };

    // this.orderService.crearOrder(order).subscribe({
    //   next: (res) => {
    //     console.log('Pedido creado correctamente:', res);
    //     this.cartService.clearCart();
    //     this.cartItems = [];
    //     this.modalRef.nativeElement.close();
    //   },
    //   error: (err) => {
    //     console.error('Error al crear pedido:', err);
    //     alert('Error al procesar el pedido.');
    //   }
    // });
  }




}
