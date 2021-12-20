import axios from "axios";
import * as user from "./user";
import * as laptop from "./laptop";
import * as order from "./order";

import type * as User from "./user";
import type * as Laptop from "./laptop";
import type * as Order from "./order";

export async function signIn(mail: string, password: string) {
  const res = await axios.get(`/api/user/${mail}/${password}`);

  return res.data as User.Record;
}

const api = {
  signIn,
  user,
  laptop,
  order,
};

export default api;

/**
 * Types
 */
export type { User, Laptop, Order };
