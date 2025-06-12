<template>
    <div class="transaction-table">
        <h2>Transações Recentes</h2>
        <table v-if="transactions.length">
        <thead>
            <div class="saldo">
                <h3>Saldo Atual: <span :class="balance >= 0 ? 'success' : 'errors'">
                R$ {{ balance.toFixed(2) }}
                </span></h3>
            </div>
        <tr>
            <th>Data</th>
            <th>Tipo</th>
            <th>Valor</th>
            <th>Categoria</th>
            <th>Descrição</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="tx in transactions" :key="tx.id">    
            <td>{{ formatDate(tx.date) }}</td>
            <td :class="tx.type === 'INPUT' ? 'success' : 'errors'"> {{ tx.type }} </td>
            <td :class="tx.type === 'INPUT' ? 'success' : 'errors'"> {{ tx.type === 'INPUT' ? '+' : '-' }}R$ {{ tx.amount.toFixed(2) }}</td>
            <td>{{ tx.category }}</td>
            <td>{{ tx.description }}</td>
        </tr>
    </tbody>
    </table>

    <p v-else>Nenhuma transação encontrada.</p>

    <ul v-if="errorMessages.length" class="errors">
      <li v-for="(msg, i) in errorMessages" :key="i">{{ msg }}</li>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';

type Transaction = {
    id: string
    amount: number
    description: string
    date: string
    type: string
    category: string
}

const token = localStorage.getItem('token')

const transactions = ref<Transaction[]>([])
const errorMessages = ref([])

async function listTransactions(){
    try{
        const response = await fetch('http://localhost:8080/transactions', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json', 
                'Authorization': `Bearer ${token}` },
        })

        const data = await response.json();

        if(response.ok){
            console.log("Transação obtida com sucesso")
            transactions.value = data 
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

function formatDate(dateString: string): string {
  const date = new Date(dateString)
  return date.toLocaleDateString('pt-BR') + ' ' + date.toLocaleTimeString('pt-BR')
}

const balance = computed(() => {
  return transactions.value.reduce((total, tx) => {
    return tx.type === 'INPUT'
      ? total + tx.amount
      : total - tx.amount
  }, 0)
})

defineExpose({
  listTransactions
})

onMounted(() => {
  listTransactions()
})

</script>