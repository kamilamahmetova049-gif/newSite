document.addEventListener('DOMContentLoaded', () => {
    const chips = document.querySelectorAll('.chip');
    const products = document.querySelectorAll('.product-card');

    chips.forEach((chip) => {
        chip.addEventListener('click', () => {
            const filter = chip.getAttribute('data-filter');
            chips.forEach((c) => {
                c.classList.remove('active');
                c.setAttribute('aria-pressed', 'false');
            });
            chip.classList.add('active');
            chip.setAttribute('aria-pressed', 'true');

            products.forEach((card) => {
                const category = card.getAttribute('data-category');
                if (!category || filter === 'all' || category === filter) {
                    card.classList.remove('hidden');
                } else {
                    card.classList.add('hidden');
                }
            });
        });
    });

    document.querySelectorAll('[data-action="variant"]').forEach((button) => {
        button.addEventListener('click', () => {
            const target = button.getAttribute('data-target');
            if (target) {
                window.location.href = target;
            }
        });
    });
});

