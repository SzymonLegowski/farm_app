<template>
    <div class="top-bar">
        <div class="top-bar-header">
            {{ title }}
        </div>
    </div>
</template>
<script setup>
    defineProps({
        title: String
    })
</script>