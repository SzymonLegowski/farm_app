const formatDate = (date) => {
    let d = String(date.getDate())
    let m = String(date.getMonth()+1)
    let y = String(date.getFullYear())
    if(d.length < 2) d = "0" + d
    if(m.length < 2) m = "0" + m
    let formattedDate = `${d}.${m}.${y}`
    return formattedDate
}

const statusesPL = ["Wolna", "Pokryta", "Karmiąca", "Padnięta", "Sprzedana"]
const statuses = ["FREE", "INSEMINATED", "FARROWED", "DECEASED", "SOLD"]
const monthNames = ["Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"]
const weekDays = ["Pn", "Wt", "Śr", "Czw", "Pt", "Sob", "Ndz"]

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

export {
    formatDate, 
    getStatus,
    monthNames,
    statuses,
    statusesPL,
    weekDays
}