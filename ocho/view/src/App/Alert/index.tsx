import React from "react";
import { usePortalBody } from "../Portals";
import useModal from "../useModal";

export default function Alert(props: Props) {
  const { error, message, onHide, onUnMount } = props;

  const ref = useModal(onHide, onUnMount);

  if (error) {
    console.error(error);
  }

  return (
    <div
      ref={ref}
      className="modal fade"
      
      tabIndex={-1}
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" >
              {props.title || "Alerta"}
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            />
          </div>
          <div className="modal-body">
            {error?.message || message || "Unknown"}
          </div>
          <div className="modal-footer">
            <button
              autoFocus
              
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

export function useAlert() {
  const append = usePortalBody();

  return React.useCallback(
    (opt: Props) => {
      append(({ remove }) => {
        return (
          <Alert
            {...opt}
            onHide={() => {
              if (opt.onHide) opt.onHide();
              remove();
            }}
          />
        );
      });
    },
    [append]
  );
}

/**
 * Types
 */
interface Props {
  error?: any;
  message?: string;
  title?: string;
  onHide?: () => void;
  onUnMount?: () => void;
}
