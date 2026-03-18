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
    import { monthNames, weekDays, formatDate } from '../utils/utils'
    const emit = defineEmits(['select'])
    const date = ref(new Date())
    const monthYear = ref(monthNames[date.value.getMonth()] + " " + date.value.getFullYear())
    const daysOfMonth = ref([])

    const getColor = (month) => {
        if(month !== 0)
            return "otherMonth"
        return ""
    }

    const calculateDays = () => {
        let days = []
        let helperDate = new Date(date.value)
        let day
        helperDate.setDate(1)
        let dayOfWeek = helperDate.getDay()
        if(dayOfWeek===0) dayOfWeek=7
        for(let i = 0; i < dayOfWeek-1; i++){
            let prevMonth = new Date(helperDate)
            prevMonth.setDate(prevMonth.getDate() - dayOfWeek + i + 1)
            day = prevMonth.getDate()
            days.push({d: day, m:-1})
        }
        while (helperDate.getMonth() === date.value.getMonth()){
            day = helperDate.getDate()
            days.push({d: day, m:0})
            helperDate.setDate(day + 1)
        }
        dayOfWeek = helperDate.getDay()
        if(dayOfWeek !== 1)
        for (let i = dayOfWeek-1; i<7; i++){
            day = helperDate.getDate()
            helperDate.setDate(day + 1)
            days.push({d: day, m:1})
        }
        return days
        
    }

    daysOfMonth.value = calculateDays()
    
    const nextMonth = () => {
        date.value.setMonth(date.value.getMonth() + 1)
        if(date.value.getMonth() === 12) {
            date.value.setFullYear(date.value.getFullYear() + 1)
            date.value.setMonth(0)
        }
        monthYear.value = monthNames[date.value.getMonth()] + " " + date.value.getFullYear()
        daysOfMonth.value = calculateDays()
    }

    const previousMonth = () => {
        date.value.setMonth(date.value.getMonth() - 1)
        if(date.value.getMonth() === -1) {
            date.value.setFullYear(date.value.getFullYear() - 1)
            date.value.setMonth(11)
        }
        monthYear.value = monthNames[date.value.getMonth()] + " " + date.value.getFullYear()
        daysOfMonth.value = calculateDays()
    }
    
    const dayClick = (day) => {
        let selectedDate = new Date(date.value.getFullYear(), date.value.getMonth() + day.m, day.d)
        let finalDate = formatDate(selectedDate)
        emit('select', finalDate)
    }


</script>