<template>
  <div class="budget-comparison card">
    <h2>Comparação Planejado x Real</h2>

    <table class="table">
      <thead>
        <tr>
          <th>Categoria</th>
          <th>Planejado</th>
          <th>Real</th>
          <th>Diferença</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in comparison" :key="item.category">
          <td>{{ formatLabel(item.category) }}</td>
          <td class="form">
          <input
          type="number"
          v-model.number="item.planned"
          @change="updatePlannedAmount(item.category, item.planned)"
          />
        </td>

          <td>{{ formatCurrency(item.actual) }}</td>
          <td :class="item.diff < 0 ? 'errors' : 'success'">
            {{ formatCurrency(item.diff) }}
          </td>
        </tr>
      </tbody>
    </table>

    <p v-if="comparison.length === 0" class="empty-msg">Nenhum dado disponível.</p>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useBudgetPlanning } from '../../composables/useBudgetPlanning';

interface BudgetPlan {
  category: string;
  plannedAmount: number;
}

interface Transaction {
  category: string;
  amount: number;
  type: string; // 'INPUT' or 'OUTPUT'
}

const props = defineProps<{
  transactions: Transaction[];
}>();

const { plannedBudgets } = useBudgetPlanning(); 

function updatePlannedAmount(category: string, newAmount: number) {
  const budget = (plannedBudgets.value as BudgetPlan[]).find((b: BudgetPlan) => b.category === category);

  if (budget) {
    budget.plannedAmount = newAmount;
  }
}

const comparison = computed(() =>
  (plannedBudgets.value as BudgetPlan[]).map((plan: BudgetPlan) => {
    const plannedCategoryNormalized = plan.category.trim().toUpperCase();

    const actualSpent = props.transactions
      .filter(
        (t: Transaction) =>
          t.type === 'OUTPUT' &&
          t.category.trim().toUpperCase() === plannedCategoryNormalized
      )
      .reduce((sum, tx) => sum + tx.amount, 0);

    return {
      category: plan.category,
      planned: plan.plannedAmount,
      actual: actualSpent,
      diff: plan.plannedAmount - actualSpent,
    };
  })
);

function formatCurrency(value: number): string {
  return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

function formatLabel(value: string): string {
  return value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
}
</script>
