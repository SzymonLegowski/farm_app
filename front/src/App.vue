<template>
  <Navigation/>
  <div class="main-view">
    <RouterView/>
  </div>
</template>

<script setup>
  import { useRouter } from 'vue-router'
  import Navigation from './components/Navigation.vue';
  const router = useRouter()

  router.push('/dashboard')
</script>
