<template>
    <div class="modal-container" @click="dismiss"></div>
    <div class="sow-select-grid">
        <button 
            v-for="number in 100"
            class="sow-select-grid-button" 
            :class="getColorClass(number)" 
            @click="handleClick(number)">
                {{ number }}
        </button>
    </div>
</template>
<script setup>
    import apiClient from '@/api/apiClient'

    const props = defineProps({
        show: Boolean,
        sows: Array
    })

    const emit = defineEmits(['dismiss', 'select', 'add'])
    const dismiss = () => {
        emit('dismiss', false)
    }

    const handleClick = (number) => {
        console.log("pressed", number)
        const selectedSow = props.sows.filter((sow) => sow.number == number)[0]
        console.log(selectedSow)
        if(selectedSow != null){
            emit('select', selectedSow)
            dismiss();
        }else{
            quickAddSow(number)
        }
    }

    const getColorClass = (number) => {
        if(props.sows == null) return ''
        const status = props.sows
            .filter((sow) => sow.number == number)
            .map(sow => sow.status)[0]
        switch (status) {
            case 'FREE': return 'bg-free'
            case 'INSEMINATED': return 'bg-inseminated'
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
                console.log(response)
                props.sows.push(response.data)
                emit('select', response.data)
                dismiss()
            }).catch((err) => console.log(err))
    }

</script>