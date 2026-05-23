import apiClient from "@/api/apiClient";
import emitter from "@/api/eventBus";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useSowsStore = defineStore('sows', () => {
    const sows = ref([])

    const fetchSows = () => {
        apiClient.get('sows', {timeout: 3000})
            .then((response) => {
                sows.value = response.data
                console.log("sowsvalue: ", sows.value)
            })
            .catch((err) => {
                console.log(err)
                emitter.emit('alert', {message: 'Błąd podczas pobierania danych', type: 'error'})
            })
    }

    return {sows, fetchSows}
})