import { ref } from 'vue';

const token = ref(localStorage.getItem('token'))

export function useAuth() {

    function setToken(newToken: string) {
        token.value = newToken
        localStorage.setItem('token', newToken)
    }

    function getToken() {
        return token.value
    }

    function logout() {
        token.value = null
        localStorage.removeItem('token')
    }

    function isAuthenticated() {
        return !!token.value
    }

    return { token, setToken, getToken, logout, isAuthenticated }
}