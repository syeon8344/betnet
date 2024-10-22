console.log("game.js");

doLoginCheck();
function doLoginCheck(){
    console.log(doLoginCheck);
    
    $.ajax({
        async:false,
        method:'get',
        url:"/member/logcheck",
        success:(result)=>{console.log(result);
            if(result == ""){
                alert("로그인 후 이용가능합니다.")
                location.href = "/member/login"
            }
            // memberInfo = result
            // console.log(memberInfo)
        }
    })
}



busLog();
function busLog(){
    console.log(busLog);
    
    $.ajax({
        async : false , 
        method : "get" , 
        url : "/bus/log" ,
        success : (r) => {
            console.log(r);
            let gameListBox = document.querySelector('.gameListBox');
            let html = ``;
            r.forEach(log => {
                html += `<tr>
                            <td> ${log.resNo} </td> <td> ${log.gameCode} </td><td> ${log.logDate} </td><td> ${log.seat} </td><td> ${log.reStatus==-1?'예약완료':'예약취소'} </td><td><button type="button" onclick="cancel()">취소</button></td>
                        </tr>`;
            });
            gameListBox.innerHTML = html;
        } , 
        error : (e) => {
            console.log(e);
        }
    })
}

function cancel(){
    console.log(cancel);
    
}