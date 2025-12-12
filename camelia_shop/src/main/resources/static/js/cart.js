// Cart functionality
document.addEventListener('DOMContentLoaded', function() {
    updateCartCount();
    
    // Add to cart buttons
    document.querySelectorAll('.add-to-cart').forEach(button => {
        button.addEventListener('click', function() {
            const productId = this.getAttribute('data-product-id');
            addToCart(productId);
        });
    });
});

function addToCart(productId) {
    fetch('/cart/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `productId=${productId}&quantity=1`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            updateCartCount();
            showToast(data.message || 'Товар добавлен в корзину');
        } else {
            showToast(data.message || 'Ошибка при добавлении товара', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showToast('Ошибка при добавлении товара', 'error');
    });
}

function updateQuantity(button, change) {
    const cartItem = button.closest('.cart-item');
    const itemId = cartItem.getAttribute('data-item-id');
    const quantitySpan = cartItem.querySelector('.quantity');
    let currentQuantity = parseInt(quantitySpan.textContent);
    let newQuantity = currentQuantity + change;
    
    if (newQuantity < 1) {
        removeItem(button.closest('.cart-item').querySelector('.remove-btn'));
        return;
    }
    
    fetch('/cart/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `itemId=${itemId}&quantity=${newQuantity}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            quantitySpan.textContent = newQuantity;
            updateCartTotals(data);
            showToast('Количество обновлено');
        } else {
            showToast(data.message || 'Ошибка при обновлении', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showToast('Ошибка при обновлении', 'error');
    });
}

function removeItem(button) {
    const itemId = button.getAttribute('data-item-id');
    const cartItem = button.closest('.cart-item');
    
    fetch('/cart/remove', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `itemId=${itemId}`
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            cartItem.style.transition = 'opacity 0.3s';
            cartItem.style.opacity = '0';
            setTimeout(() => {
                cartItem.remove();
                updateCartTotals(data);
                if (document.querySelectorAll('.cart-item').length === 0) {
                    location.reload();
                }
            }, 300);
            showToast(data.message || 'Товар удален из корзины');
        } else {
            showToast(data.message || 'Ошибка при удалении', 'error');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        showToast('Ошибка при удалении', 'error');
    });
}

function updateCartCount() {
    fetch('/cart/count')
        .then(response => response.json())
        .then(data => {
            const cartCount = document.getElementById('cart-count');
            if (cartCount) {
                cartCount.textContent = data.count || 0;
            }
        })
        .catch(error => console.error('Error updating cart count:', error));
}

function updateCartTotals(data) {
    if (data.total !== undefined) {
        const totalElement = document.getElementById('cart-total');
        if (totalElement) {
            totalElement.textContent = formatPrice(data.total) + ' ₸';
        }
    }
    
    if (data.cartCount !== undefined) {
        const countElement = document.getElementById('cart-count');
        if (countElement) {
            countElement.textContent = data.cartCount;
        }
    }
    
    if (data.totalItems !== undefined) {
        const totalItemsElement = document.getElementById('total-items');
        if (totalItemsElement) {
            totalItemsElement.textContent = data.totalItems;
        }
    }
}

function formatPrice(price) {
    return new Intl.NumberFormat('ru-RU').format(Math.round(price));
}

function showToast(message, type = 'success') {
    // Remove existing toast
    const existingToast = document.querySelector('.toast');
    if (existingToast) {
        existingToast.remove();
    }
    
    // Create new toast
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);
    
    // Show toast
    setTimeout(() => {
        toast.classList.add('visible');
    }, 10);
    
    // Hide toast after 3 seconds
    setTimeout(() => {
        toast.classList.remove('visible');
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

