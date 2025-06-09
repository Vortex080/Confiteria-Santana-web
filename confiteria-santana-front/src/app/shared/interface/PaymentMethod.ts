export interface PaymentMethod {
    id: number;
    provider: string;
    token: string;
    type: string;
    last4: string;
    expiryMonth: number;
    expiryYear: number;
    brand: string;
    user: number;
}