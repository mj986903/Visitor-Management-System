const entryCreatedModal = new bootstrap.Modal(document.getElementById('entryCreatedModal'), {}); 
entryCreatedModal.toggle();

document.getElementById('entryCreatedModalBtn').addEventListener('click', ()=> 
{
    entryCreatedModal.hide();
});