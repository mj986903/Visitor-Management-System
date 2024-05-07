// using same modal---entryCreated modal
console.log("ll");
const otpErrorModal = new bootstrap.Modal(document.getElementById('entryCreatedModal'), {}); 

document.getElementById('modalContentDiv').innerHTML = `<h3 style='color: red;'>Oops!</h3> <hr> <p>Something Went Wrong!<p/>`;
otpErrorModal.toggle();

document.getElementById('entryCreatedModalBtn').addEventListener('click', ()=> 
{
    otpErrorModal.hide();
});