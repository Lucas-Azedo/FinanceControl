<template>
  <div
    v-if="message"
    class="global-message"
    :class="message.type"
  >
    {{ message.text }}
    <button @click="clearMessage">OK</button>
  </div>
</template>

<script setup lang="ts">
import { useGlobalMessage } from '../../composables/useGlobalMessage'

const { globalMessage: message, clearMessage } = useGlobalMessage()
</script>

