<template>
	<div id="login" class="all">
		<h1>SignUp</h1>
		<input v-model="name" placeholder="Name" />
		<input v-model="email" placeholder="E-mail" />
		<input v-model="password" placeholder="Password" type="password" />
		<button @click="signUp">Cadastrar</button>
		<button @click="signIn()">Logar</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuth } from '../../composables/useAuth'
import { useNavigation } from '../../composables/useNavigation'

const { setToken } = useAuth()
const { redirect } = useNavigation()

const name = ref('')
const email = ref('')
const password = ref('')

async function signUp() {
	try {
		const response = await fetch('http://localhost:8080/auth/signup', {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({
				name: name.value,
				email: email.value,
				password: password.value
			})
		})

		const data = await response.json()

		if (!response.ok) {
			console.error('Erro do backend:', data)
			throw new Error(data.message || 'Erro ao cadastrar')
		}

		setToken(data.token)
		redirect('/dashboard')
	} catch (error) {
		console.error('Erro no signIn:', error)
	}
}

function signIn() {
	redirect('/signin')
}
</script>