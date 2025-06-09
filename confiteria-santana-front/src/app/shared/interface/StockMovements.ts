import { MovementReason } from "./MovementReason";
import { MovementType } from "./MovementType";

export interface StockMovements {
    id: number;
    product: number;
    type: MovementType;
    unit: string;
    reason: MovementReason;
}