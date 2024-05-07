const exitEntryModal = new bootstrap.Modal(document.getElementById('exitEntryModal'), {}); 
exitEntryModal.toggle();

document.getElementById('exitModalBtn').addEventListener('click', ()=> 
{
    exitEntryModal.hide();
});