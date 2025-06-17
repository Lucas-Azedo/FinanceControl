<template>
  <FormErrors :errors="errorMessages" />
  <div id="login" class="login-page">
    <div class="login-card">
      <h1>Sign in to your account in <span class="brand">FinanceControl</span></h1>

      <form class="form">
        <label> E-mail </label>
        <input v-model="email" placeholder="e-mail" type="email" required />
        <label> Senha </label>
        <input v-model="password" placeholder="Password" required />
        <button @click="signIn" class="primary">Log In</button>
        <button @click="signUp" class="secondary">Create Account</button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import FormErrors from '../../components/common/FormErrors.vue'

import { ref } from 'vue'
import { useAuth } from '../../composables/useAuth'
import { useNavigation } from '../../composables/useNavigation'
import { useApiFetch } from '../../composables/useApiFetch'
import { useExtractErrors } from '../../composables/useExtractErrors'

const email = ref('')
const password = ref('')
const errorMessages = ref<string[]>([])

const { setToken } = useAuth()
const { redirect } = useNavigation()
const { extractErrors } = useExtractErrors()

async function signIn() {
  errorMessages.value = []
  try {
    const data = await useApiFetch<{ token: string }>('http://localhost:8080/auth/signin', {
        method: 'POST',
        body: JSON.stringify({
        email: email.value,
        password: password.value
    }),
    skipAuthInterceptor: true
    })


    if (data && 'token' in data) {
      setToken(data.token)
      redirect('/dashboard')
    }
    }catch(error){
      console.error('Erro ao logar:', error)
      errorMessages.value = extractErrors(error)
    }
}

function signUp() {
  redirect('/signup')
}
</script>
