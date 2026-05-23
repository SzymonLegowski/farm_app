<template>
    <Transition name="">
        <div class="sow-select-grid" @click.stop>
            <button 
                v-for="number in 100"
                class="sow-select-grid-button" 
                :class="getColorClass(number)" 
                @click="handleClick(number)">
                    {{ number }}
            </button>
        </div>
    </Transition>
</template>
<script setup>
    import apiClient from '@/api/apiClient'
    import { useSowsStore } from '@/stores/sows'
    import { ref } from 'vue'


    const sows = ref(useSowsStore().sows)
    const emit = defineEmits(['dismiss', 'select', 'add'])
    const dismiss = () => {
        emit('dismiss', false)
    }

    const handleClick = (number) => {
        const selectedSow = sows.value.filter((sow) => sow.number == number)[0]
        if(selectedSow != null){
            emit('select', selectedSow)
            dismiss();
        }else{
            quickAddSow(number)
        }
    }

    const getColorClass = (number) => {
        if(sows.value == null) return ''
        const status = sows.value
            .filter((sow) => sow.number == number)
            .map(sow => sow.status)[0]
        switch (status) {
            case 'FREE': return 'bg-free'
            case 'INSEMINATED': return 'bg-inseminated'
            case 'GRAVID': return 'bg-gravid'
            case 'FARROWED': return 'bg-farrowed'
        }
    }

    const quickAddSow = async (number) => {
        //TODO ADD CONFIRM WINDOW TO AVOID MISSCLICK ADDING
        let newSow = {
            number: number,
            status: "FREE",
            group: 0,
            note: ""
        }
        await apiClient.post(`/sows`, newSow)
            .then((response) =>{
                sows.value.push(response.data)
                emit('select', response.data)
                emitter.emit('alert', {message: 'Dodano pomyślnie', type: 'success', timeout: 1000})
                dismiss()
            }).catch((err) => {
                emitter.emit('alert', {message: 'Błąd podczas dodawania lochy', type: 'error'})
            })
    }

</script>