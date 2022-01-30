import axios from "axios";

import type { AxiosRequestConfig } from "axios";

export function createInstance(config?: AxiosRequestConfig) {
  return axios.create({
    ...(config || {}),
    baseURL: process.env.PUBLIC_URL || "/",
  });
}
