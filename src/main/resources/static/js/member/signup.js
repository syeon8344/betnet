console.log('signup.js');
// 현재 유효성검사 체크 현황
let checkArray=[false,false,false,false,false];

function checkUsername(){ console.log('checkUsername()')
    // 1. 입력된 값 가져오기
    let userName = document.querySelector('#username').value;   console.log( userName );
    let modalBody = document.getElementById('usernameModalBody');
    // 2. 정규표현식 : 영소문자와 숫자 조합의 5~12글자 까지 허용
    let idReg =  /^[a-z0-9]{5,12}$/
    // 3. 정규표현식 검사.
    console.log( idReg.test( userName ) )
    if( idReg.test(userName) ){
        // 아이디 중복검사 REST API 통신
        $.ajax({
            async : false,              // 비동기true vs 동기false
            method : "get",             // HTTP method
            url : "/member/idcheck",    // HTTP url
            data : { userName : userName } ,        // HTTP 전송할 DATA
            success : (result)=>{   console.log(result);
                // HTTP 응답받을 DATA
                if( !result ){
                    modalBody.textContent = '사용 가능한 아이디입니다.';
                    modalBody.classList.remove('text-danger');
                    modalBody.classList.add('text-success');
                    checkArray[0]=false;
                }else{
                    modalBody.textContent = '이미 사용 중인 아이디입니다.';
                    modalBody.classList.remove('text-success');
                    modalBody.classList.add('text-danger');
                    checkArray[0]=true;
                }
            } // success method end
        }) // ajax end
    }else{
        modalBody.textContent = '영소문자와 숫자 조합의 5~12글자 까지 가능합니다.';
        modalBody.classList.remove('text-success');
        modalBody.classList.add('text-danger');
        checkArray[0]=false;
    }
     // 모달 창 표시
     $('#usernameModal').modal('show');
} // method end

function checkPhone(){console.log('checkPhone');
}

function hideModal(){console.log('hideModal()');

    $('#usernameModal').modal('hide');
}
