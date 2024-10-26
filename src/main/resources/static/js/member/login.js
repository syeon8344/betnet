console.log('login.js');

$(document).ready(function() {
    $('#logoutButton').click(function() {
        $.ajax({
            url: '/logout',
            method: 'POST',
            success: function(response) {
                $('#logoutMessage').text(response.message);
            },
            error: function() {
                $('#logoutMessage').text('Error logging out.');
            }
        });
    });
});
// 엔터키 이벤트 리스너 추가
document.getElementById('pw').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        doLogIn(); // 로그인 함수 호출
    }
});

function doLogIn(){console.log('doLogIn()');
    let id=document.querySelector('#id').value;
    let pw=document.querySelector('#pw').value;
    // FormData 객체 생성
    const formData = new FormData(document.querySelector('.loginForm'));
    $.ajax({
        method:'post',
        url:"/auth/login",
        data:formData,
        processData: false, // FormData를 사용하므로 false로 설정
        contentType: false, // FormData를 사용하므로 false로 설정
        success:(result)=>{
            console.log(result);
            if(result){
                // loginPoint(result.memberid)
                location.href="/"
            }
            else{
                alert('로그인 실패')
                document.querySelector('#id').value='';
                document.querySelector('#pw').value=''
            }
        },
        error: (jqXHR) => { // 코드 4xx, 5xx 등 오류
            const data = jqXHR.responseJSON; // JSON
            alert(data.error); // JSON 메시지 처리
        }
    })
}

function naverLogin() {
    console.log('네이버 로그인 시도');

    // 네이버 로그인 API 호출 또는 관련 로직 작성
    // 예를 들어, OAuth 인증을 위한 리다이렉트 등을 수행할 수 있습니다.

    window.location.href = 'https://nid.naver.com/oauth2.0/authorize?...'; // 실제 로그인 URL로 대체
}

function kakaoLogin() {
    console.log('카카오 로그인 시도');

    // 네이버 로그인 API 호출 또는 관련 로직 작성
    // 예를 들어, OAuth 인증을 위한 리다이렉트 등을 수행할 수 있습니다.

    window.location.href = 'https://nid.naver.com/oauth2.0/authorize?...'; // 실제 로그인 URL로 대체
}
// function loginPoint(memberid){
//     console.log('loginPoint()');
//     $.ajax({
//             method:'post',
//             url:"/point/login",
//             data:{'memberid':memberid},
//             success:(result)=>{console.log(result);
//                 if(result){
//                      alert('로그인 포인트 10 증정')
//                 }
//                 else{
//                     alert('포인트 지급 실패')
//                 } //else end
//             } // success end
//     }) // ajax end
// }// function end