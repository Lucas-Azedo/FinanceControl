<template>
    <div class="header">
      <div class="saldo">
        <h3>Saldo Atual: <span :class="balance >= 0 ? 'success' : 'errors'">
        R$ {{ balance.toFixed(2) }}
        </span></h3>
      </div>
      <div class="redirect">
        <button @click="goTo('dashboard')">Planejador</button>
        <button @click="goTo('planner')">Gerenciador</button>
      </div>
      <div class="menu-container">
      <button @click="toggleMenu">Menu</button>
      <div v-if="showMenu" class="dropdown">
        <ul>
          <li><button @click="goTo('profile')">Ver Perfil</button></li>
          <li><button @click="handleLogout">Logout</button></li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useNavigation } from '../../composables/useNavigation'
import { useAuth } from '../../composables/useAuth'
import { useBalance } from '../../composables/useBalance'
const { redirect } = useNavigation()
const { logout } = useAuth()
const { balance } = useBalance()

const showMenu = ref(false)

  function toggleMenu() {
      showMenu.value = !showMenu.value
  }

  function goTo(location: string) {
      showMenu.value = false
      redirect(`/${location}`)
  }

  function handleLogout() {
      showMenu.value = false
      logout()
      redirect('/signin')
  }
</script>