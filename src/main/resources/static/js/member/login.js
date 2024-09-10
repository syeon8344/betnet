console.log('login.js');

function doLogIn(){console.log('doLogIn()');
    let id=document.querySelector('#id').value;
    let pw=document.querySelector('#pw').value;
    $.ajax({
        
        method:'post',
        url:"/member/login",
        data:{'userName':id,'password':pw},
        success:(result)=>{console.log(result);
            if(result){alert('로그인 성공')
                location.href="/"
            }
            else{
                alert('로그인 실패')
                document.querySelector('#id').value='';
                document.querySelector('#pw').value=''
                
            }
        }
    })


}