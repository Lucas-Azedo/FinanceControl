<template>
  <div id="login" class="login-page">
    <div class="login-card">
      <h1>Create your account in <span class="brand">FinanceControl</span></h1>

      <ul v-if="errorMessages.length" class="errors">
        <li v-for="(err, i) in errorMessages" :key="i">{{ err }}</li>
      </ul>

      <div class="form">
        <input v-model="name" placeholder="Name" type="text" autocomplete="name" />
        <input v-model="email" placeholder="E-mail" type="text" autocomplete="email" />
        <input v-model="password" placeholder="Password" type="password" autocomplete="new-password" />
        <button @click="signUp" class="primary">Sign Up</button>
        <button @click="signIn" class="secondary">Log in</button>
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

const name = ref('')
const email = ref('')
const password = ref('')
const errorMessages = ref<string[]>([])

const { setToken } = useAuth()
const { redirect } = useNavigation()
const { extractErrors } = useExtractErrors()

async function signUp() {
  errorMessages.value = []

  try {
    const data = await useApiFetch<{ token: string } | null>('http://localhost:8080/auth/signup', {
      method: 'POST',
      body: JSON.stringify({
        name: name.value,
        email: email.value,
        password: password.value
      })
    })

    if (data && 'token' in data) {
      setToken(data.token)
      redirect('/dashboard')
    }

  } catch (err) {
    errorMessages.value = extractErrors(err)
  }
}

function signIn() {
  redirect('/signin')
}
</script>
