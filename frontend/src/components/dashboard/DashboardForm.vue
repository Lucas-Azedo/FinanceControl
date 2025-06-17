<template>
    <FormErrors :errors="errorMessages" />
    <div class="card transaction-form">

      <h2>Nova Transação</h2>
  
      <form class="form" @submit.prevent="submitTransaction">
        <input v-model="amount" placeholder="Valor" type="number" />
        <input v-model="description" placeholder="Descrição" />
  
        <select v-model="transactionType" class="select">
            <option disabled value="">Selecione o tipo</option>
            <option value="INPUT">Entrada</option>
            <option value="OUTPUT">Saída</option>
        </select>

        <select v-model="category" class="select" :disabled="!transactionType">
            <option disabled value="">Selecione a categoria</option>
            <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
        </select>
        
        <button  class="primary">Enviar</button>
  
      </form>
    </div>
  </template>
<script setup lang="ts">
import FormErrors from '../common/FormErrors.vue'

import { ref, computed, watch } from 'vue'
import { TransactionCategories } from '../../enum'
import { useBalance  } from '../../composables/useBalance'
import { useApiFetch } from '../../composables/useApiFetch'
import { useExtractErrors } from '../../composables/useExtractErrors'

const amount = ref('')
const description = ref('')
const transactionType = ref('')
const category = ref('')

const { balance, setBalance } = useBalance()
const { extractErrors } = useExtractErrors()

const errorMessages = ref<string[]>([])

const emit = defineEmits(['transaction-added'])

async function submitTransaction() {
  try{
    const data = await useApiFetch('http://localhost:8080/transactions', {
      method: 'POST',
      body: JSON.stringify({
        amount: parseFloat(amount.value),
        description: description.value,
        type: transactionType.value,
        category: category.value
      }),
    })

    if (data) {
      errorMessages.value = []
      console.log("Transação efetuada com sucesso!")
      const numericAmount = parseFloat(amount.value)
      const delta = transactionType.value === 'INPUT' ? numericAmount : -numericAmount
      setBalance(data.newBalance || balance.value + delta)
      clearForm()
      emit('transaction-added')
      return
    }

  }catch(error){
    console.error('Erro ao adicionar transação:', error)
    errorMessages.value = extractErrors(error)
  }
}
function clearForm() {
  amount.value = ''
  description.value = ''
  transactionType.value = ''
  category.value = ''
}

watch(transactionType, () => {
  category.value = ''
})

const categories = computed(() => {
  if (!transactionType.value) return []
  return TransactionCategories[transactionType.value as keyof typeof TransactionCategories]
})
</script>