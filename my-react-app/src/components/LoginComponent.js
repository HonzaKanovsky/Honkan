import React, {useState} from 'react';
import AuthService from "../services/authService";
import {useNavigate} from "react-router-dom";

function LoginComponent() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();


    const doThis = async (e) =>{
        e.preventDefault()
        console.log(username)
        console.log(password)

        try{
            await AuthService.login(username, password);
            console.log("USER:" + AuthService.getCurrentUser())
            if(AuthService.getCurrentUser()["id"] == null){
                setMessage('User name or password is incorect')
                console.log("user bad")
            }else{
                setMessage('')
                navigate("/dashboard")
            }

        }catch (e){
            setMessage("GENERAL error")
        }

    };

    return (
        <div>
            <h1>Crypto tracker!</h1>
            <form onSubmit={doThis}>
                <div>
                    <label>Username</label>
                    <input
                        type="text"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                </div>
                <div>
                    <button disabled={loading}>
                        {loading && <span>Loading...</span>}
                        {!loading && <span>Login</span>}
                    </button>
                </div>
                {message && <div>{message}</div>}
            </form>
        </div>
    );
}

export default LoginComponent;
