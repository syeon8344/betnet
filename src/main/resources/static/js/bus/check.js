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

            let currentTime = new Date();  // 현재 시간
            // 12시간을 밀리초로 변환
            let HoursInMillis = 3 * 60 * 60 * 1000;


            r.forEach(log => {
                // 게임코드 문자열 시간 타입으로 변환 코드
                // 문자열을 '-'로 분리
                let parts = log.gameCode.split('-');
                // 날짜, 팀 이름, 시간 변수에 할당
                let datePart = parts[0]; // "20241023"
                let timePart = parts[2]; // "1830"
                // 날짜와 시간을 결합하여 YYYY-MM-DDTHH:mm 형식으로 변환
                let formattedDateTime = `${datePart.substring(0, 4)}-${datePart.substring(4, 6)}-${datePart.substring(6, 8)}T${timePart.substring(0, 2)}:${timePart.substring(2, 4)}`;
                // Date 객체로 변환
                let gameDateTime = new Date(formattedDateTime);
                let HoursBefore = new Date(gameDateTime.getTime() - HoursInMillis);

                if(HoursBefore>currentTime){
                    html += `<tr>
                        <td> ${log.resNo} </td> <td> ${log.gameCode} </td><td> ${log.logDate} </td><td> ${log.seat} </td><td> ${log.reStatus==-1?'예약완료':'예약취소'} </td><td><button type="button" onclick="cancel('${log.gameCode}',${log.seat})">취소</button></td>
                    </tr>`;
                } // if end
                else{
                    html += `<tr>
                        <td> ${log.resNo} </td> <td> ${log.gameCode} </td><td> ${log.logDate} </td><td> ${log.seat} </td><td> ${log.reStatus==-1?'예약완료':'예약취소'} </td><td><button type="button" onclick="notcancel()">취소불가</button></td>
                    </tr>`;
                }//else end
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


function notcancel(){
    alert("12시간 전에만 취소가 가능합니다.")
}

