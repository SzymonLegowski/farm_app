<template>
    <div class="date-selector-container ">
        <div class="date-selector-header">
            <button class="button date-selector-button" @click="previousMonth"> < </button>
            <div> {{ monthYear }} </div>
            <button class="button date-selector-button" @click="nextMonth"> > </button>
        </div>
        <div class="date-selector-body">
            <div v-for="day in weekDays"> {{ day }} </div>
            <div class="button day-select" v-for="day in daysOfMonth" :class="getColor(day.m)" @click="dayClick(day)">
                {{ day.d }}
            </div>
        </div>
    </div>
</template>
<script setup>
    import { ref } from 'vue';
    import { monthNames, weekDays, formatDateDMY, calculateDays } from '../utils/utils'
    const emit = defineEmits(['select'])
    const date = ref(new Date())
    const monthYear = ref(monthNames[date.value.getMonth()] + " " + date.value.getFullYear())
    const daysOfMonth = ref([])

    const getColor = (month) => {
        if(month !== 0)
            return "otherMonth"
        return ""
    }

    daysOfMonth.value = calculateDays(date.value)
    
    const nextMonth = () => {
        date.value.setMonth(date.value.getMonth() + 1)
        if(date.value.getMonth() === 12) {
            date.value.setFullYear(date.value.getFullYear() + 1)
            date.value.setMonth(0)
        }
        monthYear.value = monthNames[date.value.getMonth()] + " " + date.value.getFullYear()
        daysOfMonth.value = calculateDays(date.value)
    }

    const previousMonth = () => {
        date.value.setMonth(date.value.getMonth() - 1)
        if(date.value.getMonth() === -1) {
            date.value.setFullYear(date.value.getFullYear() - 1)
            date.value.setMonth(11)
        }
        monthYear.value = monthNames[date.value.getMonth()] + " " + date.value.getFullYear()
        daysOfMonth.value = calculateDays(date.value)
    }
    
    const dayClick = (day) => {
        let selectedDate = new Date(date.value.getFullYear(), date.value.getMonth() + day.m, day.d)
        let finalDate = formatDateDMY(selectedDate)
        emit('select', finalDate)
    }


</script>