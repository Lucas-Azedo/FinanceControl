<script setup lang="ts">
import SignUp from './components/SignUp.vue'
import SignIn from './components/SignIn.vue'
</script>

<template>
  <div> 
    <SignUp />
    <SignIn />
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
