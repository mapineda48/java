/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import "./index.scss";

export default function Login() {
  return (
    <div className="sign-in">
      <form id="form-login" className="card">
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
            id="email"
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
            id="password"
          />
        </div>
        <div className="mb-3">
          <p>
            <strong>¿No tienes cuenta?</strong>{" "}
          </p>
          <p>
            <a href="#" id="create-account">
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
