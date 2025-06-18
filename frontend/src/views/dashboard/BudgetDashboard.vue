<template>
    <DropdownMenu />

    <div class="dashboard">
      <BudgetComparison :transactions="transactions" />
    </div>
</template>

<script setup lang="ts">
import DropdownMenu from '../../components/common/DropdownMenu.vue'
import BudgetComparison from '../../components/budget/BudgetComparasion.vue' // Veja abaixo
import { ref, onMounted } from 'vue'

import { useApiFetch } from '../../composables/useApiFetch'
import { useExtractErrors } from '../../composables/useExtractErrors'

type Transaction = {
  id: string
  amount: number
  description: string
  date: string
  type: string
  category: string
}

const transactions = ref<Transaction[]>([])
const errorMessages = ref<string[]>([])

const { extractErrors } = useExtractErrors()

async function listTransactions(){
    try{
        const data = await useApiFetch('http://localhost:8080/transactions', {
            method: 'GET',
        })

        if (data) {
          console.log("Transações obtidas com sucesso")
          transactions.value = data // provavelmente esqueceu de setar aqui
        }

    }catch(error){
        console.error('Erro ao listar transações:', error)
        errorMessages.value = extractErrors(error)
    }
}

onMounted(() => {
  listTransactions()
})
</script>