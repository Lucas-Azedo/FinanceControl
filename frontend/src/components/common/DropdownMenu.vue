<template>
    <div class="header">
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
import { ref } from 'vue'
import { useNavigation } from '../../composables/useNavigation'
import { useAuth } from '../../composables/useAuth'

const { redirect } = useNavigation()
const { logout } = useAuth()
const showMenu = ref(false)

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