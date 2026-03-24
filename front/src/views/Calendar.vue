<template>
    <div class="calendar-container">
        <div class="date-selector-header calendar-header">
            <button class="button calendar-date-selector-button" @click="previousMonth"> < </button>
            <div> {{ monthYear }} </div>
            <button class="button calendar-date-selector-button" @click="nextMonth"> > </button>
        </div>
        <div class="calendar-weekdays">
            <div class="calendar-day-name" v-for="day in weekDays">{{ day }}</div>
        </div>
        <div class="calendar-body">
            <div class="button calendar-day-select" v-for="day in days" :class="getColor(day.m)">
                {{ day.d }}
                <div class="calendar-event">

                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
    import { ref } from 'vue';
    import { monthNames, weekDays, calculateDays } from '@/utils/utils';
    import apiClient from '@/api/apiClient';

    const date = ref(new Date())
    const days = ref(calculateDays(date.value))
    const monthYear = ref(monthNames[date.value.getMonth()] + " " + date.value.getFullYear())
    const getColor = (month) => {
        if(month !== 0)
            return "otherMonth"
        return ""
    }

    const inseminations = ref()
    const litters = ref()
    const events = ref()

    const fetchData = () => {
        apiClient.get(`litters/${date.value.getFullYear()}/${date.value.getMonth()+1}`)
            .then((r) => {
                console.log(r.data)
                litters.value = r.data
            })
            .catch((e) =>{
                console.log(e.response)
            })

        apiClient.get(`inseminations/${date.value.getFullYear()}/${date.value.getMonth()+1}`)
            .then((r) => {
                console.log(r.data)
              inseminations.value = r.data
            }).catch((e) => {
                console.log(e.response)
            })
    }
    
    const nextMonth = () => {
        date.value.setMonth(date.value.getMonth() + 1)
        if(date.value.getMonth() === 12) {
            date.value.setFullYear(date.value.getFullYear() + 1)
            date.value.setMonth(0)
        }
        monthYear.value = monthNames[date.value.getMonth()] + " " + date.value.getFullYear()
        days.value = calculateDays(date.value)
        fetchData()
    }

    const previousMonth = () => {
        date.value.setMonth(date.value.getMonth() - 1)
        if(date.value.getMonth() === -1) {
            date.value.setFullYear(date.value.getFullYear() - 1)
            date.value.setMonth(11)
        }
        monthYear.value = monthNames[date.value.getMonth()] + " " + date.value.getFullYear()
        days.value = calculateDays(date.value)
        fetchData()
    }

    fetchData()

</script>