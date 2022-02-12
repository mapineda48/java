import React from "react";
import { usePortalBody } from "../Portals";
import useModal from "../useModal";
import { useAlert } from "../Alert";
import { getDataForm, setDataForm, setLoading } from "../vanilla";
import { Modal } from "bootstrap";
import type { Api, Laptop as Type } from "../api";

export function Laptop({ api, record, onHide }: Props) {
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

                const laptop: Type.Laptop = getDataForm(form);

                const ready = setLoading(form);

                try {
                  let message = "Agregado";

                  if (!record) {
                    await api.laptop.insert(laptop);
                  } else {
                    await api.laptop.update({ ...record, ...laptop });
                    message = "Actualizado";
                  }

                  showAlert({
                    title: "Laptop",
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
                <label htmlFor="brand" className="form-label">
                  Marca
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="brand"
                  required
                  className="form-control"
                  aria-describedby="brandHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="model" className="form-label">
                  Model
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="model"
                  required
                  className="form-control"
                  aria-describedby="modelHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="procesor" className="form-label">
                  Procesador
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="procesor"
                  required
                  className="form-control"
                  aria-describedby="procesorHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="os" className="form-label">
                  OS
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="os"
                  required
                  className="form-control"
                  aria-describedby="osHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="description" className="form-label">
                  Descripción
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="description"
                  required
                  className="form-control"
                  aria-describedby="descriptionHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="memory" className="form-label">
                  Memoria
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="memory"
                  required
                  className="form-control"
                  aria-describedby="memoryHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="hardDrive" className="form-label">
                  Disco Duro
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="hardDrive"
                  required
                  className="form-control"
                  aria-describedby="hardDriveHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="availability" className="form-label">
                  Disponible
                </label>
                <select
                  className="form-select"
                  aria-label="Default select example"
                  name="availability"
                  datatype="boolean"
                  required
                >
                  <option value="true">Si</option>
                  <option value="false">No</option>
                </select>
              </div>
              <div className="mb-3">
                <label htmlFor="price" className="form-label">
                  Precio
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="price"
                  required
                  className="form-control"
                  aria-describedby="priceHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="quantity" className="form-label">
                  Cantidad
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="quantity"
                  required
                  className="form-control"
                  aria-describedby="quantityHelp"
                />
              </div>
              <div className="mb-3">
                <label htmlFor="photography" className="form-label">
                  Foto
                </label>
                <input
                  maxLength={80}
                  type="text"
                  name="photography"
                  required
                  className="form-control"
                  aria-describedby="photographyHelp"
                />
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

export function useModalLaptop() {
  const append = usePortalBody();

  return React.useCallback(
    (props: Props) => {
      append(({ remove }) => {
        return (
          <Laptop
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
  api: Api;
  record?: Type.Record;
  onHide?: () => void;
}
