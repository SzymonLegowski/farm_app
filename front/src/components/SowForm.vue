<template>
    <div class="modal-container" @click="dismiss"></div>
        <div class="sow-form">
            <div class="sow-form-header">
                Edycja lochy nr:
            </div>
            <div class="sow-form-body">
                <div class="form-input-header">Grupa
                    <input class="form-input"/>
                </div>
                <div class="form-input-header">Status
                    <input class="form-input"/>
                </div>
                <div class="form-input-header">Data brakowania
                    <input class="form-input"/>
                </div>
                <div class="form-input-header">Uwagi
                    <input class="form-input"/>
                </div>
                <div class="form-input-buttons">
                    <button class="nav-button" style="background-color: #11cc11;">
                        Zapisz
                    </button>
                    <button class="nav-button" style="background-color: #ddcc22" @click="dismiss">
                        Anuluj
                    </button>
                </div>
            </div>
        </div>
</template>
<script setup>
    
    const emit = defineEmits(['dismiss', 'select', 'add'])
    const dismiss = () => {
        emit('dismiss', false)
    }
    
</script>