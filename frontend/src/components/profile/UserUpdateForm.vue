<template>
  <FormErrors :errors="errorMessages" />

  <form class="form" @submit.prevent="handleSubmit">
    <label>{{ label }}</label>
    <input v-model="input" :type="type" :placeholder="placeholder" required />

    <label>Senha atual</label>
    <input v-model="password" type="password" placeholder="Digite sua senha" required />

    <button type="submit">{{ buttonText }}</button>
    <button type="button" @click="$emit('close')">Cancelar</button>
  </form>
</template>

<script setup lang="ts">
import FormErrors from '../common/FormErrors.vue';

import { ref } from 'vue'
import { useExtractErrors } from '../../composables/useExtractErrors'

const props = defineProps<{
  label: string
  placeholder: string
  type?: string
  buttonText: string
  onUpdate: (value: string, password: string) => Promise<void>
}>()

const emit = defineEmits(['close'])

const input = ref('')
const password = ref('')
const errorMessages = ref<string[]>([])
const { extractErrors } = useExtractErrors()

async function handleSubmit() {
  errorMessages.value = []

  try {
    await props.onUpdate(input.value, password.value)
    input.value = ''
    password.value = ''
    emit('close')
  } catch (error) {
    errorMessages.value = extractErrors(error)
  }
}
</script>
