
function salary(){ console.log('salary()');
    let name=document.querySelector('.name').value;
    console.log(name);
    
    $.ajax({
        method:"GET",
        data:{"name":name},
        url:"http://127.0.0.1:5000/salary",
        success: (result) => {
            console.log(result);
            
        }
    })
}