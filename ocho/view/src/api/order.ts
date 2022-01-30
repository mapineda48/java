import { CRUD } from "./crud";
import type { Record as Base } from "./crud";
import type { HttpSession } from "./session";
import type { Record as User } from "./user";
import type { Record as Laptop } from "./laptop";

export default class OrderCRUD extends CRUD<Record> {
  constructor(session: HttpSession) {
    super(session, "order");
  }
}

/**
 * Types
 */
export interface Record extends Order, Base {}

export interface Order {
  registerDay: string;
  status: string;
  salesMan: User;
  products: Laptop[];
  quantities: {
    [K: string]: number;
  };
}
