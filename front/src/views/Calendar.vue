<template>
    <EventForm
        v-if="showEventForm"
        @dismiss="onEventFormClose($event)"
        @event-post="updateCalendar()"
        :event="selectedEvent"
        :date="selectedDate"
        :latestGroup="latestGroup"
    />
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
            <div class="button calendar-day-select" v-for="day in days" :class="getColor(day.m)" @click="onDayClick(day)">
                {{ day.d }}
                <hr style="border-top: 1px solid #000" />
                <div class="calendar-day-events">
                    <div v-for="date in eventsByDate?.filter(e => e.date.getDate() === day.d && e.date.getMonth() === date.getMonth() + day.m)" 
                        :key="date">
                        <div class="calendar-day-event" :class="getClass(e.type)" v-for="e in date.events" :key="e.id" @click.stop="onEventClick(e, date)">
                            {{ eventTypes[e.type] }}
                            <br/>
                            {{ e.sows.map(s => s.sow_no).join(', ') }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
    import { ref } from 'vue';
    import { monthNames, weekDays, calculateDays, eventTypes } from '@/utils/utils';
    import apiClient from '@/api/apiClient';
    import EventForm from '@/components/EventForm.vue';
import { useSowsStore } from '@/stores/sows';

    const showEventForm = ref(false)
    // const date = ref(new Date(2025, 11, 19))
    const date = ref(new Date())
    const days = ref(calculateDays(date.value))
    const monthYear = ref(monthNames[date.value.getMonth()] + " " + date.value.getFullYear())
    const eventsByDate = ref([])
    const selectedEvent = ref()
    const selectedDate = ref()
    const latestGroup = ref()

    const onEventFormClose = (event) => {
        showEventForm.value = event
        selectedDate.value = null
        selectedEvent.value = null
    }

    const onDayClick = (day) => {
        selectedDate.value = new Date(date.value.getFullYear(), date.value.getMonth() + day.m, day.d)
        showEventForm.value = true
    }

    const onEventClick = (event, date) => {
        showEventForm.value = true
        selectedEvent.value = event
        selectedEvent.value.date = date.date
        selectedDate.value = date.date
    }

    const getClass = (type) => {
        switch(type){
            case 0:
                return "insemination"
            case 1:
                return "farrowing"
            case 2:
                return "weaning"
            case 3:
                return "predfarrowing"
        }
    }

    const getColor = (month) => {
        if(month !== 0)
            return "otherMonth"
        return ""
    }

    const fetchData = () => {
        let start = new Date(date.value)
        let end = new Date(date.value)
        start.setDate(1)
        start.setDate(start.getDate() - start.getDay() + 1)
        end.setDate(1)
        end.setMonth(end.getMonth() + 1)
        end.setDate(end.getDate() + 7 - end.getDay())
        start = start.toISOString().split('T')[0]
        end = end.toISOString().split('T')[0]
        apiClient.get(`litters/events/${start}/${end}`)
            .then((r) => {
                eventsByDate.value = r.data.calendarData
                latestGroup.value = r.data.latestGroup
                for(let e of eventsByDate.value){ e.date = new Date(Date.parse(e.date))}
            })
            .catch((e) =>{
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

    const updateCalendar = () => {
        useSowsStore().fetchSows()
        fetchData()
    }

    fetchData()

</script>