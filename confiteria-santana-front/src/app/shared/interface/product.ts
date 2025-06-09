import { Alergen } from "./alergen";
import { Category } from "./category";
import { Photo } from "./photo";

export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    unit: string;
    alergens: Alergen[];
    category: Category;
    photos: Photo[];
}



