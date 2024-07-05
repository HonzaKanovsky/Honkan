import axios from 'axios';

const API_URL = 'http://localhost:8080/api/user/';

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL + 'login', { username, password })
            .then(response => {
                    localStorage.setItem('user', JSON.stringify(response.data));
                    console.log(response.data)
                    return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();
