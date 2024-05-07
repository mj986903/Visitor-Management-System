const sortOption = document.getElementById("sortData");

const tableBody = document.getElementById("tableBody");
 

function getTableContent() 
{
    const tableContent = [];
    const tableBodyChildren = tableBody.children;
    for(let i = 0; i < tableBodyChildren.length; i++)
    {
        const tableRow = tableBodyChildren[i];
    
        const visitingID = (tableRow.children)[1].innerText;
        const fName = (tableRow.children)[2].innerText;
        const lName = (tableRow.children)[3].innerText;
        const mobNum = (tableRow.children)[4].innerText;
        const aadharNum = (tableRow.children)[5].innerText;
        const purpose = (tableRow.children)[6].innerText;
        const entryDate = (tableRow.children)[7].innerText;
        let exitDate = (tableRow.children)[8].innerText;
        const editBtn = (tableRow.children)[9].innerHTML;
        const exitBtn = (tableRow.children)[10].innerHTML;

        if(exitDate == "-----") // beacause I made changes on backend that null will become -----
        {
            exitDate = "null";
        }

        const rowObj = {
            visitingID: visitingID,
            fName: fName, 
            lName: lName, 
            mobNum: mobNum, 
            aadharNum: aadharNum, 
            purpose: purpose,
            entryDate: entryDate, 
            exitDate: exitDate, 
            editBtn : editBtn, 
            exitBtn: exitBtn       
        };

        tableContent.push(rowObj);
    }
    return tableContent;
}

function sortData(sortingType) 
{   
    const tableContent = getTableContent();
    if(sortingType == "newest")
    {   
        tableContent.sort((obj1, obj2)=>
        {
            return new Date(obj2.entryDate) - new Date(obj1.entryDate);
        });
    }
    else if(sortingType == "oldest")
    {   
        tableContent.sort((obj1, obj2)=>
        {
            return new Date(obj1.entryDate) - new Date(obj2.entryDate)
        });
    }
    else
    {
        tableContent.sort((obj1, obj2)=>
        {	
            if(obj2.exitDate != "null" && obj1.exitDate != "null")
            {
                return new Date(obj2.entryDate) - new Date(obj1.entryDate);
            }
			if(obj2.exitDate == "null")
			{
				return 1;
			}
			if(obj1.exitDate == "null")
			{
				return -1;
			}
            
        });
        
        tableContent.sort((obj1, obj2)=>
        {	
            if(obj2.exitDate == "null" && obj1.exitDate == "null")
            {
                return new Date(obj2.entryDate) - new Date(obj1.entryDate);
            }
			return 0;
        });
    }
    return tableContent;
}

function setTableHTML(tableContent) 
{   
    let tableBodyHTML = "";
    for(let i = 0; i < tableContent.length; i++)
    {   
        if(tableContent[i].exitDate == "null")
        {
            tableContent[i].exitDate = "-----"; // I have print -----
        }

        tableBodyHTML += `<tr>
        <th scope='row'>${i + 1}</th>
        <td>${tableContent[i].visitingID}</td>
        <td>${tableContent[i].fName}</td>
        <td>${tableContent[i].lName}</td>
        <td>${tableContent[i].mobNum}</td>
        <td>${tableContent[i].aadharNum}</td>
        <td>${tableContent[i].purpose}</td>
        <td>${tableContent[i].entryDate}</td>
        <td>${tableContent[i].exitDate}</td>
        <td>${tableContent[i].editBtn}</td>
        <td>${tableContent[i].exitBtn}</td>`
    }
    
    tableBody.innerHTML = tableBodyHTML;
}

sortOption.addEventListener("change", ()=>
{
    const sortOptionVal = sortOption.options[sortOption.selectedIndex].value;

    const tableContent = sortData(sortOptionVal);
    setTableHTML(tableContent);

    // when data is rearranged, HTML is again added to table body so eventListner on new buttons is not added
    addEventListenerToExitTimeBtns();
    addEventListnerToEditBtns(); 
});