const crossBtns = document.getElementsByClassName("close");

for (let i = 0; i < crossBtns.length; i++) {
    crossBtns[i].addEventListener("click", ()=>
    {
        formBox.style.display = "none";
        exitTimeForm.style.display = "none";
        editForm.style.display = "none";
        container.style.display = "flex";
        
    });
}