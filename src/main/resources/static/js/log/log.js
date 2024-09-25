
console.log('log.js')

// 회원 접속 조회 함수
function mAccessLog(){
    $.ajax({
       async : false,
       method : "GET" ,
       url : "/cadmin/accessLog" ,
       success : r => {
            console.log(r);
           let html = '';
           html += `<table class="table table-hover table-bordered">
                        <thead class="thead-dark">
                        <tr>
                          <th scope="col">접속번호</th>
                          <th scope="col">회원번호</th>
                          <th scope="col">이름</th>
                          <th scope="col">접속날짜/th>
                        </tr>
                        </thead>
                        <tbody>`;
           r.forEach(log => {
                html += `<tr>
                    <td>${log.accessid}</td>
                    <td>${log.memberid}</td>
                    <td>${log.username}</td>
                    <td>${log.memberdatetime}</td>
                    </tr>`;
           });
           html += `    </tbody>
                       </table>`;
           document.querySelector('.indexMain').innerHTML = html
       }    // success end
    })  // ajax end
}   // function end