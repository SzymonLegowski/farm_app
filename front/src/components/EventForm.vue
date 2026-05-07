<template>
    <div class="modal-container" @click="dismiss"></div>
        <div class="event-form-container">
            <template v-if="props.date">
                <div class="form-header">
                    Nowe {{ selectedType ? selectedType : "Wydarzenie" }} <br/>{{ formatDateDMY(props.date) }}
                </div>
            </template>
            <template v-if="props.editEvent">
                <div class="form-header" >
                    Edytuj {{ selectedType }} {{ formatDateDMY(props.editEvent.date) }}
                </div>
            </template>
                 <div class="form-input-header">Typ
                    <select class="form-input" v-model="selectedType">
                        <option disabled value="">Wybierz jedno</option>
                        <option v-for="s in eventTypesSelect" >{{ s }}</option>
                    </select>
                </div>
                <div class="form-input-header">Lochy  (nr lochy:nr miotu)
                    <div class="form-input event-form-sows-input" @click="onSowsButtonClick">
                        {{ sows.map(s => `${s.number}:${s.litter_no}`).join(',') }}
                    </div>
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
</template>
<script setup>
import { eventTypesSelect, formatDateDMY } from '@/utils/utils'
import { ref } from 'vue'

const emit = defineEmits(['dismiss'])
const dismiss = () => {
    emit('dismiss', false)
}

const save = () => {

}

const props = defineProps({
    editEvent: Object,
    date: Object
})

const onSowsButtonClick = () => {
    
}

const selectedType = ref(eventTypesSelect[props.editEvent?.type])
const sows = ref(props.editEvent.sows)

</script>