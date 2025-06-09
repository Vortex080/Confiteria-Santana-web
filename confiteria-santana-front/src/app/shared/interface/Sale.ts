import { SaleLine } from "./SaleLine";

export interface Sale {
    id: number;
    date: string;
    total: number;
    metodoPago: string;
    line: SaleLine[];
}