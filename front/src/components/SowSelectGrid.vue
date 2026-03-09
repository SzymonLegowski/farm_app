<template>
    <div class="sow-select-container" @click="dismiss"></div>
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
    const props = defineProps({
        show: Boolean,
        sows: Array
    })
    const emit = defineEmits(['dismiss', 'select'])
    props.sows.forEach(sow => {
        console.log(sow.number)
        console.log(sow.status)
    });
    const dismiss = () => {
        emit('dismiss', false)
    }
    const handleClick = (number) => {
        console.log("pressed", number)
        const selectedSow = props.sows.filter((sow) => sow.number == number)[0]
        emit('select', selectedSow)
        dismiss();
    }
    const getColorClass = (number) => {
        const status = props.sows
            .filter((sow) => sow.number == number)
            .map(sow => sow.status)[0]
        switch (status) {
            case 'FREE': return 'bg-free'
            case 'INSEMINATED': return 'bg-inseminated'
            case 'FARROWED': return 'bg-farrowed'
        }
    }

</script>