<template>
    <DateSelector 
        v-if="show"
        @select="handleDateSelect($event)"
    />
    <div class="modal-container" @click="dismiss"></div>
        <div class="sow-form">
            <div class="sow-form-header">
                Edycja lochy nr: {{ editSow.number }}
            </div>
            <div class="sow-form-body">
                <div class="form-input-header">Grupa
                    <input class="form-input" v-model="group" />
                </div>
                <div class="form-input-header">Status
                    <select class="form-input" v-model="sowStatus">
                        <option disabled value="">Wybierz jedno</option>
                        <option v-for="s in statusesPL" >{{ s }}</option>
                    </select>
                </div>
                <div class="form-input-header" v-if="['DECEASED', 'SOLD'].includes(getStatus(sowStatus))" >Data brakowania
                    <input class="form-input" v-model="disposalDate" @click="showDateSelector" />
                </div>
                <div class="form-input-header">Uwagi
                    <textarea rows="1" class="form-input form-textarea" v-model="note"></textarea>
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
        </div>
</template>
<script setup>
    import { ref } from 'vue'
    import DateSelector from './DateSelector.vue'

    const emit = defineEmits(['dismiss', 'save'])
    const props = defineProps({
        editSow: Object
    })

    const statusesPL = ["Wolna", "Pokryta", "Karmiąca", "Padnięta", "Sprzedana"]
    const statuses = ["FREE", "INSEMINATED", "FARROWED", "DECEASED", "SOLD"]
    const show = ref(false)
    const editSow = ref(props.editSow)
    const group = ref(editSow.value.group)
    const sowStatus = ref()
    const disposalDate = ref(editSow.value.disposalDate)

    const note = ref(editSow.value.note)
    const dismiss = () => {
        emit('dismiss', false)
    }

    const save = () => {
        editSow.value.group = group.value
        editSow.value.note = group.value
        editSow.value.status = getStatus(sowStatus.value)
        
    }

    const showDateSelector = () => {
        show.value=true
    }

    const handleDateSelect = (date) => {
        disposalDate.value = date
    }

    function getStatus(status){
        switch(status){
            case statuses[0]:
                return statusesPL[0]
            case statuses[1]:
                return statusesPL[1]
            case statuses[2]:
                return statusesPL[2]
            case statuses[3]:
                return statusesPL[3]
            case statuses[4]:
                return statusesPL[4]
            case statusesPL[0]:
                return statuses[0]
            case statusesPL[1]:
                return statuses[1]
            case statusesPL[2]:
                return statuses[2]
            case statusesPL[3]:
                return statuses[3]
            case statusesPL[4]:
                return statuses[4]       
            }
    }

    sowStatus.value = getStatus(editSow.value.status)
    
</script>