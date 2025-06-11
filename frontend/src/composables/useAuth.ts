import { ref } from 'vue';
import { useNavigation } from '../composables/useNavigation'


const token = ref(localStorage.getItem('token'))
const { redirect } = useNavigation()

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
        redirect('/signin')
    }

    function isAuthenticated() {
        return !!token.value
    }

    return { token, setToken, getToken, logout, isAuthenticated }
}