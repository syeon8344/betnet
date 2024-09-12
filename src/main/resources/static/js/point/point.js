let date = new Date();
console.log(date);
let currentYear = date.getFullYear();
let currentMonth = date.getMonth()+1 < 10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
let currentDay = date.getDate() < 10 ? "0"+(date.getDate()) : date.getDate();
date = `${currentYear}-${currentMonth}-${currentDay}`;
console.log(date)

document.querySelector('.endDate').value = date;
// 날짜 버튼을 눌렀을 때
function changeDate(day){
    // startdate에 넣을 때 빼기로 설정을 해줘야하므로 설정에 따른 값 매개변수 day로 받아오기
    console.log("changeDate()")
    let startDateInput = document.querySelector(".startDate");
    let startDate = new Date();     // 왼쪽 input
    startDate.setDate(startDate.getDate()-day);     // 오늘 날짜에서 매개변수로 받아온 일수 만큼 빼주기 js라이브러리 함수
    console.log(startDate);
    let startYear = startDate.getFullYear();    console.log(startYear);
    // 한자리수일 경우에 0 앞에 붙이는 삼항연산자
    let startMonth = startDate.getMonth()+1 < 10 ? "0"+(startDate.getMonth()+1) : startDate.getMonth()+1;      
    console.log(startMonth);
    let startDay = startDate.getDate() < 10 ? "0"+(startDate.getDate()) : startDate.getDate();          
    console.log(startDay);
    let strStartDate = `${startYear}-${startMonth}-${startDay}`;    // input date 포맷이 문자열 "YY-MM-DD" 형식으로만 받기 떄문에 문자 따로 만들어주기
    console.log(strStartDate)
    startDateInput.value = strStartDate;
    let endDateInput = document.querySelector(".endDate");  // 오른쪽 input
    endDateInput.value = date;  // 컨셉 상 enddate는 오늘 날짜를 기준으로 함 전역변수로 설정해둔 오늘 날짜 대입.
}

// 검색 기능 객체 컨셉 상 검색 조건이 문의 유형별 , 처리상태별 , 검색별 , 기간별 이므로 해당하는 객체 만들어주기
let searchInfo = {
    description : 0 , 
    startDate : '' , 
    endDate : '' , 
    memberid : 0
}
// 검색버튼 눌렀을 때
function onSearch(){
    console.log('onSearch()');
    let description = document.querySelector('.descriptionBox').value;
    let startDate = document.querySelector('.startDate').value;
    let endDate = document.querySelector('.endDate').value;
    searchInfo.description = description;
    searchInfo.startDate = startDate;
    searchInfo.endDate = endDate;
    console.log(searchInfo);
    // 새로고침
    getMyPoint();
}

let info = {}   // 결제에 필요한 member정보 저장하는 전역변수

getMyPoint();
function getMyPoint(){
    console.log('getMyPoint');
    $.ajax({
        async : false , 
        method : "get" , 
        url : "/point/mypoint" , 
        data : searchInfo , // 전역변수 보내기
        success : (r) => {
            console.log(r);
            info = r;
            console.log(info);
            if(r == ""){
                alert('로그인 후 이용 가능합니다')
                location.href="/member/login"
            }
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
                            <td> ${log.logDate} </td> <td> ${log.descriptionStr} </td> `;
                if (log.description == 3 || log.description == 4){
                    html += `       
                            <td> - ${log.pointChange} </td>
                        </tr>`;
                }else{
                    html += `       
                            <td> ${log.pointChange} </td>
                        </tr>`;
                }
            });
            pointLogBox.innerHTML = html;
        } , 
        error : (e) => {
            console.log(e);
        }
    })  // ajax end
}