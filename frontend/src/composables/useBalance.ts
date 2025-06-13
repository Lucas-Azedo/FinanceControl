import { ref } from 'vue'

const balance = ref<number>(getStoredBalance())

function getStoredBalance() {
  const value = localStorage.getItem('balance')
  return value ? parseFloat(value) : 0
}

function setBalance(newBalance: number) {
  balance.value = newBalance
  localStorage.setItem('balance', newBalance.toFixed(2))
}

export function useBalance() {
  return { balance, setBalance }
}