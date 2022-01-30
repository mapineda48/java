import React from "react";
import createAsyncHook from "../createAsyncHook";
import { useAlert } from "../Alert";
import { useModalLaptop, useModalUser } from "../Modal";
import { useApi } from "../Session";

import type { User as TUser, Laptop as TLaptop } from "../api";
import { usePromise } from "../createAsyncHook/usePromise";

export function Laptop(props: {
  record: TLaptop.Record;
  onChange?: () => void;
}) {
  const api = useApi();
  const [loading, , , remove] = usePromise(true, api.laptop.remove);

  const alert = useAlert();
  const updateLatop = useModalLaptop();

  const laptop = props.record;

  return (
    <div className="col">
      <div className="card shadow-sm">
        <div className="card-body">
          <p className="card-text">
            <strong>Marca: </strong>
            {laptop.brand}
          </p>
          <p className="card-text">
            <strong>Modelo: </strong>
            {laptop.model}
          </p>
          <p className="card-text">
            <strong>Procesador: </strong>
            {laptop.procesor}
          </p>
          <p className="card-text">
            <strong>OS: </strong>
            {laptop.os}
          </p>
          <p className="card-text">
            <strong>Descripción: </strong>
            {laptop.description}
          </p>
          <p className="card-text">
            <strong>Memoria: </strong>
            {laptop.memory}
          </p>
          <p className="card-text">
            <strong>Disco Duro: </strong>
            {laptop.hardDrive}
          </p>
          <p className="card-text">
            <strong>Disponible: </strong>
            {laptop.availability ? "Si" : "No"}
          </p>
          <p className="card-text">
            <strong>Precio: </strong>
            {laptop.price}
          </p>
          <p className="card-text">
            <strong>Cantidad: </strong>
            {laptop.quantity}
          </p>
          <p className="card-text">
            <strong>Foto: </strong>
            {laptop.photography}
          </p>
          <div className="d-flex justify-content-between align-items-center">
            <div className="btn-group">
              <button
                type="button"
                className="btn btn-sm btn-outline-secondary"
                disabled={loading}
                onClick={(e) => {
                  remove(laptop.id).then(({ error }) => {
                    if (error) {
                      alert({ error });
                      return;
                    }

                    alert({
                      message: laptop.model,
                      title: "Eliminado",
                      onHide: props.onChange,
                    });
                  });
                }}
              >
                Eliminar
              </button>
              <button
                type="button"
                className="btn btn-sm btn-outline-secondary"
                onClick={() => {
                  updateLatop({ record: props.record, onHide: props.onChange });
                }}
              >
                Editar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export function User(props: { record: TUser.Record; onChange?: () => void }) {
  const api = useApi();
  const [loading, , , remove] = usePromise(true, api.user.remove);

  const alert = useAlert();
  const updateUser = useModalUser();

  const user = props.record;

  return (
    <div className="col">
      <div className="card shadow-sm">
        <div className="card-body">
          <p className="card-text">
            <strong>Identificacion: </strong>
            {user.identification}
          </p>
          <p className="card-text">
            <strong>Nombre: </strong>
            {user.name}
          </p>
          <p className="card-text">
            <strong>Email: </strong>
            {user.email}
          </p>
          <p className="card-text">
            <strong>Celular: </strong>
            {user.cellPhone}
          </p>
          <p className="card-text">
            <strong>Direccion: </strong>
            {user.address}
          </p>
          <p className="card-text">
            <strong>Zona: </strong>
            {user.zone}
          </p>
          <p className="card-text">
            <strong>Tipo: </strong>
            {user.type}
          </p>

          <div className="d-flex justify-content-between align-items-center">
            <div className="btn-group">
              <button
                type="button"
                className="btn btn-sm btn-outline-secondary"
                disabled={loading}
                onClick={(e) => {
                  console.log({ record: props.record });
                  remove(props.record.id).then(({ error }) => {
                    if (error) {
                      alert({ error });
                      return;
                    }

                    alert({
                      message: user.name,
                      title: "Eliminado",
                      onHide: props.onChange,
                    });
                  });
                }}
              >
                Eliminar
              </button>
              <button
                type="button"
                className="btn btn-sm btn-outline-secondary"
                onClick={() => {
                  updateUser({ record: props.record, onHide: props.onChange });
                }}
              >
                Editar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

const Card = { User, Laptop };

export default Card;
