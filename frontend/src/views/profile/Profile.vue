<template>
  <DropdownMenu />
  <div class="profile">
    <UserInfo />

    <div class="actions">
      <button @click="showForm = 'name'">Atualizar Nome</button>
      <button @click="showForm = 'email'">Atualizar Email</button>
      <button @click="showForm = 'password'">Atualizar Senha</button>
    </div>

    <UserUpdateForm
      v-if="showForm === 'name'"
      label="Novo nome"
      placeholder="Digite o novo nome"
      type="text"
      buttonText="Atualizar Nome"
      :onUpdate="(value, password) => updateField('/me/name', value, password)"
      @close="showForm = ''"
    />

    <UserUpdateForm
      v-if="showForm === 'email'"
      label="Novo email"
      placeholder="Digite o novo email"
      type="email"
      buttonText="Atualizar Email"
      :onUpdate="(value, password) => updateField('/me/email', value, password)"
      @close="showForm = ''"
    />

    <UserUpdateForm
      v-if="showForm === 'password'"
      label="Nova senha"
      placeholder="Digite a nova senha"
      type="password"
      buttonText="Atualizar Senha"
      :onUpdate="(value, password) => updateField('/me/password', value, password)"
      @close="showForm = ''"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import DropdownMenu from '../../components/common/DropdownMenu.vue'
import UserInfo from '../../components/profile/UserInfo.vue'
import UserUpdateForm from '../../components/profile/UserUpdateForm.vue'
import { useApiFetch } from '../../composables/useApiFetch'

const showForm = ref<'name' | 'email' | 'password' | ''>('')

async function updateField(endpoint: string, value: string, password: string) {
    var body: Record<string, string> = { password }

    if (endpoint === '/me/name') {
        body.name = value
    } 

    if (endpoint === '/me/email') {
        body.email = value
    }  

    if (endpoint === '/me/password') {
        body.newPassword = value
    }

  const res = await useApiFetch(`http://localhost:8080${endpoint}`, {
    method: 'PUT',
    body: JSON.stringify( body ),
    headers: { 'Content-Type': 'application/json' }
  })
  return res
}
</script>
