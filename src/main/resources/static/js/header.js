console.log('header.js')

// 헤드 메타태그 CSRF 토큰을 AJAX 요청마다 포함하도록 헤더 JS에 등록
// Function to get CSRF token and header name from meta tags
function getCsrfToken() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    return { csrfToken, csrfHeader };
}

$.ajaxSetup({
    beforeSend: function(xhr) {
        const { csrfToken, csrfHeader } = getCsrfToken(); // Get CSRF token and header
        xhr.setRequestHeader(csrfHeader, csrfToken); // Set the header
    }
});

doLoginCheck();
function doLoginCheck(){
    console.log("doLoginCheck");
    
    $.ajax({
        async:false,
        method:'get',
        url:"/member/logincheck",
        success:(result)=>{
            console.log(result);
            let html = ''
            if (result.username == 'admin'){
                html += `<li class="nav-item"><a class="nav-link" href="/admin">관리자 페이지</a></li>
                <li class="nav-item"> 관리자 님</li>
                <li class="nav-item"><a class="nav-link" href="#" onclick="doLogout()">로그아웃</a></li>`
            }
            else if(result != ''){
                console.log('로그인')
                html += `<li class="nav-item">${result.name} 님</li>
                        <li class="nav-item pointInfo"></li>
                        <li class="nav-item"><a class="nav-link" href="#" onclick="doLogout()">로그아웃</a></li>
                        <li class="nav-item"><a class="nav-link" href="/member/mypage">마이페이지</a></li>
                        <li class="nav-item"><a class="nav-link" href="/game">게임구매내역</a></li>`
            }
            // else{
            //     console.log('비로그인')
            //     html+=`<li class="nav-item"><a class="nav-link" href="/member/signup">회원가입</a></li>
            //            <li class="nav-item"><a class="nav-link" href="/member/login">로그인</a></li>`
            // }
            document.querySelector('#topMenu').innerHTML=html;
        },
        error: (xhr) => {}  // 비로그인 상태 등 400대 오류: 변화 없음으로
    })
}


hypothesis()
// 파이썬 가설검증 연동
function hypothesis() {
    let hypoBar = document.querySelector(".hypoBar");

    console.log('hypothesis()');

    $.ajax({
        async: false,
        method: "get",
        url: "http://127.0.0.1:5000/hypo",
        success: (result) => {
            console.log(result);
            hypoBar.innerHTML = `<ul style="display: inline; margin: 0; padding: 0;">`;

            // 가설 결과를 배열로 가져와서 반복
            const hypothesisResults = Object.values(result).map(hypo => hypo.가설결과);
            hypothesisResults.forEach(result => {
                hypoBar.innerHTML += `<li style="font-size: 18px; display: inline; padding-right: 10px; margin-right: 200px; font-size: 20px; color: #ffffff;">${result}</li>`;
            });
            hypoBar.innerHTML += `</ul>`;
        } // success end
    }); // ajax end
}




getMyPoint();
function getMyPoint(){
    console.log('getMyPoint');
    $.ajax({
        async : false ,
        method : "get" ,
        url : "/point/mypoint" ,
        success : (r) => {
            console.log(r);
            let point = 0;
            // r 값이 null이 아니면 점수 업데이트
            if (r && r.sum !== undefined) {
                point = r.sum;
            }
            let html='';
            console.log('로그인')
            html+=`<a class="nav-link" href="/point">${point}포인트</a>`
            
            document.querySelector('.pointInfo').innerHTML=html;
        } ,
        error: (xhr) => {}  // 비로그인 상태 등 400대 오류: 변화 없음으로
    })  // ajax end
}   // getMyPoint() end


function doLogout(){console.log('doLogout()')
    $.ajax({
        method:'post',
        url:"/logout",
        success:(result)=>{console.log(result);
        location.href="/";
        }
    })
}


