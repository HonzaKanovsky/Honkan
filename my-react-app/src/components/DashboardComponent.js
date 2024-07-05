import React, { useEffect, useState } from 'react';
import AuthService from '../services/authService';
import BalanceComponent from './BalanceComponent';

const DashboardComponent = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const currentUser = AuthService.getCurrentUser();
        if (currentUser) {
            setUser(currentUser);
        }
    }, []);

    return (
        <div className="dashboard-container">
            {user ? (
                <div>
                    <BalanceComponent user={user} /> {/* Pass user as a prop */}
                    <div>
                        <div>
                            <h2>Positions:</h2>
                            <ul>
                                {user.portfolio?.positions?.map(position => (
                                    <li key={position.cryptoID}>
                                        {position.symbol}: {position.amount}
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <h2>User History:</h2>
                        <ul>
                            {user.userHistory?.map(history => (
                                <li key={history.id}>
                                    {new Date(history.historizationDate).toLocaleString()}: {history.portfolioValue}
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            ) : (
                <div>Please log in to see your dashboard</div>
            )}
        </div>
    );
};

export default DashboardComponent;
