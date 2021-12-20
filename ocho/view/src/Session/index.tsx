import React from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

import type { User } from "../api";

const init: Session = {
  user: null,
  logout() {},
  signin() {
    return Promise.reject(new Error("unknown error"));
  },
};

const Context = React.createContext<Session>({ ...init });

export function useSession() {
  return React.useContext(Context);
}

export function SessionContext(props: Props) {
  const [state, setState] = React.useState<Session>({ ...init });

  React.useMemo(() => {
    setState({
      ...init,
      logout() {
        setState((state) => ({ ...state, user: null }));
      },
      async signin(user, password) {
        const record = await api.signIn(user, password);

        if(!record.id){
          throw new Error("Invalid username y/o password");
        }

        setState((state) => ({ ...state, user: record }));
      },
    });
  }, [setState]);

  return <Context.Provider value={state}>{props.children}</Context.Provider>;
}

export default SessionContext;

/**
 * Types
 */
interface Session {
  user: User.Record | null;
  logout: () => void;
  signin: (user: string, password: string) => Promise<void>;
}

export interface Props {
  children: React.ReactNode;
}
