import { Address } from "./address";

export interface User {
    username: string;
    name:     string;
    lastname: string;
    photo:    string;
    email:    string;
    address:  Address;
    rol:      string;
    phone:    number;
    pass:     string;
}
