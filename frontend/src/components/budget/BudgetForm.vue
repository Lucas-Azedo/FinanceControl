<template>
  <FormErrors :errors="errorMessages" />
  <div class="card budget-form">
    <h2>Planejamento de Gastos</h2>
    <form class="form" @submit.prevent="handleSubmit">
        <select v-model="category" class="select">
            <option disabled value="">Selecione a categoria</option>
            <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
        </select>

      <input
        v-model.number="plannedAmount"
        type="number"
        placeholder="Valor planejado"
        class="input"
      />

      <button class="primary">Salvar</button>
    </form>

    <ul class="budget-list">
      <li v-for="item in plannedBudgets" :key="item.category">
        {{ item.category }}: R$ {{ item.plannedAmount.toFixed(2) }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import FormErrors from '../common/FormErrors.vue'
import { ref } from 'vue'
import { useBudgetPlanning } from '../../composables/useBudgetPlanning'
import { TransactionCategories } from '../../enum'

const category = ref('')
const plannedAmount = ref(0)
const errorMessages = ref<string[]>([])
const categories = TransactionCategories.OUTPUT

const { plannedBudgets, setPlannedAmount } = useBudgetPlanning()

function handleSubmit() {
  errorMessages.value = []

  if (!category.value.trim()) {
    errorMessages.value.push('A categoria é obrigatória.')
  }
  if (plannedAmount.value <= 0) {
    errorMessages.value.push('O valor planejado deve ser maior que zero.')
  }

  if (errorMessages.value.length > 0) return

  setPlannedAmount(category.value.trim(), plannedAmount.value)
  category.value = ''
  plannedAmount.value = 0
}


</script>