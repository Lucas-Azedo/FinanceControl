<template>
    <DropdownMenu />

    <div class="dashboard">
        <TransactionTable ref="tableRef"/>
        <DashboardForm @transaction-added="handleTransactionAdded" />
    </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import DropdownMenu from '../../components/common/DropdownMenu.vue'
import DashboardForm from '../../components/dashboard/DashboardForm.vue'
import TransactionTable from '../../components/dashboard/TransactionTable.vue'

/*import Pagination from '../../components/dashboard/Pagination.vue'
import TransactionCharts from '../../components/dashboard/TransactionCharts.vue'
import TransactionFilters from '../../components/dashboard/TransactionFilters.vue'*/

const tableRef = ref()

function handleTransactionAdded() {
  tableRef.value?.listTransactions() // chamando método do filho
}
</script>