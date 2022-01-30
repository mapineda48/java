import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Portals from "./Portals";
import Home from "./Home";
import Session from "./Session";
import SignIn from "./SignIn";
import Dashboard from "./Dashboard";
import reportWebVitals from "./reportWebVitals";

import "./index.scss";

ReactDOM.render(
  <React.StrictMode>
    <Portals>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route
            path="/*"
            element={
              <Session>
                <Routes>
                  <Route path="login" element={<SignIn />} />
                  <Route path="dashboard/*" element={<Dashboard />} />
                </Routes>
              </Session>
            }
          />
        </Routes>
      </BrowserRouter>
    </Portals>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
