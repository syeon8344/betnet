let sel_year = 0
getYearTable()

function getYearTable(){
    console.log("getYearTable");
    
    let year = document.querySelector(".year").value
    if (year == sel_year){
        return
    } else {
        sel_year = year
    }
    $.ajax({
        async: false,
        method: "GET",
        data: {year: year},
        url: "http://127.0.0.1:5000/getranktable",
        success: (resp) => {
            response = JSON.parse(resp)
            console.log(response);
            let html = ""
            for (let i = 0; i < response.length; i++){
                console.log(i);
                
                html += `<tr>`
                for (const key in response[i]){
                    console.log(key);
                    html += `<td>${response[i][key]}</td>`
                }
                html += `</tr>`
            }
            document.querySelector(".historyTbody").innerHTML = html
        }
    })
}