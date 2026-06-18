// Configuration - Relative since frontend is served from the same server/port
const API_BASE_URL = '/api';

// Auth State Management
function getToken() {
    return localStorage.getItem('token');
}

// Auth state management functions
function setToken(token) {
    localStorage.setItem('token', token);
}

// Auth state helper functions
function getUser() {
    return localStorage.getItem('username');
}

function setUser(username) {
    localStorage.setItem('username', username);
}

function getRoles() {
    try {
        return JSON.parse(localStorage.getItem('roles') || '[]');
    } catch (e) {
        return [];
    }
}

function setRoles(roles) {
    localStorage.setItem('roles', JSON.stringify(roles));
}

function isAuthenticated() {
    return !!getToken();
}

function isAdmin() {
    return getRoles().includes('ROLE_ADMIN');
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('roles');
    showToast('Logged out successfully', 'success');
    setTimeout(() => {
        window.location.href = 'index.html';
    }, 1000);
}

// Redirect helpers
function checkAuth() {
    if (!isAuthenticated()) {
        window.location.href = 'login.html';
    }
}

// Admin Auth Helper
function checkAdminAuth() {
    if (!isAuthenticated() || !isAdmin()) {
        showToast('Access Denied. Admins only.', 'error');
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 1500);
    }
}

// Base Fetch request utility with auth header
async function apiRequest(endpoint, options = {}) {
    const token = getToken();
    const headers = {
        'Content-Type': 'application/json',
        ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
        ...(options.headers || {})
    };
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers
    });
    
    const text = await response.text();
    let data = {};
    if (text) {
        try {
            data = JSON.parse(text);
        } catch (e) {
            data = { message: text };
        }
    }
    
    if (!response.ok) {
        throw new Error(data.message || 'Something went wrong');
    }
    return data;
}

// Package Selection Helper
function selectPackage(packageName, price, country) {
    sessionStorage.setItem('selectedPackage', JSON.stringify({ name: packageName, price: price, country: country }));
    if (!isAuthenticated()) {
        showToast('Please login or signup to book this package', 'info');
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 1500);
    } else {
        window.location.href = 'booking.html';
    }
}

// Social Login Implementation (Real Google / GitHub oauth)
async function handleSocialLogin(provider) {
    if (provider === 'google') {
        await handleGoogleLoginReal();
        return;
    }
    if (provider === 'github') {
        // Redirect to GitHub authorization page
        const githubClientId = '0v23liXMKsjKup6kagcg';
        const redirectUrl = `https://github.com/login/oauth/authorize?client_id=${githubClientId}&scope=read:user%20user:email`;
        window.location.href = redirectUrl;
        return;
    }
}

async function handleGoogleLoginReal() {
    if (typeof google === 'undefined') {
        showToast('Google Sign-In library is loading. Please try again.', 'info');
        return;
    }
    
    showToast('Opening Google Sign-In...', 'info');
    
    const tokenClient = google.accounts.oauth2.initTokenClient({
        client_id: '1058887688018-ff0n1avvh38nn79qvflp8dq5o7vphh4q.apps.googleusercontent.com',
        scope: 'openid email profile',
        callback: async (response) => {
            if (response.error) {
                showToast(`Google login failed: ${response.error}`, 'error');
                return;
            }
            
            try {
                showToast('Authenticating with Tourism Hub...', 'info');
                const res = await apiRequest('/auth/google', {
                    method: 'POST',
                    body: JSON.stringify({ accessToken: response.access_token })
                });
                
                if (res && res.data) {
                    setToken(res.data.accessToken);
                    setUser(res.data.username);
                    setRoles(res.data.roles || []);
                    showToast('Google login successful!', 'success');
                    setTimeout(() => {
                        if (sessionStorage.getItem('selectedPackage')) {
                            window.location.href = 'booking.html';
                        } else {
                            window.location.href = 'dashboard.html';
                        }
                    }, 1000);
                }
            } catch (err) {
                showToast(`Google Authentication failed: ${err.message}`, 'error');
            }
        }
    });
    
    tokenClient.requestAccessToken();
}

async function handleGitHubCallback(code) {
    try {
        showToast('Exchanging GitHub credentials...', 'info');
        const res = await apiRequest('/auth/github', {
            method: 'POST',
            body: JSON.stringify({ code: code })
        });
        
        if (res && res.data) {
            setToken(res.data.accessToken);
            setUser(res.data.username);
            setRoles(res.data.roles || []);
            showToast('GitHub login successful!', 'success');
            // Remove code from URL
            window.history.replaceState({}, document.title, window.location.pathname);
            setTimeout(() => {
                if (sessionStorage.getItem('selectedPackage')) {
                    window.location.href = 'booking.html';
                } else {
                    window.location.href = 'dashboard.html';
                }
            }, 1000);
        }
    } catch (err) {
        showToast(`GitHub login failed: ${err.message}`, 'error');
        window.history.replaceState({}, document.title, window.location.pathname);
    }
}

