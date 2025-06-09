export interface Order {
    id: number;
    user: number;
    total: number;
    shipping: number;
    paymentMethod: number;
    billingAddress: number;
    sale: number;
    createdAt: Date;
    created_at: string;
}