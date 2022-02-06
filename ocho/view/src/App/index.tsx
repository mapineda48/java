import React from "react";
import { Routes, Route } from "react-router-dom";
import Portals from "./Portals";
import Session from "./Session";
import SignIn from "./SignIn";
import Dashboard from "./Dashboard";

export default function App() {
  React.useEffect(() => {
    document.title = "App";
  }, []);

  return (
    <Portals>
      <Session>
        <Routes>
          <Route path="login" element={<SignIn />} />
          <Route path="dashboard/*" element={<Dashboard />} />
        </Routes>
      </Session>
    </Portals>
  );
}