// Dynamic Navbar Injection
document.addEventListener('DOMContentLoaded', () => {
    updateNavbar();
    
    // Check if returning from GitHub authorization redirect
    const urlParams = new URLSearchParams(window.location.search);
    const githubCode = urlParams.get('code');
    if (githubCode) {
        handleGitHubCallback(githubCode);
    }
});

function updateNavbar() {
    const header = document.querySelector('header.fixed');
    if (!header) return;

    // Find or construct the Nav Container
    let navDiv = header.querySelector('nav');
    if (navDiv) {
        navDiv.innerHTML = `
            <a class="font-label-caps text-label-caps hover:text-primary transition-colors duration-200" href="index.html">Home</a>
            <a class="font-label-caps text-label-caps hover:text-primary transition-colors duration-200" href="packages.html">Tour Packages</a>
            ${isAuthenticated() ? `<a class="font-label-caps text-label-caps hover:text-primary transition-colors duration-200" href="dashboard.html">My Bookings</a>` : ''}
            ${isAdmin() ? `<a class="font-label-caps text-label-caps hover:text-primary transition-colors duration-200 text-secondary" href="admin.html">Admin Panel</a>` : ''}
        `;
    }

    // Find Auth actions container
    let authContainer = header.querySelector('#auth-buttons-container') || header.querySelector('div.flex.items-center.gap-md') || header.querySelector('.flex.items-center.gap-lg') || header.querySelector('div:last-child');
    if (authContainer) {
        if (isAuthenticated()) {
            const username = getUser() || 'User';
            authContainer.innerHTML = `
                <div class="flex items-center gap-md">
                    <span class="font-body-sm text-text-muted hidden sm:inline">Hello, <strong class="text-on-surface">${username}</strong></span>
                    ${isAdmin() ? 
                        `<a href="admin.html" class="px-md py-sm rounded-lg bg-secondary/10 text-secondary hover:bg-secondary/20 font-label-caps text-label-caps transition-all">Admin</a>` : 
                        `<a href="dashboard.html" class="px-md py-sm rounded-lg bg-primary/10 text-primary hover:bg-primary/20 font-label-caps text-label-caps transition-all">Dashboard</a>`
                    }
                    <button onclick="logout()" class="border border-outline-variant/30 text-text-muted hover:text-error hover:border-error/30 px-md py-sm rounded-lg font-label-caps text-label-caps transition-all">Logout</button>
                </div>
            `;
        } else {
            authContainer.innerHTML = `
                <a href="login.html" class="font-label-caps text-label-caps text-on-surface-variant px-md py-sm hover:text-primary transition-colors">Login</a>
                <a href="login.html?register=true" class="bg-gradient-to-r from-ocean-gradient-start to-ocean-gradient-end font-label-caps text-label-caps text-white px-lg py-sm rounded-lg shadow-sm hover:scale-[1.02] active:scale-95 transition-all">Signup</a>
            `;
        }
    }
}

// Search redirection helper
function handleSearchRedirect(query) {
    window.location.href = `packages.html?search=${encodeURIComponent(query)}`;
}


// Toast Alert notification helper
function showToast(message, type = 'info') {
    // Remove existing toast container if any
    let toastContainer = document.getElementById('toast-container');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toast-container';
        toastContainer.className = 'fixed bottom-5 right-5 z-50 flex flex-col gap-sm max-w-sm';
        document.body.appendChild(toastContainer);
    }

    const toast = document.createElement('div');
    const colorClass = type === 'success' ? 'bg-secondary-container text-on-secondary-container border-secondary' :
                       type === 'error' ? 'bg-error-container text-on-error-container border-error' :
                       'bg-primary-container text-on-primary-container border-primary';
    
    toast.className = `p-md rounded-xl border-l-4 shadow-lg flex items-center gap-md transform translate-y-2 opacity-0 transition-all duration-300 ${colorClass}`;
    
    const icon = type === 'success' ? 'check_circle' : type === 'error' ? 'error' : 'info';
    toast.innerHTML = `
        <span class="material-symbols-outlined">${icon}</span>
        <span class="font-body-sm font-semibold">${message}</span>
    `;

    toastContainer.appendChild(toast);

    // Trigger animation
    setTimeout(() => {
        toast.classList.remove('translate-y-2', 'opacity-0');
    }, 10);

    // Remove toast after delay
    setTimeout(() => {
        toast.classList.add('translate-y-2', 'opacity-0');
        setTimeout(() => {
            toast.remove();
        }, 300);
    }, 3500);
}
