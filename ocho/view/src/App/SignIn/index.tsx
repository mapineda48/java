/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import { useAlert } from "../Alert";
//import { useSignUp } from "../Modal";
import { useSignIn } from "../Session";
import { getDataForm, setLoading } from "../vanilla";
import "./index.scss";

export default function Login() {
  const signin = useSignIn();
  const showAlert = useAlert();
  //const signup = useSignUp();

  return (
    <div className="sign-in">
      <form
        className="card"
        onSubmit={async (e) => {
          e.preventDefault();

          const form = e.currentTarget;

          const { email, password } = getDataForm(form);

          const ready = setLoading(form);

          try {
            await signin(email, password);
          } catch (error: any) {
            showAlert({
              error,
            });
            ready();
          }
        }}
      >
        <div className="mb-3">
          <h3>Ocho</h3>
        </div>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">
            Correo
          </label>
          <input
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
            type="password"
            required
            className="form-control"
            name="password"
          />
        </div>
        <div className="mb-3">
          <p>
            <strong>¿No tienes cuenta?</strong>{" "}
          </p>
          <p>
            <a
              onClick={() => {
                showAlert({
                  title: "Profe",
                  message:
                    "Esto no estaba en los requerimientos, pero lo hago para facilitar el testeo.",
                  onUnMount() {
                    //signup({});
                  },
                });
              }}
              href="#"
            >
              Crea tu cuenta aquí
            </a>
          </p>
        </div>
        <button type="submit" className="btn btn-primary">
          Ingresar
        </button>
      </form>
    </div>
  );
}
