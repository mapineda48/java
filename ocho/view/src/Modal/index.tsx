import React from "react";
import { usePortalBody } from "../Portals";
import useModal from "../useModal";
import { useAlert } from "../Alert";
import { getDataForm, setDataForm, setLoading } from "../vanilla";
import api from "../api";
import { Modal } from "bootstrap";

import type { User, Laptop } from "../api";

export function MLaptop(props: PLaptop) {
  const ref = useModal(props.onHide);
  const showAlert = useAlert();

  React.useEffect(() => {
    if (!props.record || !ref.current) return;

    setDataForm(ref.current, props.record);
  }, [props.record, ref]);

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

                const laptop: Laptop.Laptop = getDataForm(form);

                const ready = setLoading(form);

                try {
                  let message = "Agregado";

                  if (!props.record) {
                    await api.laptop.insert(laptop);
                  } else {
                    await api.laptop.update({ ...props.record, ...laptop });
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
    (opt: PLaptop) => {
      append(({ remove }) => {
        return (
          <MLaptop
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

export function SignUp(props: Props) {
  const ref = useModal(props.onHide);
  const showAlert = useAlert();

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
            <h5 className="modal-title">Registrar Administrador</h5>
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

                const user: User.User = getDataForm(form);

                const ready = setLoading(form);

                try {
                  await api.user.insert(user);

                  showAlert({
                    title: "Administrador",
                    message: "Agregado Correctamente",
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

export function useSignUp() {
  const append = usePortalBody();

  return React.useCallback(
    (opt: Props) => {
      append(({ remove }) => {
        return (
          <SignUp
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

export function MUser(props: PUser) {
  const ref = useModal(props.onHide);
  const showAlert = useAlert();

  React.useEffect(() => {
    if (!props.record || !ref.current) return;

    setDataForm(ref.current, props.record);
  }, [props.record, ref]);

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

                const user: User.User = getDataForm(form);

                const ready = setLoading(form);

                try {
                  let message = "Agregado";

                  if (!props.record) {
                    await api.user.insert(user);
                  } else {
                    await api.user.update({ ...props.record, ...user });
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
    (opt: PUser) => {
      append(({ remove }) => {
        return (
          <MUser
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
  onHide?: () => void;
}

interface PLaptop {
  record?: Laptop.Record;
  onHide?: () => void;
}

interface PUser {
  record?: User.Record;
  onHide?: () => void;
}
