<template>
    <div id="login" class="all">
        <h1>SignIn</h1>

        <div v-if="errorMessages.length" class ="errors">
            <ul>
                <li v-for="(err, i) in errorMessages" :key="i">{{ err }}</li>
            </ul>
        </div>

        <div>
            <input v-model="email" placeholder="e-mail" type="email" />
            <input v-model="password" placeholder="Password" type="password" />
            <button @click="signIn">Logar</button>
            <button @click="signUp">Cadastrar</button>
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