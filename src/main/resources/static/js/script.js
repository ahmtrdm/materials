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

function handleSelection(material, method = null) {
    const endpoint = method ? "/filter-photos" : "/filter-methods";
    const params = method
        ? `material=${encodeURIComponent(material)}&method=${encodeURIComponent(method)}`
        : `material=${encodeURIComponent(material)}`;

    const xhr = new XMLHttpRequest();
    xhr.open("GET", `${endpoint}?${params}`);
    xhr.onload = function () {
        if (xhr.status === 200) {
            if (method) {
                // Fotoğraf URL'lerini işleyip göstermek
                const photoUrls = JSON.parse(xhr.responseText); // Yanıt bir JSON listesi
                const photosContainer = document.querySelector('#photos');
                photosContainer.innerHTML = ""; // Eski içerikleri temizle
                photoUrls.forEach(url => {
                    const img = document.createElement('img');
                    img.src = url;
                    img.alt = "Photo";
                    img.style.width = "150px"; // Fotoğraf boyutunu belirlemek için
                    img.style.margin = "10px";
                    photosContainer.appendChild(img);
                });
            } else {
                // Metotları güncellemek
                document.querySelector('#methods').innerHTML = xhr.responseText;
            }
        } else {
            console.error(`Failed to load ${method ? "photos" : "methods"}:`, xhr.status, xhr.statusText);
        }
    };
    xhr.onerror = function () {
        console.error(`An error occurred while loading ${method ? "photos" : "methods"}.`);
    };
    xhr.send();
}



