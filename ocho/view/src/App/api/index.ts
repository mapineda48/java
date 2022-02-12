import UserCRUD from "./user";
import OrderCRUD from "./order";
import LaptopCRUD from "./laptop";
import { session } from "./session";
import { ensureThis } from "./util";

import type { HttpSession } from "./session";
import type * as User from "./user";
import type * as Laptop from "./laptop";
import type * as Order from "./order";

export async function signin(email: string, password: string) {
  const httpSession = await session.signin(email, password);

  return createAPI(httpSession);
}

export async function validateSession() {
  const httpSession = await session.getCurrent();

  if (!httpSession) {
    return null;
  }

  return createAPI(httpSession);
}

export async function createAPI(session: HttpSession) {
  return {
    user: ensureThis(new UserCRUD(session)),
    order: ensureThis(new OrderCRUD(session)),
    laptop: ensureThis(new LaptopCRUD(session)),
  };
}

export const logout = session.logout;

/**
 * Types
 */
export type Api = ReturnType<typeof createAPI> extends Promise<infer R>
  ? R
  : never;

export type { HttpSession, User, Order, Laptop };
