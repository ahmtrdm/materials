document.addEventListener("DOMContentLoaded", function () {
    const materials = document.querySelectorAll('.material-item');
    let activeMaterial = null; // Aktif malzemeyi takip etmek için

    materials.forEach(material => {
        material.addEventListener('click', function () {
            // Önceki tıklamada işaretlenen yöntemlerin rengini sıfırla
            if (activeMaterial) {
                const previousMethodIds = activeMaterial.getAttribute('data-methods')
                    .split(',')
                    .map(id => id.trim().replaceAll(' ', ''));
                previousMethodIds.forEach(id => {
                    const previousMethodElement = document.getElementById(`method-${id}`);
                    if (previousMethodElement) {
                        previousMethodElement.classList.remove('highlight');
                    }
                });
            }

            // Yeni tıklanan malzemeyi aktif olarak işaretle
            activeMaterial = this;

            const methodIds = this.getAttribute('data-methods')
                .split(',')
                .map(id => id.trim().replaceAll(' ', ''));

            // İlgili yöntemlere 'highlight' sınıfını ekle
            methodIds.forEach(id => {
                const methodElement = document.getElementById(`method-${id}`);
                if (methodElement) {
                    methodElement.classList.add('highlight');
                }
            });
        });
    });
});

material.addEventListener('mouseover', function () {
    const methodIds = this.getAttribute('data-methods').split(',');
    console.log('Hovered Material:', this.textContent); // Hangi malzeme seçiliyor?
    console.log('Method IDs:', methodIds); // IDs doğru geliyor mu?

    methodIds.forEach(id => {
        const methodElement = document.getElementById(`method-${id}`);
        console.log('Method Element:', methodElement); // Hangi yöntemler bulunuyor?
        if (methodElement) {
            methodElement.classList.add('highlight');
        }
    });
});