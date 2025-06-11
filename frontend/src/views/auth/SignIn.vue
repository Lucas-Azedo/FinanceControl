<template>
    <div id="login" class="login-page">
        <div class="login-card">
            <h1>Sign in to your account in <span class="brand">FinanceControl</span></h1>

            <div v-if="errorMessages.length" class ="errors">
                <ul>
                    <li v-for="(err, i) in errorMessages" :key="i">{{ err }}</li>
                </ul>
            </div>

            <div class="form">
                <input v-model="email" placeholder="e-mail" type="email" autocomplete="name"/>
                <input v-model="password" placeholder="Password" type="password" />
                <button @click="signIn" class="primary">Log In </button>
                <button @click="signUp" class="secondary">Create Account</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useAuth } from '../../composables/useAuth'
import { useNavigation } from '../../composables/useNavigation'

const { setToken } = useAuth()
const { redirect } = useNavigation()


const email = ref('')
const password = ref('')

const errorMessages = ref([])

async function signIn() {
    try {
        const response = await fetch('http://localhost:8080/auth/signin', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                email: email.value,
                password: password.value
            }),
        })

        const data = await response.json();

        
        if (response.ok) {
            setToken(data.token)
            redirect('/dashboard')
            return
        }

        errorMessages.value = []
        const fieldErrors = data.fields && Object.values(data.fields)
        const arrayErrors = Array.isArray(data.errors) && data.errors
        const fallbackError = data.message || 'Erro desconhecido'

        errorMessages.value = fieldErrors || arrayErrors || [fallbackError]
		
    } catch (error) {
        console.error('Erro no signIn:', error)
    }
}

function signUp() {
    redirect('/signup')
}

</script>