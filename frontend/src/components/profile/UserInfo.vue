<template>
    <div id="user">
        <p v-if="user">Nome: {{ user.name }}</p>
        <p v-if="user">Email: {{ user.email }}</p>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApiFetch } from '../../composables/useApiFetch'
import { useExtractErrors } from '../../composables/useExtractErrors'

type User = {
    name: string
    email: string
}

const errorMessages = ref<string[]>([])
const user = ref<User | null>(null)

const { extractErrors } = useExtractErrors()


async function showUser() {
    try{
        const data = await useApiFetch('http://localhost:8080/me', {
            method: 'GET',
        })

        if(data){
            console.log("Usuario obtido com sucesso")
            user.value = data
        }

    }catch(error){
        console.error('Erro ao obter usuario:', error)
        errorMessages.value=extractErrors(error)
    }
}

onMounted(() => {
    showUser()
})
</script>