import React from "react";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import Welcome from "./Welcome";
import About from "./About/Lazy";
import "./index.scss";

export default function Home() {
  const navigate = useNavigate();

  React.useEffect(() => {
    document.title = "Bienvenido";
  }, []);

  return (
    <div className="home">
      <div className="text-center">
        <div className="cover-container d-flex h-100 p-3 mx-auto flex-column">
          <header className="masthead mb-auto">
            <div className="inner">
              <h3
                className="masthead-brand"
                onClick={() => {
                  navigate("/");
                }}
              >
                Ocho
              </h3>
              <nav className="nav nav-masthead justify-content-center">
                <Link className="nav-link" to="/shop">
                  Tienda
                </Link>
                <Link className="nav-link" to="/app/login">
                  App
                </Link>
                <Link className="nav-link" to="/about">
                  Acerca De
                </Link>
              </nav>
            </div>
          </header>

          <Routes>
            <Route path="/" element={<Welcome />} />
            <Route path="about" element={<About />} />
          </Routes>
        </div>
      </div>
    </div>
  );
}
