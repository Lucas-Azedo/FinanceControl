<template>
  <div id="login" class="login-page">
    <div class="login-card">
      <h1>Sign in to your account in <span class="brand">FinanceControl</span></h1>

      <ul v-if="errorMessages.length" class="errors">
        <li v-for="(err, i) in errorMessages" :key="i">{{ err }}</li>
      </ul>

      <div class="form">
        <input v-model="email" placeholder="e-mail" type="email" autocomplete="email" />
        <input v-model="password" placeholder="Password" type="password" />
        <button @click="signIn" class="primary">Log In</button>
        <button @click="signUp" class="secondary">Create Account</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
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
    errorMessages.value = []
  } catch (err) {
    errorMessages.value = extractErrors(err)
  }
}

function signUp() {
  redirect('/signup')
}
</script>
