<template>
    <div class="header">
      <div class="saldo">
        <h3>Saldo Atual: <span :class="balance >= 0 ? 'success' : 'errors'">
        R$ {{ balance.toFixed(2) }}
        </span></h3>
      </div>
      <div class="menu-container">
      <button @click="toggleMenu">Menu</button>
      <div v-if="showMenu" class="dropdown">
        <ul>
          <li><button @click="goToProfile">Ver Perfil</button></li>
          <li><button @click="handleLogout">Logout</button></li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useNavigation } from '../../composables/useNavigation'
import { useAuth } from '../../composables/useAuth'
const { redirect } = useNavigation()
const { logout } = useAuth()

const showMenu = ref(false)
const balance = ref(0)

const storedBalance = localStorage.getItem('balance')
balance.value = storedBalance ? parseFloat(storedBalance) : 0

  function toggleMenu() {
      showMenu.value = !showMenu.value
  }

  function goToProfile() {
      showMenu.value = false
      redirect('/profile')
  }

  function handleLogout() {
      showMenu.value = false
      logout()
      redirect('/signin')
  }

</script>