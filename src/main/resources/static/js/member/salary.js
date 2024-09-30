
function salary(){ console.log('salary()');
    let name=document.querySelector('.name').value;
    console.log(name);
    
    $.ajax({
        method:"GET",
        data:{"name":name},
        url:"http://127.0.0.1:5000/salary",
        success: (result) => {
            console.log(result);
            let stat=document.querySelector(".stat");
            let html=''
            result.forEach(r =>{
                html+=`<tr>
                            <td> ${r.선수명} </td> <td> ${r.타율} </td>    
                            <td> ${r.타석} </td> <td> ${r.홈런} </td>
                            <td> ${r.타점} </td> <td> ${r.도루} </td>
                            <td> ${r.장타율} </td><td> ${r.출루율} </td>
                            <td> ${r.예상연봉} </td>
                        </tr>`;
            })
            stat.innerHTML=html;
        }
    })
}