/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import { Link, Navigate, Routes, Route, useNavigate } from "react-router-dom";
import Card from "./Card";
import { useApi, useLogout } from "../Session";
import { useAlert } from "../Alert";
import { usePromise } from "mp48-react/usePromise";
import style from "./index.module.scss";
import { useModalUser, useModalLaptop } from "../Modal";

export function Laptops() {
  const api = useApi();

  const [isLoading, error, res, fetchAll] = usePromise(
    false,
    api.laptop.fetchAll
  );

  if (isLoading) {
    return <Loading />;
  }

  if (error) {
    return <Message error={error} />;
  }

  if (!res || !res.length) {
    return <Message>Sin Resutlados</Message>;
  }

  return (
    <Cards>
      {res.map((laptop, key) => (
        <Card.Laptop record={laptop} key={key} onChange={fetchAll} />
      ))}
    </Cards>
  );
}

export function Users() {
  const api = useApi();

  const [isLoading, error, res, fetchAll] = usePromise(
    false,
    api.user.fetchAll
  );

  if (isLoading) {
    return <Loading />;
  }

  if (error) {
    return <Message error={error} />;
  }

  if (!res || !res.length) {
    return <Message>Sin Resutlados</Message>;
  }

  return (
    <Cards>
      {res.map((user, key) => (
        <Card.User record={user} key={key} onChange={fetchAll} />
      ))}
    </Cards>
  );
}

export function Cards(props: { children: React.ReactNode }) {
  return (
    <div className="album py-5 bg-light">
      <div className="container">
        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          {props.children}
        </div>
      </div>
    </div>
  );
}

export function Message(props: { children?: string; error?: any }) {
  if (props.error) {
    console.error(props.error);
  }

  return (
    <div className={style.message}>
      {props.children || props?.error?.message || "Unknown"}
    </div>
  );
}

export function Loading() {
  return (
    <section className="py-5 text-center container">
      <div className="row py-lg-5">
        <div className="col-lg-6 col-md-8 mx-auto">
          <div className="spinner-border" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      </div>
    </section>
  );
}

export function Welcome() {
  return (
    <div className={style.welcome}>
      <h1>Welcome</h1>
    </div>
  );
}

export function CreateRecord() {
  const navigate = useNavigate();
  const insertUser = useModalUser();
  const insertLaptop = useModalLaptop();
  const api = useApi();

  return (
    <button
      onClick={() => {
        const pathname = window.location.pathname;

        const refresh = () => {
          navigate({ pathname: "/empty" });
          navigate({ pathname });
        };

        if (pathname.endsWith("users")) {
          insertUser({
            onHide: refresh,
            api,
          });
        } else if (pathname.endsWith("laptops")) {
          insertLaptop({
            onHide: refresh,
            api,
          });
        }
      }}
      type="button"
      className={`btn btn-primary ${style.btnCreate}`}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width={16}
        height={16}
        fill="currentColor"
        className="bi bi-plus"
        viewBox="0 0 16 16"
      >
        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
      </svg>
      Crear
    </button>
  );
}

export default function Navigation() {
  const showAlert = useAlert();
  const logout = useLogout();

  return (
    <div>
      <header className="sticky-me">
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
          <div className="container-fluid">
            <a className="navbar-brand" href="#">
              Ocho
            </a>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon" />
            </button>
            <div className="collapse navbar-collapse">
              <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item dropdown">
                  <a
                    className="nav-link dropdown-toggle"
                    href="#"
                    role="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    Inicio
                  </a>
                  <ul
                    className="dropdown-menu"
                    aria-labelledby="navbarDropdown"
                  >
                    <li>
                      <Link to="welcome" className="dropdown-item">
                        Inicio
                      </Link>
                    </li>
                    <li>
                      <Link to="users" className="dropdown-item">
                        Personal
                      </Link>
                    </li>
                    <li>
                      <Link to="laptops" className="dropdown-item">
                        Producto
                      </Link>
                    </li>
                  </ul>
                </li>
                <li className="nav-item">
                  <form
                    className="d-flex"
                    onSubmit={(e) => {
                      e.preventDefault();
                      showAlert({
                        title: "Ups...",
                        message: "Esto aun no funciona",
                      });
                    }}
                  >
                    <input
                      className="form-control me-2"
                      type="search"
                      placeholder="ID"
                      aria-label="Search"
                      name="id"
                    />
                    <button className="btn btn-outline-success" type="submit">
                      Search
                    </button>
                  </form>
                </li>
              </ul>
              <button onClick={logout} className={style.logout} title="Salir">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width={20}
                  height={20}
                  fill="currentColor"
                  className="bi bi-box-arrow-right"
                  viewBox="0 0 16 16"
                >
                  <path
                    fillRule="evenodd"
                    d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"
                  />
                  <path
                    fillRule="evenodd"
                    d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"
                  />
                </svg>
              </button>
            </div>
          </div>
        </nav>
      </header>
      <main className={style.main}>
        <Routes>
          <Route path="/" element={<Navigate replace to="welcome" />} />
          <Route path="welcome" element={<Welcome />} />
          <Route path="users" element={<Users />} />
          <Route path="laptops" element={<Laptops />} />
        </Routes>
      </main>
      <Routes>
          <Route path="users" element={<CreateRecord />} />
          <Route path="laptops" element={<CreateRecord />} />
        </Routes>
    </div>
  );
}
