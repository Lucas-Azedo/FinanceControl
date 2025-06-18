import { ref, watch } from 'vue';
import { TransactionCategories } from '../enum';

interface BudgetPlan {
  category: string;
  plannedAmount: number;
}

const STORAGE_KEY = 'plannedBudgets';

export function useBudgetPlanning() {
  const stored = localStorage.getItem(STORAGE_KEY);

  const plannedBudgets = ref<BudgetPlan[]>(
    stored
      ? JSON.parse(stored)
      : TransactionCategories.OUTPUT.map(category => ({
          category,
          plannedAmount: 0,
        }))
  );

  const setPlannedAmount = (category: string, amount: number) => {
    const index = plannedBudgets.value.findIndex((b: BudgetPlan) => b.category === category);
    if (index !== -1) {
      plannedBudgets.value[index].plannedAmount = amount;
    } else {
      plannedBudgets.value.push({ category, plannedAmount: amount });
    }
  };

  watch(
    plannedBudgets,
    (newVal) => {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(newVal));
    },
    { deep: true }
  );

  return {
    plannedBudgets,
    setPlannedAmount,
  };
}