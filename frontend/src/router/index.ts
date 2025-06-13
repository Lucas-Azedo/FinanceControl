// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'

// Auth
import SignIn from '../views/auth/SignIn.vue'
import SignUp from '../views/auth/SignUp.vue'

// Dashboard
import Dashboard from '../views/dashboard/Dashboard.vue'

// Admin
import UserManagement from '../views/admin/UserManagement.vue'

// Profile
import Profile from '../views/profile/Profile.vue'

// Planner
import Planner from '../views/planner/Planner.vue'

import { useAuth } from '../composables/useAuth'

const routes = [
  {
    path: '/',
    redirect: '/signin'
  },
  {
    path: '/signin',
    component: SignIn,
    meta: { layout: 'PublicLayout' }
  },
  {
    path: '/signup',
    component: SignUp,
    meta: { layout: 'PublicLayout' }
  },
  {
    path: '/dashboard',
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  {
    path: '/planner',
    component: Planner,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/profile',
    component: Profile,
    meta: { requiresAuth: true }
  },
  {
    path: '/admin/users',
    component: UserManagement,
    meta: { requiresAuth: true, requiresAdmin: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const { isAuthenticated } = useAuth();

  if(to.meta.requiresAuth && !isAuthenticated()){
    next('/signin')
  }else{
    next();
  }

})

export default router
