<template>
    <SowSelectGrid 
        v-if="showSelectGrid" 
        :sows="sows"    
        @dismiss="showSelectGrid = $event" 
        @select="handleSowSelect($event)"
    />
    <SowForm
        v-if="showSowForm"
        :editSow="selectedSow"
        @dismiss="showSowForm = $event"
        @save="handleSowEdit($event)"
    />
    <DateSelector
        v-if="showDateSelector"
        @select=""
    />
    <div class="top-bar">
        <button class="button" @click="viewSelectGrid">
            Wybierz lochę
        </button>
        <div class="top-bar-header" v-if="selectedSow.number">
            Locha nr {{ selectedSow.number }}
        </div>
        <div class="top-bar-header" v-if="selectedSow.number">
            Grupa {{  selectedSow.group }}
        </div>
        <button class="button" @click="viewSowForm" v-if="selectedSow.number">
            Edytuj lochę
        </button>
    </div>
    <div class="sow-card-container">
        <table class="sow-card">
            <thead class="sow-card-header">
                <tr>
                    <th :colspan="1 + maxInseminationCount">Sektor krycia</th>
                    <th :colspan="7" style="border-right: 0px;">Porodówka</th>
                    <!-- <th :colspan="7">Porodówka</th>
                    <th :rowspan="4">Ocena <br/> prosiąt </th>
                    <th :rowspan="4" style="border-right: 0px;">indeks <br/> prod. <br/> 365 dni</th> -->
                </tr>
                <tr>
                    <th :rowspan="3">Nr miotu</th>
                    <th :colspan="maxInseminationCount">Data pokrycia</th>
                    <th :colspan="3">Data</th>
                    <th :colspan="4" style="border-right: 0px;">Liczba prosiąt</th>
                </tr>
                <tr>
                    <th v-for="n in maxInseminationCount" :rowspan="2">{{n}}.<br/> (rasa kn.)</th>
                    <th :rowspan="2">Przew. <br/> oproszenia</th>
                    <th :rowspan="2">Oproszenia</th>
                    <th :rowspan="2">Odsadzenia</th>
                    <th :colspan="2">Urodzonych</th>
                    <th :rowspan="2">Przygnieconych</th>
                    <th :rowspan="2" style="border-right: 0px;">Odsadzonych</th>
                </tr>
                <tr>
                    <th>żywe</th>
                    <th>martwe</th>
                </tr>
            </thead>
            <tbody>
            <tr v-for="(litter, index) of sowData" class="sow-card-row">
                <td>{{ index+1 }}</td>
                <!-- <td v-for="n in maxInseminationCount"><input class="sow-card-input" v-model="litter.inseminations[n-1]"></td> -->
                <td v-for="n in maxInseminationCount"> {{ litter.inseminations[n-1] }} </td>
                <td style="background-color: rgba(125, 125, 125, 0.2); user-select: none;" >{{ litter.predictedFarrowing }}</td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.farrowing"></td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.weaning"></td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.bornAlive"></td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.bornDeceased"></td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.deceased"></td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.weaned"></td>
            </tr>
            </tbody>
        <button class="button save-litter" @click="save" v-if="editedLitters.length > 0">Zapisz zmiany</button>
        </table>
    </div>
</template>
<script setup>
import apiClient from '@/api/apiClient'
import SowSelectGrid from '@/components/SowSelectGrid.vue'
import SowForm from '@/components/SowForm.vue'
import { ref } from 'vue'
import emitter from '@/api/eventBus'
import { formatStringDateDMY, formatDateYMD, formatDateDMY } from '@/utils/utils'
import DateSelector from '@/components/DateSelector.vue'

const showSelectGrid = ref(false)
const showSowForm = ref(false)
const showDateSelector = ref(false)
const sows = ref()
const selectedSow = ref({})
const sowData = ref()
const maxInseminationCount = ref(3)
const isDataFetched = ref(false)
const editedLitters = ref([])

// emitter.emit('alert', {message: 'Pobieranie danych...', type: 'info'})

const handleSowSelect = (sow) => {
    selectedSow.value = sow
    console.log(sow)
    apiClient.get(`litters/sow/${sow.id}`)
        .then((response) => {
            sowData.value = response.data
            for(let litter of sowData.value){
                for(let i in litter.inseminations){
                    litter.inseminations[i] = formatStringDateDMY(litter.inseminations[i])
                }
                litter.predictedFarrowing = formatStringDateDMY(litter.predictedFarrowing)
                litter.farrowing = formatStringDateDMY(litter.farrowing)
                litter.weaning = formatStringDateDMY(litter.weaning)
                if(litter.inseminations.length > maxInseminationCount.value){
                    maxInseminationCount.value = litter.inseminations.length
                }
                console.log(litter)
            }
        })
        editedLitters.value = []
}

apiClient.get('sows', {timeout: 3000})
    .then((response) => {
        sows.value = response.data
        showSelectGrid.value = true
        isDataFetched.value = true
        })
    .catch((err) => {
        console.log(err)
        emitter.emit('alert', {message: 'Błąd podczas pobierania danych', type: 'error'})
    })

const viewSelectGrid = () => {
    if(isDataFetched.value){ showSelectGrid.value = true }
    else{ emitter.emit('alert', {message: 'Brak połączenia z api', type: 'error', timeout: 1000}) }
}

const viewSowForm = () => {
    console.log("clicked")
    showSowForm.value = true
}

const handleSowEdit = (editSow) => {
    apiClient.put(`sows/${editSow.id}`, editSow)
    .then((response) => {
        console.log(response)
        emitter.emit('alert', {message: 'Zapisano pomyślnie', type: 'success', timeout: 1000})
        if(editSow.disposalDate != null)
            sows.value = sows.value.filter(s => s.id !== editSow.id)      
    })
    .catch((e) => {
        console.log("e: " + e)
        console.log("e.response: " + e.response)
        emitter.emit('alert', {message: 'Coś poszło nie tak', type: 'error', timeout: 1000})
    })
    console.log(editSow)
}

const onChange = (litterId) =>{
    if(editedLitters.value.find(id => id === litterId)) return
    editedLitters.value.push(litterId)
    console.log(editedLitters.value)
    
}

const save = async () =>{
    for(let litterId of editedLitters.value){
        try{       
            let litter = sowData.value.find(l => l.id == litterId)
            let litterRequest = {
                id: litter.id,
                farrowing: formatDateYMD(litter.farrowing),
                weaning: formatDateYMD(litter.weaning),
                bornAlive: parseInt(litter.bornAlive),
                bornDeceased: parseInt(litter.bornDeceased),
                deceased: parseInt(litter.deceased),
                weaned: parseInt(litter.weaned),
                note: litter.note,
                updateSowStatus: true,
                sowId: selectedSow.value.id
            }
            await apiClient.put(`litters/${litter.id}`, litterRequest)
            console.log(litterRequest)
        }catch(e){
            console.error("Błąd przy zapisywaniu miotu", e)
            emitter.emit('alert', {message: 'Coś poszło nie tak', type: 'error'})
            return
        }
    }
    editedLitters.value = []
    emitter.emit('alert', {message: 'Zapisano pomyślnie', type: 'success', timeout: 1000})
}
</script>
