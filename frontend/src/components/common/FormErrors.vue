<template>
    <div class="errors">
        <ul v-if="props.errors.length">
          <li v-for="(msg, i) in props.errors" :key="i">{{ msg }}</li>
        </ul>
    </div>
</template>
<script setup lang="ts">
const props = defineProps<{ errors: string[] }>()
</script>
