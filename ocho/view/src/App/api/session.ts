import { createInstance } from "./axios";
import type { AxiosInstance, AxiosResponse, AxiosError } from "axios";

const KEY_AUTH = "Cache-Local-Storage";

export const storage = {
  set(auth: Auth) {
    localStorage.setItem(KEY_AUTH, JSON.stringify(auth));
  },

  get(): Auth | null {
    const cache = localStorage.getItem(KEY_AUTH);

    if (!cache) {
      return null;
    }

    return JSON.parse(cache);
  },

  clear() {
    localStorage.removeItem(KEY_AUTH);
  },
};

const Authorization = "Authorization";

const PREFIX_TOKEN = "Bearer ";

const BASE_URL = "/";

const API_SIGNIN = "/api/auth/signin";
const API_TOKEN_REFRESH = "/api/auth/refresh";
const API_TOKEN_VALIDATE = "/api/auth/validate";

const apiPublic = createInstance();

export async function signin(email: string, password: string) {
  const { data } = await apiPublic.post(API_SIGNIN, { email, password });

  const session = new HttpSession(data);

  return session;
}

export async function getCurrent() {
  const cache = storage.get();

  if (!cache) {
    return null;
  }

  const session = new HttpSession(cache);

  try {
    await session.valdiate();

    return session;
  } catch (error) {
    console.log(error);
    return null;
  }
}

export const session = {
  signin,
  getCurrent,
  logout() {
    storage.clear();
  },
};

export class HttpSession {
  public api: AxiosInstance;

  constructor(private auth: Auth) {
    console.log(auth);

    this.api = this.createInstance(auth.accessToken);
  }

  public delete(url: string) {
    return this.doRequest("delete", [url]);
  }

  public async put(url: string, data?: any) {
    return this.doRequest("put", [url, data]);
  }

  public async post(url: string, data?: any) {
    return this.doRequest("post", [url, data]);
  }

  public async get(url: string) {
    return this.doRequest("get", [url]);
  }

  public async valdiate() {
    await this.doRequest("get", [API_TOKEN_VALIDATE]);

    return this;
  }

  /**
   * most likely you are wondering why?, my intention is that the session handler is able to
   * refresh the token in case a request fails because it is not authorized, this method the
   * first time you receive this 401 error, it should try to refresh the token with the
   * corresponding api, when obtaining the new token it performs the request again, if it
   * fails a second time or the attempt to refresh fails, the method will fail.
   * @param verb http verb
   * @param args arguments to pass to axios method
   * @param tokenWasRefresh token was refresh
   * @returns
   */
  private async doRequest(
    verb: string,
    args: any[],
    tokenWasRefresh = false
  ): Promise<AxiosResponse<any, any>> {
    const action = (this as any).api[verb];

    if (tokenWasRefresh) {
      return action.call(this.api, ...args);
    }

    try {
      const res = await action.call(this.api, ...args);

      return res;
    } catch (error: any) {
      await this.handlerErr(error);

      return this.doRequest(verb, args, true);
    }
  }

  private async handlerErr(error: AxiosError) {
    const isUnauthorized = error?.response?.status === 401;

    if (!isUnauthorized) {
      throw error;
    }

    await this.refreshToken();
  }

  private async refreshToken() {
    const res = await apiPublic.post(API_TOKEN_REFRESH, this.auth.refreshToken);

    const token: string = res.data;

    console.log("success refresh token");

    this.createInstance(token);
  }

  private createInstance(token: string) {
    this.auth.accessToken = token;

    storage.set(this.auth);

    return (this.api = createInstance({
      headers: {
        [Authorization]: PREFIX_TOKEN + token,
      },
    }));
  }
}

/**
 * Types
 */

export interface Auth {
  accessToken: string;
  refreshToken: string;
}
