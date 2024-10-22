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
                            <td> ${log.resNo} </td> <td> ${log.gameCode} </td><td> ${log.logDate} </td><td> ${log.seat} </td><td> ${log.reStatus==-1?'예약완료':'예약취소'} </td><td><button type="button" onclick="cancel('${log.gameCode}',${log.seat})">취소</button></td>
                        </tr>`;
            });
            gameListBox.innerHTML = html;
        } , 
        error : (e) => {
            console.log(e);
        }
    })
}

function cancel(gameCode,seat){
    console.log(cancel);
    $.ajax({
        async : false ,
        method:"post",
        url:"/bus/Reservation",
        data:{gameCode:gameCode,pointChange:18000,description:10,seat:seat,reStatus:1},
        success: (r) => {
            console.log(r);
            if(r){alert('취소가 완료되었습니다.')
                location.href="/"
            }else{alert('취소가 실패하였습니다.')}
        } //success end
}) // ajax end
    
}