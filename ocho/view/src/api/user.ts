import axios from "axios";

export async function remove(id: number) {
  console.log({ id });
  await axios.delete(`/api/user/${id}`);
}

export async function fetchAll() {
  const res = await axios.get("/api/user/all");

  return res.data as Record[];
}

export async function insert(user: User) {
  const exists: boolean = (
    await axios.get(`/api/user/emailexist/${user.email}`)
  ).data;

  if (exists) {
    throw new Error("El email ya esta registrado");
  }

  const res = await axios.post("/api/user/new", { ...user, id: Date.now() });

  return res.data as Record;
}

export async function update(user: Record) {
  const res = await axios.put("/api/user/update", { ...user });

  return res.data as Record;
}

/**
 * Types
 */
export interface Record extends User {
  id: number;
}

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
