<template>
    <Transition name="alert-transition">
        <div class="alert" :class="[type]" v-if="show">
            {{ message }}
                <img class="alert-close" src="../assets/close.svg" @click="dismiss"/>
        </div>
    </Transition>
</template>
<script setup>
    
    import emitter from '@/api/eventBus'
    import { onBeforeUnmount, onMounted, ref } from 'vue'

    const show = ref(false)
    const message = ref('')
    const alert = "alert"
    const type = ref("success")

    const handler = (data) => {
        show.value=true
        message.value = data.message
        type.value = data.type
        if(data.timeout != null){
            setTimeout(() => show.value=false, data.timeout)
        }
    }

    const dismiss = () => {
        show.value = false
    }

    onMounted(() => {
        emitter.on('alert', handler)
    })

    onBeforeUnmount(() => {
        emitter.off('alert', handler)
    })

</script>