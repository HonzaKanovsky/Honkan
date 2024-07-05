import React from 'react';
import './App.css';
import LoginComponent from './components/LoginComponent';
import {Route,BrowserRouter as Router, Routes} from "react-router-dom";
import DashboardComponent from "./components/DashboardComponent";
import NavbarComponent from "./components/NavbarComponent";

function App() {
  return (
      <div className="app">
          <div className="App-header">
              <Router>
                  <Routes>
                      <Route path="/login" element={<LoginComponent />} />
                      <Route path="/dashboard" element={<DashboardComponent />} />
                      <Route path="*" element={<NotFound />} /> {/* Optional: Handle unknown routes */}
                  </Routes>
              </Router>
          </div>

      </div>

  );
}

const NotFound = () => {
    return <h2>404: Page Not Found</h2>;
};

export default App;
