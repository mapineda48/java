import React from "react";
import { usePortalBody } from "../Portals";
import * as bootstrap from "bootstrap";

export default function Alert() {
  const ref = React.useRef<HTMLDivElement>(null);

  React.useEffect(() => {
    if (!ref.current) return;

    const modal = new bootstrap.Modal(ref.current);

    modal.show();

    console.log(modal);
  });

  return (
    <div
      ref={ref}
      className="modal fade"
      id="exampleModal"
      tabIndex={-1}
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="exampleModalLabel">
              title
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            />
          </div>
          <div className="modal-body">message</div>
          <div className="modal-footer">
            <button
              autoFocus
              id="btn-action"
              type="button"
              className="btn btn-primary"
              data-bs-dismiss="modal"
            >
              Ok
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
