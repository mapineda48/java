import { CRUD } from "./crud";
import type { Record as Base } from "./crud";
import type { HttpSession } from "./session";

export default class LaptopCRUD extends CRUD<Record> {
  constructor(session: HttpSession) {
    super(session, "laptop");
  }
}

/**
 * Types
 */
export interface Record extends Laptop, Base {}

export interface Laptop {
  brand: string;
  model: string;
  procesor: string;
  os: string;
  description: string;
  memory: string;
  hardDrive: string;
  availability: boolean;
  price: number;
  quantity: number;
  photography: string;
}
