import React from "react";
import { initAction } from "mp48-react/useState";

const useState = initAction({
  res(state: State, val: any): State {
    return { ...state, loading: false, isFirst: false, res: val };
  },
  error(state: State, error: any): State {
    return { ...state, loading: false, isFirst: false, error };
  },
  loading(state: State): State {
    return { ...state, loading: true };
  },
});

export default function createHook<T, W extends boolean = false>(
  task: T,
  waitCall?: W
): T extends (...args: infer A) => Promise<infer R>
  ? W extends true
    ? () => [boolean, any, R | null, (...args: A) => Promise<void>]
    : (...args: A) => [boolean, any, R | null, (...args: A) => void]
  : never;
export default function createHook(thunk: any, waitCall = false): any {
  let mount = false;

  return (...args: any[]) => {
    const [{ res, loading, isFirst, error }, setState, action] = useState({
      loading: false,
      isFirst: true,
      res: null,
      error: null,
    });

    const autoCall = !waitCall && isFirst && !loading && args;

    React.useEffect(() => {
      if (!autoCall) return;

      action.loading();
      thunk
        .call(autoCall)
        .then((res: any) => mount && action.res(res))
        .catch((err: any) => mount && action.error(err));
    }, [action, autoCall]);

    const cb = React.useMemo(() => {
      if (!waitCall) {
        return (...args: any[]) => {
          setState((state) => {
            if (state.loading) {
              return state;
            }

            thunk
              .call(null, args)
              .then((data: any) => mount && action.res(data))
              .catch((err: any) => mount && action.error(err));

            return { ...state, loading: true };
          });
        };
      }

      return async (...args: any[]) => {
        return new Promise<void>((res, rej) => {
          setState((state) => {
            if (state.loading) {
              rej(new Error("wait to finish promise"));
              return state;
            }

            thunk
              .call(null, ...args)
              .then((data: any) => {
                if (!mount) return;

                action.res(data);
                res();
              })
              .catch((err: any) => {
                if (!mount) return;

                action.error(err);
                rej(err);
              });

            return { ...state, loading: true };
          });
        });
      };
    }, [action, setState]);

    React.useEffect(() => {
      mount = true;
      return () => {
        mount = false;
      };
    }, []);

    return [loading, error, res, cb];
  };
}

/**
 * Types
 */
interface State {
  isFirst: boolean;
  res: any;
  error: any;
  loading: boolean;
}
