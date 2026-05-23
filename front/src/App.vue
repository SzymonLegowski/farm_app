<template>
  <Navigation/>
  <div class="main-view">
    <Alert/>
    <RouterView/>
  </div>
</template>

<script setup>
  import { useRouter } from 'vue-router'
  import Navigation from './components/Navigation.vue';
  import Alert from './components/Alert.vue';
  import { useSowsStore } from './stores/sows';
  const router = useRouter()
  const sowsStore = useSowsStore()
  sowsStore.fetchSows()
  
  // apiClient.get('sows', {timeout: 3000})
  //     .then((response) => {
  //       sowsStore.sows.value = response.data
  //         })
  //     .catch((err) => {
  //         console.log(err)
  //         emitter.emit('alert', {message: 'Błąd podczas pobierania danych', type: 'error'})
  //     })

  router.push('/dashboard')
</script>
