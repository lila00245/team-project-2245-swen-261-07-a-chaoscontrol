import { Need } from "./Need";

export interface User{
    name:string,
    password:string,
    basket:Need[],
    role:string // admin or user
    totalFunding: number;
}