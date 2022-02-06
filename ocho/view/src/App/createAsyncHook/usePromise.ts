import { ensureAction } from "mp48-react/useState";
import React from "react";

const useState = ensureAction({
  loading(state: State, isLoading = true): State {
    return { ...state, isLoading, isFirstCall: false, res: null, error: null };
  },
  res(state: State, res: any): State {
    if (!state.isLoading) return state;

    return { ...state, res, isLoading: false };
  },
  error(state: State, error: any): State {
    if (!state.isLoading) return state;
    
    return { ...state, error, isLoading: false };
  },
});

/**
 * not wait call
 */
export function usePromise<
  P,
  V extends any[] = P extends (...args: infer A) => Promise<any> ? A : never[],
  R = P extends (...args: V) => Promise<infer R> ? R : never
>(
  waitCall: false,
  promise: P,
  ...args: P extends (...args: infer A) => Promise<R> ? A : never[]
): [boolean, any, R | null, (...args: V) => Promise<{ error: any; res?: R }>];
/**
 * wait call
 */
export function usePromise<
  P,
  V extends any[] = P extends (...args: infer A) => Promise<any> ? A : never[],
  R = P extends (...args: V) => Promise<infer R> ? R : never
>(
  waitCall: true,
  promise: P
): [boolean, any, R | null, (...args: V) => Promise<{ error: any; res?: R }>];
/**
 * default
 */
export function usePromise<
  P,
  V extends any[] = P extends (...args: infer A) => Promise<any> ? A : never[],
  R = P extends (...args: V) => Promise<infer R> ? R : never
>(
  promise: P,
  ...args: P extends (...args: infer A) => Promise<R> ? A : never[]
): [boolean, any, R | null];
export function usePromise(...vals: any[]): any {
  const [arg1, arg2, ...args] = vals;

  const isBoolArg1 = typeof arg1 === "boolean";

  const waitCall = isBoolArg1 && arg1;

  const promise = isBoolArg1 ? arg2 : arg1;

  const [{ res, error, isLoading, isFirstCall }, , hook] = useState({
    res: null,
    error: null,
    isLoading: false,
    isFirstCall: true,
  });

  const shouldCall = !res && !isLoading && !error && isFirstCall && !waitCall;

  React.useEffect(() => {
    if (!shouldCall) {
      return;
    }

    hook.loading();

    promise
      .call(null, ...args)
      .then((res: any) => hook.isMount() && hook.res(res))
      .catch((error: any) => hook.isMount() && hook.error(error));
  });

  const cb = React.useCallback(
    (...args: any[]) => {
      hook.loading();

      return new Promise<any>((res, rej) => {
        promise
          .call(null, ...args)
          .then((val: any) => {
            if (!hook.isMount()) {
              return;
            }

            hook.res(val);
            res({ error: null, res: val });
          })
          .catch((error: any) => {
            if (!hook.isMount()) {
              return;
            }

            hook.error(error);
            res({ error, res: null });
          });
      });
    },
    [hook, promise]
  );

  if (!isBoolArg1) {
    return [isLoading, error, res];
  }

  return [isLoading, error, res, cb];
}

/**
 * Types
 */
interface State {
  res: any;
  error: any;
  isLoading: boolean;
  isFirstCall: boolean;
}
