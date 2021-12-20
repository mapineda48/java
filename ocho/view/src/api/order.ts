import axios from "axios";
import type { User, Laptop } from ".";

export async function remove(id: number) {
  await axios.delete(`/api/order/${id}`);
}

export async function fetchAll() {
  const res = await axios.get("/api/order/all");

  return res.data as Record[];
}

export async function insert(order: Order) {
  const res = await axios.post("/api/order/new", { ...order, id: Date.now() });

  return res.data as Record;
}

export async function update(order: Record) {
  const res = await axios.put("/api/order/update", { ...order });

  return res.data as Record;
}

/**
 * Types
 */
export interface Record extends Order {
  id: number;
}

export interface Order {
  id: number;
  registerDay: string;
  status: string;
  salesMan: User.Record;
  products: Laptop.Record[];
  quantities: {
    [K: string]: number;
  };
}
