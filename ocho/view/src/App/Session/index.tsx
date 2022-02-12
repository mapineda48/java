import React from "react";
import { initAction } from "mp48-react/useState";
import { useLocation, useNavigate } from "react-router-dom";
import Loading from "../../Loading";
import { useAlert } from "../Alert";
import { validateSession, signin, logout } from "../api";
import type { Api } from "../api";

const useState = initAction({
  api(state: State, api: Api | null): State {
    return { ...state, api };
  },
  loading(state: State, isLoading = true): State {
    return { ...state, isLoading, complete: !isLoading };
  },
  init(state: State): State {
    return init();
  },
});

/**
 * Context
 */
const Context = {
  Api: React.createContext<Api | null>(null),
  SignIn: React.createContext<SignIn>(() => Promise.resolve()),
  LogOut: React.createContext<() => void>(() => {}),
};

export function useSignIn() {
  return React.useContext(Context.SignIn);
}

export function useLogout() {
  return React.useContext(Context.LogOut);
}

export function useApi() {
  const api: any = React.useContext(Context.Api);

  return api as Api;
}

const PATH_LOGIN = "/app/login";
const PATH_DASHBOARD = "/app/dashboard/";
/**
 * Functional Component
 */
export function SessionContext(props: Props) {
  const [state, , session] = useState(init);

  const showAlert = useAlert();

  const navigate = useNavigate();

  const location = useLocation();

  const inLogin = location.pathname === PATH_LOGIN;

  const skipValidateSession = state.isLoading || state.complete;

  React.useEffect(() => {
    if (skipValidateSession) {
      return;
    }

    session.loading();

    validateSession()
      .then((api) => {
        if (!api) {
          if (!inLogin) {
            navigate(PATH_LOGIN);
          }
          return;
        }

        session.api(api);

        if (inLogin) {
          navigate(PATH_DASHBOARD);
        }
      })
      .catch((error) => {
        showAlert({
          error,
          onUnMount() {
            if (!inLogin) {
              navigate(PATH_LOGIN);
            }
          },
        });
      })
      .finally(() => session.loading(false));
  }, [
    inLogin,
    navigate,
    session,
    showAlert,
    state.isLoading,
    skipValidateSession,
  ]);

  if (!state.complete) {
    return <Loading />;
  }

  if (inLogin) {
    return (
      <Context.SignIn.Provider
        value={async (email, password) => {
          return signin(email, password).then((api) => {
            session.api(api);
            navigate(PATH_DASHBOARD);
          });
        }}
      >
        {props.children}
      </Context.SignIn.Provider>
    );
  }

  if (!state.api) {
    return null;
  }

  return (
    <Context.Api.Provider value={state.api}>
      <Context.LogOut.Provider
        value={() => {
          logout();
          session.init();
          navigate(PATH_LOGIN);
        }}
      >
        {props.children}
      </Context.LogOut.Provider>
    </Context.Api.Provider>
  );
}

function init(): State {
  return {
    complete: false,
    isLoading: false,
    api: null,
  };
}

export default SessionContext;

/**
 * Types
 */

export interface Props {
  children: React.ReactNode;
}

interface State {
  complete: boolean;
  isLoading: boolean;
  api: Api | null;
}

type SignIn = (email: string, password: string) => Promise<void>;
