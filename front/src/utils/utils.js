const formatDateDMY = (date) => {
    let d = String(date.getDate())
    let m = String(date.getMonth()+1)
    let y = String(date.getFullYear())
    if(d.length < 2) d = "0" + d
    if(m.length < 2) m = "0" + m
    let formattedDate = `${d}.${m}.${y}`
    return formattedDate
}

const formatDateYMD = (date) => {
    if(date == null) return null
    if(date.length < 1) return null
    const data = date.split(".")
    return `${data[2]}-${data[1]}-${data[0]}`
}

const formatStringDateDMY = (date) => {
    if(date == null) return null
    if(date.length < 1) return null
    const data = date.split("-")
    return `${data[2]}.${data[1]}.${data[0]}`
}

const calculateDays = (selectedDate) => {
        let days = []
        let helperDate = new Date(selectedDate)
        let day
        helperDate.setDate(1)
        let dayOfWeek = helperDate.getDay()
        if(dayOfWeek===0) dayOfWeek=7
        for(let i = 0; i < dayOfWeek-1; i++){
            let prevMonth = new Date(helperDate)
            prevMonth.setDate(prevMonth.getDate() - dayOfWeek + i + 1)
            day = prevMonth.getDate()
            days.push({d: day, m:-1})
        }
        while (helperDate.getMonth() === selectedDate.getMonth()){
            day = helperDate.getDate()
            days.push({d: day, m:0})
            helperDate.setDate(day + 1)
        }
        dayOfWeek = helperDate.getDay()
        if(dayOfWeek !== 1)
        for (let i = dayOfWeek-1; i<7; i++){
            day = helperDate.getDate()
            helperDate.setDate(day + 1)
            days.push({d: day, m:1})
        }
        return days
    }

const statusesPL = ["Wolna", "Pokryta", "Prośna", "Karmiąca", "Padnięta", "Sprzedana"]
const statuses = ["FREE", "INSEMINATED", "GRAVID", "FARROWED", "DECEASED", "SOLD"]
const monthNames = ["Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"]
const weekDays = ["Pn", "Wt", "Śr", "Czw", "Pt", "Sob", "Ndz"]
const eventTypes = ["Krycie", "Proszenie", "Odsadzanie", "P. Proszenie"]
const eventTypesSelect = ["Krycie", "Proszenie", "Odsadzanie"]

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
        case statuses[5]:
            return statusesPL[5]
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
        case statusesPL[5]:
            return statuses[5]       
        }
}

export {
    formatStringDateDMY,
    formatDateDMY, 
    formatDateYMD,
    calculateDays,
    getStatus,
    monthNames,
    statuses,
    statusesPL,
    weekDays,
    eventTypes,
    eventTypesSelect
}