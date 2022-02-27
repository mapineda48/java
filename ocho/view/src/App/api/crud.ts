import type { HttpSession } from "./session";

export class CRUD<T extends Record> {
  protected readonly apiBase: string;
  protected readonly apiFecthAll: string;
  protected readonly apiNew: string;
  protected readonly apiUpdate: string;

  constructor(protected session: HttpSession, table: string) {
    this.apiBase = `/api/${table}/`;
    this.apiFecthAll = this.apiBase + "all";
    this.apiNew = this.apiBase + "new";
    this.apiUpdate = this.apiBase + "update";
  }

  async remove(id: number) {
    await this.session.delete(this.apiBase + id);
  }

  async fetchAll() {
    const res = await this.session.get(this.apiFecthAll);

    return res.data as T[];
  }

  async insert(record: Omit<T, "id">) {
    const res = await this.session.post(this.apiNew, { ...record });

    return res.data as T;
  }

  async update(record: T) {
    const res = await this.session.put(this.apiUpdate, { ...record });

    return res.data as T;
  }
}

/**
 * Types
 */

export interface Record {
  id: number;
}
