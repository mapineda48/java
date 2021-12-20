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
      <Session>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<SignIn />} />
            <Route path="/dashboard/*" element={<Dashboard />} />
            <Route
              path="*"
              element={
                <div style={{ padding: "1rem" }}>
                  <p>There's nothing here!</p>
                </div>
              }
            />
          </Routes>
        </BrowserRouter>
      </Session>
    </Portals>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
