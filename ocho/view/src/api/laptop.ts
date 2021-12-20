import axios from "axios";

export async function remove(id: number) {
  await axios.delete(`/api/laptop/${id}`);
}

export async function fetchAll() {
  const res = await axios.get("/api/laptop/all");

  return res.data as Record[];
}

export async function insert(laptop: Laptop) {
  const res = await axios.post("/api/laptop/new", { ...laptop, id: Date.now() });

  return res.data as Record;
}

export async function update(laptop: Record) {
  const res = await axios.put("/api/laptop/update", { ...laptop });

  return res.data as Record;
}

/**
 * Types
 */
export interface Record extends Laptop {
  id: number;
}

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
