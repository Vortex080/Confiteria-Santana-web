import { Address } from "./address";

export interface User {
    id: number;
    username: string;
    name: string;
    lastname: string;
    photo: string;
    email: string;
    address: Address;
    rol: string;
    phone: number;
    pass: string;
}


export interface LoginRequest {
    email: string;
    pass: string;
}