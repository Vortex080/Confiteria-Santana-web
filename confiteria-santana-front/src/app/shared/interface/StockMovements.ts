import { MovementReason } from "./MovementReason";
import { MovementType } from "./MovementType";
import { Product } from "./product";

export interface StockMovementsInput {
    id: number;
    product: Product;
    type: MovementType;
    unit: string;
    quantity: number;
    reason: MovementReason;
    create_at: string;
}

export interface StockMovementsOutput {
    id: number;
    product: number;
    type: MovementType;
    unit: string;
    quantity: number;
    reason: MovementReason;
    create_at?: Date;
}