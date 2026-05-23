<template>
    <DateSelector 
        v-if="show"
        @select="handleDateSelect($event)"
    />
    <div class="modal-container" @click="dismiss">
        <div class="sow-form" @click.stop>
            <div class="form-header">
                Edycja lochy nr: {{ editSow.number }}
            </div>
            <div class="sow-form-body">
                <div class="form-input-header">Grupa
                    <input class="form-input" v-model="group" />
                </div>
                <div class="form-input-header">Status
                    <select class="form-input" v-model="sowStatus">
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
        </div>
</template>
<script setup>
    import { ref } from 'vue'
    import DateSelector from './DateSelector.vue'
    import { getStatus, statusesPL, formatDateDMY, formatDateYMD } from '@/utils/utils'

    const emit = defineEmits(['dismiss', 'save'])
    const props = defineProps({
        editSow: Object
    })

    const dismiss = () => {
        emit('dismiss', false)
    }

    const show = ref(false)
    const editSow = ref(props.editSow)
    const group = ref(editSow.value.group)
    const sowStatus = ref(getStatus(editSow.value.status))
    const disposalDate = ref(editSow.value.disposalDate)
    const note = ref(editSow.value.note)

    if(disposalDate.value == null){
        disposalDate.value = formatDateDMY(new Date())
    }


    const save = () => {
        editSow.value.group = group.value
        editSow.value.note = group.value
        editSow.value.status = getStatus(sowStatus.value)
        if( editSow.value.status === "DECEASED" || editSow.value.status === "SOLD" ){   
            editSow.value.disposalDate = disposalDate.value
        } 
        else{
            editSow.value.disposalDate = ""
        }
        let sowRequest = {
            id: editSow.value.id,
            number: editSow.value.number,
            status: editSow.value.status,
            group: editSow.value.group,
            disposalDate: formatDateYMD(editSow.value.disposalDate),
            note: editSow.value.note
        }
        emit('save', sowRequest)
        dismiss()
    }

    const showDateSelector = () => {
        show.value=true
    }

    const handleDateSelect = (date) => {
        disposalDate.value = date
    }
    
</script>