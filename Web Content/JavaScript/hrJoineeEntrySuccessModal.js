const entryEmailModal = new bootstrap.Modal(document.getElementById('entryEmailModal'), 
{
	
}); 
entryEmailModal.toggle();

document.getElementById('entryEmailModalBtn').addEventListener('click', ()=> 
{
    entryEmailModal.hide();
});