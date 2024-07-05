import React from 'react';
import { Link } from 'react-router-dom';
import AuthService from '../services/authService';

const NavbarComponent = () => {
    const user = AuthService.getCurrentUser();


    const handleLogout = () => {
        AuthService.logout();
        window.location.reload();
    };

    return (
        <nav>
            <Link to="/">Home</Link>
            {user ? (
                <>
                    <Link to="/dashboard">Dashboard</Link>
                    <button onClick={handleLogout}>Logout</button>
                </>
            ) : (
                <Link to="/login">Login</Link>
            )}
        </nav>
    );
};

export default NavbarComponent;
