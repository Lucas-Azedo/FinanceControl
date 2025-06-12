<template>
    <div class="card transaction-form">
      <h2>Nova Transação</h2>
  
      <div class="form">
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
        
        <button class="primary" @click="submitTransaction">Enviar</button>
  
        <ul v-if="errorMessages.length" class="errors">
          <li v-for="(msg, i) in errorMessages" :key="i">{{ msg }}</li>
        </ul>
      </div>
    </div>
  </template>
<script setup lang="ts">
import { ref, computed } from 'vue';
import { TransactionCategories } from '../../enum'

const amount = ref('')
const description = ref('')
const transactionType = ref('')
const category = ref('')

const token = localStorage.getItem('token')

const emit = defineEmits(['transaction-added'])
const errorMessages = ref([])

async function submitTransaction() {
    try{
        const response = await fetch('http://localhost:8080/transactions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', 
                'Authorization': `Bearer ${token}` },
            body: JSON.stringify({
                amount: parseFloat(amount.value),
                description: description.value,
                type: transactionType.value,
                category: category.value
            }),
        })

        const data = await response.json();

        if(response.ok){
            console.log("Transação efetuada com sucesso!")
            clearForm()
            emit('transaction-added')
            return
        }

        errorMessages.value = []
        const fieldErrors = data.fields && Object.values(data.fields)
        const arrayErrors = Array.isArray(data.errors) && data.errors
        const fallbackError = data.message || 'Erro desconhecido'

        errorMessages.value = fieldErrors || arrayErrors || [fallbackError]

    }catch(error){
        console.error('Erro no signIn:', error)
    }
}

function clearForm() {
  amount.value = ''
  description.value = ''
  transactionType.value = ''
  category.value = ''
}

const categories = computed(() => {
  if (!transactionType.value) return []
  return TransactionCategories[transactionType.value as keyof typeof TransactionCategories]
})
</script>