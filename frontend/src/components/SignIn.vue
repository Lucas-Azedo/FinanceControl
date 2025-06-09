<template>
    <div id="login" class="all">
        <h1>SignIn</h1>
        <input v-model="email" placeholder="e-mail" />
        <input v-model="password" placeholder="Password" type="password" />
        <button @click="signIn">Logar</button>
    </div>
</template>

<script setup>
import { ref } from 'vue';

    const email = ref('')
    const password = ref('')

    async function signIn() {
        try{
            const response = await fetch('http://localhost:8080/auth/signin', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    email: email.value,
                    password: password.value
                })
            })

        const data = await response.json();

        if (!response.ok) {
            console.error('Erro do backend:', data)
            throw new Error(data.message || 'Erro ao cadastrar')
        }

        console.log("Usuario logado!", data)
        }catch(error){
            console.error('Erro no signIn:', error)
        }
    }

</script>