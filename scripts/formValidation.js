// Example form validation script
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('#registerForm');
    const nameInput = form.querySelector('input[name="name"]');
    const emailInput = form.querySelector('input[name="email"]');
    const passwordInput = form.querySelector('input[name="password"]');

    form.addEventListener('submit', (event) => {
    let valid = true;

    if (nameInput.value.trim() === '') {
        valid = false;
        alert('Name is required');
    }

    if (!validateEmail(emailInput.value)) {
        valid = false;
        alert('Invalid email address');
    }

    if (passwordInput.value.length < 6) {
        valid = false;
        alert('Password must be at least 6 characters long');
    }

    if (!valid) {
        event.preventDefault();
    }
    });

    function validateEmail(email) {
      // Simple email validation regex
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
    }
});
