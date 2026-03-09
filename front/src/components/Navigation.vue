<template>
    <div class="nav-menu">
        <div class="nav-menu-border"></div>
        <div class="nav-menu-arrow">
            &rarr;
        </div>
        <div class="nav-menu-header">
            Menu
        </div>
        <div class="nav-button-container">
            <button class="nav-button" @click="toDashboard">
                Strona główna
            </button>
            <button class="nav-button" @click="toCalendar">
                Kalendarz
            </button>
            <button class="nav-button" @click="toSow">
                Karta lochy
            </button>
        </div>
    </div>
</template>
<script setup>
    import { useRouter } from 'vue-router';
    const router = useRouter()
    const toDashboard = () => {
        router.push('/dashboard')
    }
    const toCalendar = () => {
        router.push('/calendar')
    }
    const toSow = () => {
        router.push('/sow')
    }
</script>
