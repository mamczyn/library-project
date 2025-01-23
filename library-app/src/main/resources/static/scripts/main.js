document.addEventListener('DOMContentLoaded', function () {
    const searchBar = document.getElementById('search-bar');
    
    searchBar.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            const query = searchBar.value.trim();
            if (query) {
                window.location.href = `?search=${encodeURIComponent(query)}`;
            }
        }
    });
});