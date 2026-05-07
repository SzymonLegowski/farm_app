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
            <tr v-for="(litter, index) of litters" class="sow-card-row">
                <td>{{ index+1 }}</td>
                <td v-for="n in maxInseminationCount">
                <template v-if="litter.inseminations[n-1]">
                    <input class="sow-card-insemination" v-model="litter.inseminations[n-1].date" @input="onChange(litter.id)">
                    <input class="sow-card-insemination" v-model="litter.inseminations[n-1].note" @input="onChange(litter.id)">
                </template>
                </td>
                <!-- <td v-for="n in maxInseminationCount"> {{ litter.inseminations[n-1]?.date }} </td> -->
                <td style="background-color: rgba(125, 125, 125, 0.2); user-select: none;" >{{ litter.predictedFarrowing }}</td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.farrowing"></td>
                <td><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.weaning"></td>
                <td class="sow-card-litter" ><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.bornAlive"></td>
                <td class="sow-card-litter" ><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.bornDeceased"></td>
                <td class="sow-card-litter" ><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.deceased"></td>
                <td class="sow-card-litter" ><input @input="onChange(litter.id)" class="sow-card-input" v-model="litter.weaned"></td>
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
const litters = ref()
const maxInseminationCount = ref(0)
const isDataFetched = ref(false)
const editedLitters = ref([])

// emitter.emit('alert', {message: 'Pobieranie danych...', type: 'info'})

const handleSowSelect = (sow) => {
    maxInseminationCount.value = 1
    litters.value = {}
    editedLitters.value = []
    selectedSow.value = sow
    apiClient.get(`sows/history/${sow.id}`)
        .then((response) => {
            console.log(response.data.maxInsCnt)
            litters.value = response.data.litters
            let maxInsCnt = response.data.maxInsCnt
            if(maxInsCnt > 0)
                maxInseminationCount.value = response.data.maxInsCnt
        }).catch((e) => {
            console.log(e)
        })
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
    if(editedLitters.value.find(l => l.litterId === litterId)) return
    editedLitters.value.push(litterId)
    console.log(editedLitters.value)
    
}

const save = async () =>{
    emitter.emit('alert', {message: 'Zapisywanie zmian...', type: 'info'})
    let maxId = Math.max(...litters.value.map(l => l.id))
    for(let litterId of editedLitters.value){
        try{       
            let litter = litters.value.find(l => l.id == litterId)
            console.log(litter)
            let isLatest = false
            if(litter.id === maxId){isLatest = true}
            let litterRequest = {
                litterDto : {
                id: litter.id,
                inseminations: litter.inseminations,
                farrowing: litter.farrowing,
                weaning: litter.weaning,
                bornAlive: parseInt(litter.bornAlive),
                bornDeceased: parseInt(litter.bornDeceased),
                deceased: parseInt(litter.deceased),
                weaned: parseInt(litter.weaned),
                note: litter.note },
                updateSowStatus: isLatest,
            }
            await apiClient.put(`litters/${litter.id}`, litterRequest)
            console.log("litter request: ", litterRequest)
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
