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
  const router = useRouter()

  router.push('/dashboard')
</script>
