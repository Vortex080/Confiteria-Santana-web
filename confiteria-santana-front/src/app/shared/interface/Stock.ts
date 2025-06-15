import { Product } from "./product";

export interface Stock {
    id: number;
    product: Product;
    quantity: number;
}