import ReactDOM from "react-dom";
import React from "react";
import { initAction } from "mp48-react/useState";

const init = () => null as any;

const Context = React.createContext<Push>(init);

/**
 * If you wonder why I just don't use ReactDOM.createPortal,
 * it's because this way I can nested zindex.
 */
export function usePortalBody() {
  const ref = React.useContext(Context);

  return ref;
}

export default function Portals(props: { children: React.ReactNode }) {
  const [state, setState] = React.useState<Push>(init);

  return (
    <Context.Provider value={state}>
      {props.children}
      <Elements refPush={setState} />
    </Context.Provider>
  );
}

const useState = initAction({
  push(state: State, Element: Component): State {
    if (!Element) return state;

    return [...state, Element];
  },
  splice(state: State, index: number): State {
    const list = [...state];

    list[index] = null;

    const isEmpty = list.every((el) => list.indexOf(el) === 0);

    if (isEmpty) return [];

    return list;
  },
});

function Elements(props: { refPush: (push: Push) => void }) {
  const [state, , portal] = useState([]);

  const { refPush } = props;

  React.useEffect(() => {
    refPush(() => portal.push);
  }, [portal, refPush]);

  const Elements = state.map((Element, index) => {
    if (!Element) return Element;

    return (
      <Element
        key={index}
        style={{ zIndex: 1500 + index * 100 }}
        remove={() => {
          portal.splice(index);
        }}
      />
    );
  });

  return ReactDOM.createPortal(Elements, document.body);
}

/**
 * Types
 */
export type Push = (Element: Component) => void;

export type Component = (props: Props) => JSX.Element;

type State = (Component | null)[];

interface Props {
  style: React.CSSProperties;
  remove: () => void;
}
