import { CRUD } from "./crud";
import type { Record as Base } from "./crud";
import type { HttpSession } from "./session";

export default class UserCRUD extends CRUD<Record> {
  constructor(session: HttpSession) {
    super(session, "user");
  }
}

/**
 * Types
 */
export interface Record extends User, Base {}

export interface User {
  identification: string;
  name: string;
  address: string;
  cellPhone: string;
  email: string;
  password: string;
  zone: string;
  type: string;
}
