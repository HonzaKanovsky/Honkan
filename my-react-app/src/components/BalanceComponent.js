import React, { useEffect, useState } from 'react';


const BalanceComponent = ({ user }) => {

    return (
        <div className="summary-container">
            <h2>Balance</h2>
            <div>Portfolio value: {user.portfolio?.portfolioValue}</div>
        </div>
    );
};

export default BalanceComponent;
