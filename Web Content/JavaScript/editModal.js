const editEntryModal = new bootstrap.Modal(document.getElementById('editEntryModal'), {}); 
editEntryModal.toggle();

document.getElementById('editModalBtn').addEventListener('click', ()=> 
{
    editEntryModal.hide();
});