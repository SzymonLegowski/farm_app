<template>
    <div class="menu">
        <div class="menu-arrow">
            &rarr;
        </div>
        <div class="menu-header">
            Menu
        </div>
        <div class="button-container">
            <button class="button" @click="toDashboard">
                Strona główna
            </button>
            <button class="button" @click="toCalendar">
                Kalendarz
            </button>
            <button class="button" @click="toSowDetails">
                Karta lochy
            </button>
        </div>
    </div>
</template>
<script setup>
    import { useRouter } from 'vue-router';
    const router = useRouter()
    const toDashboard = () => {
        router.push('/dashboard')
    }
    const toCalendar = () => {
        router.push('/calendar')
    }
    const toSowDetails = () => {
        router.push('/sowDetails')
    }
</script>
<style scoped>
.menu{
    display: flex;
    flex-direction: column;
    max-width: 250px;
    gap: 11px;
    border-right: 2px solid black;
    padding: 10px;
    margin-top: 5px;
    min-height: 96vh;
    position: absolute;
    left: -210px;
}
.menu:hover{
    left: 0;
}
.button-container{
    display: flex;
    flex-direction: column;
    gap: 11px;
    visibility: hidden;
}
.menu:hover > .button-container{
    visibility: visible;
}
.menu:hover > .menu-header{
    visibility: visible;
}
.menu:hover > .menu-arrow{
    visibility: hidden;
}
.menu-header{
    font-size: 2em;
    text-transform: uppercase;
    font-family: 'Courier New', Courier, monospace;
    font-weight: bold;
    padding: 10px;
    letter-spacing: 2px;
    color:#000;
    text-align: center;
    border-bottom: 2px solid #000;
    visibility: hidden;
}
.menu-arrow{
    position: absolute;
    left: 205px;
    text-align: center;
    font-size: 25px;
    padding: 10px;
}

.button {
  padding: 1.3em 3em;
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 600;
  font-family: 'Courier New', Courier, monospace;
  color: #000;
  background-color: #cfc092;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease 0s;
  cursor: pointer;
  outline: none;
}

.button:hover {
  background-color: #c48423;
  box-shadow: 0px 15px 20px rgba(229, 183, 46, 0.4);
  color: #fff;
  transform: translateY(-7px);
}

.button:active {
  transform: translateY(-1px);
}



</style>