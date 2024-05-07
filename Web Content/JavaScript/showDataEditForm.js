
// check also showDataExitForm.js


const editBtns = document.getElementsByClassName("editBtns");
//console.log(editBtns);




// intial settings
formBox.style.display = "none";
editForm.style.display = "none";


function updateCSSEditForm() 
{
    formBox.style.display = "flex";
    editForm.style.display = "flex";
    container.style.display = "none";
    formTitle.innerHTML = `<h3>Confirm Changes</h3>`; // variable declared in showDataExitForm.js file
}

function setFormContentEditForm(e) 
{
    const firstName = document.getElementById("editFormFirstName");
    const lastName = document.getElementById("editFormLastName");
    const purpose = document.getElementById("editFormPurpose");
    const aadharNum = document.getElementById("editFormAadharNum");

    const row = ((e.target).parentElement).parentElement;
    const rowChildren = row.children;

    editForm.children[1].value = rowChildren[1].innerText; // setting visiting id in form heading
    editForm.children[1].setAttribute("readonly", "");

    firstName.value = rowChildren[2].innerText;
    lastName.value = rowChildren[3].innerText;
    aadharNum.value = rowChildren[5].innerText;
    purpose.value = rowChildren[6].innerText;
}

function addEventListnerToEditBtns() 
{
    for(let i = 0; i < editBtns.length; i++)
    {
        editBtns[i].addEventListener("click", (e)=>
        {	
            updateCSSEditForm();
            setFormContentEditForm(e);
        });
    }    
}

addEventListnerToEditBtns();