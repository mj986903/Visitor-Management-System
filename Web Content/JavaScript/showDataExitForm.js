const formBox = document.getElementById("formBox");
const exitTimeForm = document.getElementById("exitTimeForm");
const editForm = document.getElementById("editForm");

const container = document.getElementById("container");
const formTitle = document.getElementById("editExitFormTitle");

const exitTimeBtns = document.getElementsByClassName("exitTimeBtns");

// intial settings
formBox.style.display = "none";
exitTimeForm.style.display = "none";
editForm.style.display = "none";


function updateCSS() 
{
    formBox.style.display = "flex";
    exitTimeForm.style.display = "flex";
    container.style.display = "none";
    formTitle.innerHTML = "<h2>Confirm Exit</h2>";
}


function setFormContent(e) 
{   
    const firstName = document.getElementById("firstNameExitForm");
    const lastName = document.getElementById("lastNameExitForm");
    const mobNum = document.getElementById("mobNumExitForm");
    const aadharNum = document.getElementById("aadharNumExitForm");

    const row = ((e.target).parentElement).parentElement;
    const rowChildren = row.children;

    exitTimeForm.children[1].value = rowChildren[1].innerText; // setting visiting id in form heading
    

    firstName.value = rowChildren[2].innerText;
    lastName.value = rowChildren[3].innerText;
    mobNum.value = rowChildren[4].innerText;
    aadharNum.value = rowChildren[5].innerText;
	
	 // value is not changable
	exitTimeForm.children[1].setAttribute("readonly", "");
    firstName.setAttribute("readonly", "");
    lastName.setAttribute("readonly", "");
    mobNum.setAttribute("readonly", "");
    aadharNum.setAttribute("readonly", "");
}

function addEventListenerToExitTimeBtns()
{	
    for(let i = 0; i < exitTimeBtns.length; i++)
    {
        exitTimeBtns[i].addEventListener("click", (e)=>
        {
            updateCSS();
            setFormContent(e);
        });
    }
}

addEventListenerToExitTimeBtns();