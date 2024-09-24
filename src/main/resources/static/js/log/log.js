
console.log('log.js')

// 회원 접속 조회 함수
mAccessLog()
function mAccessLog(){
    $.ajax({
       async : false,
       method : "GET" ,
       url : "/cadmin/accessLog" ,
       success : r => {
            console.log(r);
           let html = '';
            r.forEach(log => {
                html += `<tr>
                    <td>${log.accessid}</td>
                    <td>${log.memberid}</td>
                    <td>${log.username}</td>
                    <td>${log.memberdatetime}</td>
                    </tr>`;
            });
           document.querySelector('#mAccessLog').innerHTML = html
           }
    })
}