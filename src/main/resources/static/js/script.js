document.addEventListener("DOMContentLoaded", function () {
    const materials = document.querySelectorAll('.material-item');
    const methods = document.querySelectorAll('.method-item');
    const photos = document.querySelectorAll('.photo-item');
    let activeMaterial = null;

    // Malzemeye tıklama
    materials.forEach(material => {
        material.addEventListener('click', function () {
            // Önceki vurguları temizle
            methods.forEach(method => method.classList.remove('highlight'));
            photos.forEach(photo => photo.classList.remove('highlight'));

            // Aktif malzemeyi güncelle
            activeMaterial = this;

            // İlişkili yöntemleri vurgula
            const relatedMethods = this.getAttribute('data-methods').split(',');
            relatedMethods.forEach(methodId => {
                const method = document.querySelector(`[data-method-id="${methodId.trim()}"]`);
                if (method) method.classList.add('highlight');
            });
        });
    });

    // Yönteme tıklama
    methods.forEach(method => {
        method.addEventListener('click', function () {
            // Önceki fotoğraf vurgularını temizle
            photos.forEach(photo => photo.classList.remove('highlight'));

            // İlişkili fotoğrafları vurgula
            const relatedPhotos = this.getAttribute('data-photo-ids').split(',');
            relatedPhotos.forEach(photoId => {
                const photo = document.querySelector(`[data-photo-id="${photoId.trim()}"]`);
                if (photo) photo.classList.add('highlight');
            });
        });
    });
});


