<template>
	<div id="login" class="all">
		<h1>SignUp</h1>

    <div v-if="errorMessages.length" class ="errors">
      <ul>
        <li v-for="(err, i) in errorMessages" :key="i">{{ err }}</li>
      </ul>
    </div>

    <div>
      <input v-model="name" placeholder="Name" />
      <input v-model="email" placeholder="E-mail" />
      <input v-model="password" placeholder="Password" type="password" />
      <button @click="signUp">Cadastrar</button>
      <button @click="signIn()">Logar</button>
    </div>
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

const errorMessages = ref([])

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
		errorMessages.value = ['Erro de rede ou servidor indisponível. Tente novamente mais tarde.']
	}
}

function signIn() {
	redirect('/signin')
}
</script>