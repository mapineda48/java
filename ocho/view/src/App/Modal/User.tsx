import React from "react";
import { usePortalBody } from "../Portals";
import useModal from "../useModal";
import { useAlert } from "../Alert";
import { getDataForm, setDataForm, setLoading } from "../vanilla";
import { Modal } from "bootstrap";
import type { Api, User as Type } from "../api";

export function User({ api, onHide, record }: Props) {
  const ref = useModal(onHide);
  const showAlert = useAlert();

  React.useEffect(() => {
    if (!record || !ref.current) return;

    setDataForm(ref.current, record);
  }, [record, ref]);

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
            <h5 className="modal-title">Usuario</h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            />
          </div>
          <div className="modal-body">
            <form
              onSubmit={async (e) => {
                e.preventDefault();

                const form = e.currentTarget;

                const modal = Modal.getInstance(ref.current as any) as Modal;

                const user: Type.User = getDataForm(form);

                const ready = setLoading(form);

                try {
                  let message = "Agregado";

                  if (!record) {
                    await api.user.insert(user);
                  } else {
                    await api.user.update({ ...record, ...user });
                    message = "Actualizado";
                  }

                  showAlert({
                    title: "Usuario",
                    message,
                    onHide() {
                      modal.hide();
                    },
                  });
                } catch (error: any) {
                  showAlert({
                    error,
                  });
                } finally {
                  ready();
                }
              }}
            >
              <div className="mb-3">
                <label htmlFor="email" className="form-label">
                  Correo
                </label>
                <input
                  maxLength={80}
                  type="email"
                  name="email"
                  required
                  className="form-control"
                  aria-describedby="emailHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Contraseña
                </label>
                <input
                  maxLength={50}
                  type="password"
                  required
                  className="form-control"
                  name="password"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="name" className="form-label">
                  Nombre
                </label>
                <input
                  maxLength={50}
                  type="text"
                  required
                  className="form-control"
                  name="name"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="identification" className="form-label">
                  Identificacion
                </label>
                <input
                  maxLength={50}
                  type="text"
                  required
                  className="form-control"
                  name="identification"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="address" className="form-label">
                  Direccion
                </label>
                <input
                  maxLength={50}
                  type="text"
                  required
                  className="form-control"
                  name="address"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="cellPhone" className="form-label">
                  Celular
                </label>
                <input
                  maxLength={50}
                  type="text"
                  required
                  className="form-control"
                  name="cellPhone"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="zone" className="form-label">
                  Zona
                </label>
                <input
                  maxLength={50}
                  type="text"
                  required
                  className="form-control"
                  name="zone"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="type" className="form-label">
                  Tipo
                </label>
                <select
                  className="form-select"
                  aria-label="Default select example"
                  name="type"
                  required
                >
                  <option value="COORD">Coordinador de Zona</option>
                  <option value="ASES">Asesor Comercial</option>
                  <option value="ADM">Administrador</option>
                </select>
              </div>
              <button type="submit" className="btn btn-primary">
                Completar
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export function useModalUser() {
  const append = usePortalBody();

  return React.useCallback(
    (props: Props) => {
      append(({ remove }) => {
        return (
          <User
            {...props}
            onHide={() => {
              if (props.onHide) props.onHide();
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
  record?: Type.Record;
  onHide?: () => void;
  api: Api;
}
