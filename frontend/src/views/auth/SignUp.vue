<template>
  <FormErrors :errors="errorMessages" />
  <div id="login" class="login-page">

    <div class="login-card">

      <h1>Create your account in <span class="brand">FinanceControl</span></h1>

      <form class="form" @submit.prevent="signUp">
        <label> Nome </label>
        <input v-model="name" placeholder="Name" type="text" arequired />

        <label> E-mail </label>
        <input v-model="email" placeholder="E-mail" type="text" required />

        <label> Senha </label>
        <input v-model="password" placeholder="Password" type="password" required />

        <button class="primary">Sign Up</button>

        <button @click="signIn" class="secondary">Log in</button>
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
  
    }catch(error){
      console.error('Erro ao logar:', error)
      errorMessages.value = extractErrors(error)
    }
}
function signIn() {
  redirect('/signin')
}
</script>
