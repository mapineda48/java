import React from "react";
import * as bootstrap from "bootstrap";

/**
 * https://getbootstrap.com/docs/5.1/components/modal/#events
 */
const event = {
  endShow: "shown.bs.modal",
  endHide: "hidden.bs.modal",
};

export default function useModal(onHide?: () => void, onUnmount?: () => void) {
  const ref = React.useRef<HTMLDivElement>(null);

  const [enabled, setEnabled] = React.useState(true);

  React.useEffect(() => {
    if (!ref.current) return;

    if (!enabled) {
      if (onUnmount) return onUnmount;
      return;
    }

    const el = ref.current;

    el.addEventListener(event.endHide, function onEndHide() {
      el.removeEventListener(event.endHide, onEndHide);

      const modal = bootstrap.Modal.getInstance(el);

      if (!modal) return;

      modal.dispose();

      if (onHide) {
        onHide();
      }
    });

    el.addEventListener(event.endShow, function onShowEnd() {
      el.removeEventListener(event.endShow, onShowEnd);

      setEnabled(false);
    });

    new bootstrap.Modal(el).show();
  }, [enabled, onHide, onUnmount]);

  return ref;
}
