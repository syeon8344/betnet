let info = {}   // 결제에 필요한 member정보 저장하는 전역변수

getMyPoint();
function getMyPoint(){
    console.log('getMyPoint');
    $.ajax({
        async : false , 
        method : "get" , 
        url : "/point/mypoint" , 
        success : (r) => {
            console.log(r);
            info = r;
            console.log(info);
            // if(r == ""){
            //     alert('로그인 후 이용 가능합니다')
            //     location.href="/member/login"
            // }
            let myPointBox = document.querySelector(".myPointBox");
            let html = info.points;
            myPointBox.innerHTML = html;
        }
    })  // ajax end
}   // getMyPoint() end

// 아임포트
// $(".payment").click(function() {
function payment(){
    let pointChange = document.querySelector(".pointChange").value; // 충전할 금액
    console.log(info);
    IMP.init('imp78254332');
    //결제시 전달되는 정보
    IMP.request_pay({
              pg : "html5_inicis", 
              pay_method : 'card',
              merchant_uid : `payment-${crypto.randomUUID()}`,
              name : '포인트충전'/*상품명*/,
              amount : pointChange /*상품 가격*/, 
              buyer_email : info.email /*구매자 이메일*/,
              buyer_name : info.name ,
              buyer_tel : info.cotact /*구매자 연락처*/,
          }, function(rsp) {
                // 결제 성공시 결제 금액과 discription(name) 필요
                console.log(rsp);
                if ( rsp.success ) {
              } else {
                    var msg = '결제가 완료되었습니다.';
                    // member DB에 저장
                    $.ajax({
                        async : false , 
                        method : 'put' , 
                        url : "/point/addpoint" , 
                        data : {memberid : info.memberid , pointChange : pointChange} , 
                        success : (r) => {
                            console.log(r);
                            if(r == 1){
                                document.querySelector(".pointChange").value = ""
                                getMyPoint();
                            }
                        } , 
                        error : (e) => {
                            console.log(e);
                        }
                    })  // ajax end
                    // 포인트 로그에 저장
                    $.ajax({
                        async : false , 
                        method : 'post' , 
                        url : "/point/insertpointlog" , 
                        data : {memberid : info.memberid , pointChange : pointChange , description : 1} , // description은 충전이니까 1로 지정
                        success : (r) => {
                            console.log(r);
                            mypointlog();
                        } , 
                        error : (e) => {
                            console.log(e);
                        }
                    })  // ajax end
                }
                  alert(msg);
              }
            );
}   // payment() end

mypointlog();
// 포인트내역 출력
function mypointlog(){
    $.ajax({
        async : false , 
        method : 'get' , 
        url : "/point/mypointlog" , 
        data : {memberid : info.memberid} , 
        success : (r) => {
            console.log(r);
            let pointLogBox = document.querySelector(".pointLogBox");
            let html = ``
            r.forEach(log => {
                html += `<tr>
                            <td> ${log.pointlogid} </td> <td> ${log.logDate} </td> <td> ${log.descriptionStr} </td> <td> ${log.pointChange} </td>
                        </tr>`
            });
            pointLogBox.innerHTML = html;
        } , 
        error : (e) => {
            console.log(e);
        }
    })  // ajax end
}