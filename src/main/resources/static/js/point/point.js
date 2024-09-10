
getMyPoint();
function getMyPoint(){
    console.log('getMyPoint');
    // 데이터는 테스트를 위해 임의 설정
    $.ajax({
        async : false , 
        method : "get" , 
        url : "/point/mypoint" , 
        data : {"memberid" : 1} , 
        success : (r) => {
            console.log(r);
            let myPointBox = document.querySelector(".myPointBox");
            let html = r.points;
            myPointBox.innerHTML = html;
        }
    })  // ajax end
}

// 아임포트
$(".payment").click(function() {
    //class가 payment인 태그를 선택했을 때 작동한다.
      
    IMP.init('imp78254332');
    //결제시 전달되는 정보
    IMP.request_pay({
              pg : "html5_inicis", 
              pay_method : 'card',
              merchant_uid : `payment-${crypto.randomUUID()}`,
              name : '결제테스트'/*상품명*/,
              amount : 1000 /*상품 가격*/, 
              buyer_email : 'iamport@siot.do'/*구매자 이메일*/,
              buyer_name : '양재연',
              buyer_tel : '010-1234-5678'/*구매자 연락처*/,
              buyer_addr : '서울특별시 강남구 삼성동'/*구매자 주소*/,
              buyer_postcode : '123-456'/*구매자 우편번호*/
          }, function(rsp) {
              var result = '';
                if ( rsp.success ) {
                    var msg = '결제가 완료되었습니다.';
                    msg += '고유ID : ' + rsp.imp_uid;
                    msg += '상점 거래ID : ' + rsp.merchant_uid;
                    msg += '결제 금액 : ' + rsp.paid_amount;
                    msg += '카드 승인번호 : ' + rsp.apply_num;
                    result ='0';
              } else {
                    var msg = '결제에 실패하였습니다.';
                    msg += '에러내용 : ' + rsp.error_msg;
                    result ='1';
                }
                if(result=='0') {
                      location.href= $.getContextPath()+"/Cart/Success";
                  }
                  alert(msg);
              }
            );
})