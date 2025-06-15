export interface Payments {
    id: number;
    user: number;
    order: number;
    paymentMethod: number;
    provider: string;
    status: string;
    currency: string;
}