import { Address } from "./address";
import { PaymentMethod } from "./PaymentMethod";
import { Sale } from "./Sale";
import { User } from "./user";

export interface Order {
    id: number;
    user: User;
    total: number;
    shipping: Address;
    paymentMethod: PaymentMethod;
    billingAddress: Address;
    sale: Sale;
    created_at?: string;
    createdAt?: Date;
}


export interface OrderResponse {
    id: number;
    userId: number;
    username: string;
    paymentMethodId: number;
    paymentMethodName: string;
    saleId: number;
    shipping: Address;
    billingAddress: Address;
    total: number;
    created_at: string;
}