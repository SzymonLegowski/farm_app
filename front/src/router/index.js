import { createRouter, createWebHistory } from 'vue-router'

import Dashboard from '@/views/Dashboard.vue'
import Sow from '@/views/Sow.vue'
import Calendar from '@/views/Calendar.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/dashboard', component: Dashboard},
    { path: '/sow', component: Sow},
    { path: '/calendar', component: Calendar}

  ],
})

export default router
