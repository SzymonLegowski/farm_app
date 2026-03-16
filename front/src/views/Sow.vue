<template>
    <SowSelectGrid 
        v-if="showSelectGrid" 
        :show=showSelectGrid 
        :sows="sows"    
        @dismiss="showSelectGrid = $event" 
        @select="handleSowSelect($event)"
    />
    <SowForm
        v-if="showSowForm"
        :show="showSowForm"
        @dismiss="showSowForm = $event"
    />
    <div class="top-bar">
        <button class="button" @click="viewSelectGrid">
            Wybierz lochę
        </button>
        <div class="top-bar-header">
            Locha nr {{ selectedSow.number }}
        </div>
        <div class="top-bar-header">
            Grupa {{  selectedSow.group }}
        </div>
        <button class="button" @click="viewSowForm">
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
            <tr v-for="(litter, index) of sowData">
                <td>{{ index+1 }}</td>
                <td v-for="n in maxInseminationCount">{{ litter.inseminations[n-1] }}</td>
                <td>{{ litter.predictedFarrowing }}</td>
                <td>{{ litter.farrowing }}</td>
                <td>{{ litter.weaning }}</td>
                <td>{{ litter.bornAlive }}</td>
                <td>{{ litter.bornDeceased }}</td>
                <td>{{ litter.deceased }}</td>
                <td style="border-right: 0px;">{{ litter.weaned }}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>
<script setup>
import apiClient from '@/api/apiClient'
import SowSelectGrid from '@/components/SowSelectGrid.vue'
import SowForm from '@/components/SowForm.vue'
import { ref } from 'vue'
import emitter from '@/api/eventBus'

const showSelectGrid = ref(false)
const showSowForm = ref(false)
const sows = ref()
const selectedSow = ref({})
const sowData = ref()
const maxInseminationCount = ref(3)
const isDataFetched = ref(false)

emitter.emit('alert', {message: 'Pobieranie danych...', type: 'info'})

const handleSowSelect = (sow) => {
    selectedSow.value = sow
    console.log(sow)
    apiClient.get(`litters/sow/${sow.id}`)
        .then((response) => {
            console.log(response.data)
            sowData.value = response.data
            for(let litter of sowData.value){
                if(litter.inseminations.length > maxInseminationCount.value){
                    maxInseminationCount.value = litter.inseminations.length
                }
            }
        })
}

apiClient.get('sows', {timeout: 3000})
    .then((response) => {
        console.log(response.data)
        emitter.emit('alert', {message: 'Dane załadowane pomyślnie', type: 'success', timeout: 500})
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
    else{ emitter.emit('alert', {message: 'Brak danych z api', type: 'error', timeout: 1000}) }
}

const viewSowForm = () => {
    console.log("clicked")
    showSowForm.value = true
}
</script>
