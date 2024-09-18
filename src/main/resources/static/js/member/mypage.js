getMyPoint();
function getMyPoint(){
    console.log('getMyPoint');
    $.ajax({
        async : false ,
        method : "get" ,
        url : "/point/mypoint" ,
        success : (r) => {
            console.log(r);
            let myPointBox = document.querySelector(".myPointBox");
            let html =`<a class="nav-link" href="/point">${r.sum}</a>`
            myPointBox.innerHTML = html;
        } ,
        error : (e) => {
            console.log(e)
        }
    })  // ajax end
}   // getMyPoint() end

doLoginCheck();
function doLoginCheck(){
    $.ajax({
        async:false,
        method:'get',
        url:"/member/logcheck",
        success:(result)=>{console.log(result);
            if(result == ""){
                alert("로그인 후 이용가능합니다.")
                location.href = "/member/login"
            }
            else{console.log(result);
                document.querySelector("#name").value=result.name
                document.querySelector("#userName").value=result.userName
                document.querySelector("#contact").value=result.contact
                document.querySelector("#email").value=result.email
                document.querySelector("#account").value=result.account
                document.querySelector("#teamCode").value=result.teamName
            }
        }
    })
}
