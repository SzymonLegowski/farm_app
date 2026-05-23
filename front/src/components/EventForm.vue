<template>
    <div class="modal-container" @click="dismiss">
        <div class="event-form-container" @click.stop>
            <template v-if="props.event">
                <div class="form-header" >
                    Edytuj {{ previousType }} 
                </div>
            </template>
            <template v-else>
                <div class="form-header">
                    Nowe {{ selectedType ? selectedType : "Wydarzenie" }}
                </div>
            </template>
                <div class="form-input-header">Data
                    <div class="form-input">
                        {{ formatDateDMY(props.date) }}
                    </div>
                </div>
                <div class="form-input-header">Typ
                    <select class="form-input" v-model="selectedType">
                        <option v-for="s in eventTypesSelect" >{{ s }}</option>
                    </select>
                </div>
                <div class="form-input-header" v-if="selectedType === eventTypesSelect[0]"> Grupa
                    <input class="form-input" v-model="sowGroup"/>
                </div>
                <div class="checkbox-wrapper">
                    <input id="update-status" type="checkbox" class="form-input-checkbox" v-model="updateStatuses"/>
                    <svg class="checkbox-checkmark" viewBox="0 0 50 50">
                        <path d="M7 28 L 20 40 L 43 10"></path>
                    </svg>
                    <label for="update-status" class="checkbox-label">Aktualizuj statusy loch</label>
                </div>
            <div class="form-input-buttons">
                <button class="nav-button form-input-save" @click="save">
                    Zapisz
                </button>
                <button class="nav-button form-input-cancel" @click="dismiss">
                    Anuluj
                </button>
            </div>
        </div>
            <div class="event-form-container" @click.stop>
                <div class="form-input-header">Lochy
                    <div class="event-sows-container event-sows-headers">
                        <div class="event-sow-number-header">
                            Nr
                        </div>
                        <div class="event-sow-litter_no-header">
                            Miot
                        </div>
                        <div class="event-sow-note-header" v-if="selectedType === eventTypesSelect[0]">
                            Rasa
                        </div>
                    </div>
                    <div class="event-sows-container" v-for="s in eventSows">
                        <div class="form-input event-sow-number" @click="handleSowSelect(s.sow_id)">
                            {{ s.sow_no }}
                        </div>
                        <div class="event-sow-number">
                            {{ s.litter_no }}
                        </div>
                        <template v-if="selectedType === eventTypesSelect[0]">
                            <input class="event-sow-note" v-model="s.note"/>
                        </template>
                    </div>
                </div>
            </div>
        <SowSelectGrid 
            @dismiss="showSelectGrid = $event" 
            @select="handleSowSelect($event)"
        />
    </div>
</template>
<script setup>
import SowSelectGrid from '@/components/SowSelectGrid.vue'
import { eventTypesSelect, formatDateDMY} from '@/utils/utils'
import { ref } from 'vue'
import { useSowsStore } from '@/stores/sows'
import { statuses } from '@/utils/utils'
import apiClient from '@/api/apiClient'
import emitter from '@/api/eventBus'

const previousType = ref(null)
const selectedType = ref(null)
const eventSows = ref([])
const sowGroup = ref(null)
const updateStatuses = ref(true)

const emit = defineEmits(['dismiss', 'event-post'])
const dismiss = () => {
    emit('dismiss', false)
}

const props = defineProps({
    event: Object,
    date: Object,
    latestGroup: Number
})

const getSowLitters = (id) => {
    let litterIds = useSowsStore().sows.value.find(sow => {
        return sow.id === id
    }).litterIds
    return litterIds
}

const handleSowSelect = (sow) => {
    let selectedSowId
    if(typeof sow === 'number')
        selectedSowId = sow
    else
        selectedSowId = sow.id
    let isAdded = eventSows.value.find(s => {
        return s.sow_id === selectedSowId
    })
    if(isAdded !== undefined){
        eventSows.value.splice(eventSows.value.indexOf(isAdded), 1)
        return
    }
    let litterId = sow.litterIds[sow.litterIds.length - 1]
    let litterNo = sow.litterIds.length
    if(sow.status !== statuses[1]){
        litterNo++
        litterId = null
    }
    let sowToAdd = {
        sow_id: sow.id,
        sow_no: sow.number,
        note: null,
        litter_id: litterId,
        litter_no: litterNo,
    }
    eventSows.value.push(sowToAdd)
    eventSows.value.sort((a, b) => a.sow_no - b.sow_no)
}

const save = () => {
    if(previousType.value === null)
        {
            const year = props.date.getFullYear()
            const month = String(props.date.getMonth()+1).padStart(2, '0')
            const day = String(props.date.getDate()).padStart(2, '0')
            let eventRequest = {
                type: eventTypesSelect.indexOf(selectedType.value),
                sowIdNoteList: eventSows.value.map(s => `${s.sow_id},${s.note ? s.note : ""}`),
                date: `${year}-${month}-${day}`,
                sowGroup: sowGroup.value,
                updateSowStatus: updateStatuses.value
            }
            apiClient.post('sows/event', eventRequest)
                .then((response) => {
                    emit('event-post')
                    emitter.emit('alert', {message: 'Dodano pomyślnie', type: 'success', timeout: 1000})
                }).catch((err) => {
                    console.log(err)
                    emitter.emit('alert', {message: 'Błąd podczas dodawania wydarzenia', type: 'error', timeout: 1000})
                })
        }
    else
        {
        }
}

if(props.event !== undefined && props.event !== null){
    selectedType.value = eventTypesSelect[props.event.type]
    previousType.value = eventTypesSelect[props.event.type]
    eventSows.value = props.event.sows
}

sowGroup.value = props.latestGroup

</script>