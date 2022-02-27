import { CRUD } from "./crud";
import { BlobJSON } from "./util";
import type { Record as Base } from "./crud";
import type { HttpSession } from "./session";

export default class UserCRUD extends CRUD<Record> {
  private apiDashboardData: string;

  constructor(session: HttpSession) {
    super(session, "user");

    this.apiDashboardData = this.apiBase + "dashboard";
  }

  async getUserDashboardData() {
    const res = await this.session.get(this.apiDashboardData);

    return res.data as UserDashboard;
  }

  async insertForm(record: UserForm) {
    const { avatar, ...user } = record;

    const formData = new FormData();

    formData.append("user", new BlobJSON(user));

    if (avatar && avatar[0]) {
      formData.append("avatar", avatar[0]);
    }

    const res = await this.session.post(this.apiNew, formData);

    return res.data as Record;
  }

  async updateForm(record: UserForm) {
    const { avatar, ...user } = record;

    const formData = new FormData();

    console.log(record);

    formData.append("user", new BlobJSON(user));

    if (avatar && avatar[0]) {
      formData.append("avatar", avatar[0]);
    }

    const res = await this.session.put(this.apiUpdate, formData);

    return res.data as Record;
  }
}

/**
 * Types
 */
export interface Record extends User, Base {}

export interface UserForm extends Omit<User, "urlAvatar"> {
  avatar: FileList | null;
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
  urlAvatar: string;
}

export interface UserDashboard {
  fullName: string;
  urlAvatar: string;
}
