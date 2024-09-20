console.log('index.js')
let memberInfo = {}   // 멤버정보 저장하는 변수 
console.log(memberInfo);
getSchedule()
// 파이썬 플라스크 서버에서 크롤링된 정보
// 우선 오늘 날짜 경기일정 + 내일 경기일정(혹시 없으면 모레 경기일정)
// 오늘 날짜 경기가 없으면 내일과 모레(없으면 사흘)
// X날과 X+1날 경기일정
function getSchedule(){
    $.ajax({
        method: "GET",
        url: "http://127.0.0.1:5000/getschedule",
        success: (result) => {
            result = JSON.parse(result);
            console.log(result);
            if(isEmptyObject(result)){
                alert('경기일정이 존재하지 않습니다.');
                return;
            }
            // 경기일정 표시
            let tbody = document.querySelector('.gameScheduleBody');
            let html = '';
            for(let i = 0 ; i < result.length ; i++){
                html += `<tr>
                    <td>${i+1}</td>
                    <td>${result[i].월}/${result[i].일} ${result[i].시작시간}</td>
                    <td>KBO</td>
                    <td>일반</td>
                    <td>${result[i].홈팀명} vs ${result[i].어웨이팀명}</td>
                    <td> <button type="button" onclick="choiceWinandLoss(${i+1}, '${result[i].경기코드}' , 1 , 2.14); activateButton(this);"> 승 / 2.14 </button> <button type="button" onclick="choiceWinandLoss(${i+1}, '${result[i].경기코드}' , 0 , 1.46); activateButton(this);"> 패 / 1.46 </button> </td>
                    <td>${result[i].월}/${result[i].일} ${result[i].시작시간}</td>
                    </tr>`;
            }
            tbody.innerHTML = html;
        }
    })
}


// 객체가 비어 있는지 확인하는 함수
function isEmptyObject(obj) {
    return Object.keys(obj).length === 0;
}

let matchids = []    // 선택한 경기인덱스 넣는 배열
let winRates = []   // 선택한 게임 승률
let winandlosses = []
let oddses = []   // 각 게임에 맞는 배당률
    // winRates = [45 , 65 , 55 , 35];    // 승률 샘플 테스트 후 넘겨지는 값으로 가져와야함
    // console.log(winRates)
    // 배당률 계산
    // winRates.forEach(winRate => {
    //     console.log(winRate);
    //     odds.push((100 - winRate) * 0.036); 
    // });
    // console.log(odds);

// 승패 고를때마다 실행되는 함수
function choiceWinandLoss(number , matchid , winandloss , oods){
    // 매개변수 winandloss는 회원이 승을 누르는지 패를 누르는지 판단하기 위함
    // 1 -> 승 0 -> 패
    // 매개변수로 더 가져와야 할 것들 승률 , 경기 인덱스...?
    console.log('choiceWinorLoss()');
    // 같은 경기 인덱스인데 버튼을 두번 누르면 두번 리스트에 들어가는 경우 제외
    for(let i = 0; i < matchids.length; i++){
        if(matchid == matchids[i]){
            return;
        }
    }
    // 경기 고를때마다 출력
    let purchaseCartBox = document.querySelector(".purchaseCartBox");
    let winandlossStr = "";
    // 승을 골랐는지 패를 골랐는지 구분해주는 문자 출력
    if(winandloss == 1){
        winandlossStr = "승";
    }else{
        winandlossStr = "패";
    }
    let html = ``;
    html += ` <tr> <th> ${number} </th> <th> ${matchid} </th> <th> ${winandlossStr} / ${oods} </th> <th> <button onclick="removeMatch(this)">x</button></th></tr>`;
    purchaseCartBox.innerHTML += html;

    matchids.push(matchid); // 경기 인덱스 배열저장
    winandlosses.push(winandloss);    // 회원이 결정한 승패 배열 저장
    oddses.push(oods)   // 배당률 배열 저장
    console.log(matchids);
    console.log(winandlosses);
    console.log(oddses);

    updateTotalOdds();
}

// 금액 입력할 때마다 호출되는 함수
function updateTotalOdds() {
    let totalOdds = oddses.reduce((acc, cur) => acc * cur, 1).toFixed(2);
    console.log(totalOdds);

    let betPoint = document.querySelector(".pointChange").value || 0;
    let ifPoint = Math.round(totalOdds * betPoint);
    console.log(ifPoint);

    let purchaseInfoBox = document.querySelector(".purchaseInfoBox");
    let infohtml = `
        <tr> <th> 선택한 경기 수 </th> <td> ${matchids.length} </td> </tr>
        <tr> <th> 예상 적중 배당률 </th> <td>${totalOdds}</td> </tr>
        <tr> <th> 예상 적중 금액 </th> <td>${ifPoint}</td> </tr>`;
    
    purchaseInfoBox.innerHTML = infohtml;
}

function activateButton(button) {
    // 같은 tr 내의 모든 버튼 비활성화
    const buttons = button.closest('tr').querySelectorAll('button');
    buttons.forEach(btn => {
        btn.disabled = true; // 또는 btn.style.display = 'none';
    });
    
    // 클릭한 버튼만 활성화
    button.disabled = false; // 또는 button.style.display = 'block';
}

// 선택한 경기를 제거하는 함수
function removeMatch(button) {
    const row = button.closest('tr');
    console.log(row)
    const matchid = row.children[1].innerText; // matchid를 가져옴
    console.log(matchid)

    // matchid, winandloss, odds 배열에서 해당 정보를 제거
    const index = matchids.indexOf(matchid);
    console.log(index)
    if (index > -1) {
        matchids.splice(index, 1);
        winandlosses.splice(index, 1);
        oddses.splice(index, 1);
    }

    row.remove(); // 테이블에서 행 제거

    console.log(matchids);
    console.log(winandlosses);
    console.log(oddses);
    
    updateTotalOdds(); // 정보 업데이트
    activateButtons(matchid);  // 버튼활성화
}

// 같은 matchid의 버튼만 활성화
function activateButtons(matchid) {
    const buttons = document.querySelectorAll('.gameScheduleBox tbody button');
    buttons.forEach(button => {
        const buttonMatchid = button.onclick.toString().match(/'([^']+)'/)[1]; // onclick에서 matchid 추출
        if (buttonMatchid === matchid) {
            button.disabled = false; // 해당 경기 버튼만 활성화
        }
    });
}

// 게임구매
function gamePurchase(){
    // 변수가 비어져있으면 실행되는 함수 // 로그인 후 게임 구매가 가능!
    if(isEmptyObject(memberInfo)){
        $.ajax({
            async:false,
            method:'get',
            url:"/member/logcheck",
            success:(result)=>{console.log(result);
                if(result == ""){
                    alert("로그인 후 이용가능합니다.")
                    location.href = "/member/login"
                }
                memberInfo = result
            }
        })
    }
    console.log(matchids);
    console.log(winandlosses);
    console.log(oddses);
    let memberid = memberInfo.memberid;
    let pointChange = document.querySelector(".pointChange").value;
    // 금액 제한
    if(pointChange > 50000){
        alert("구매가능 포인트는 50000포인트까지 가능합니다.")
        return;
    }
    let info = {
        memberid : memberid , pointChange : pointChange , matchids : matchids , winandlosses : winandlosses , oddses
    }
    $.ajax({
        async:false,
        method:'post',
        url:"/game/purchase",
        data : JSON.stringify(info) ,
        contentType : "application/json" , 
        success:(r)=>{
            console.log(r);
            // db 저장 성공
            if(r == 1){
                alert("구매가 완료되었습니다.");
                location.href = "/game"
            }
            // 포인트 부족
            if(r == 3){
                alert("포인트 충전 후 구매 가능합니다.");
                location.href = "/point"
            }

        } , 
        error : (e) =>{
            console.log(e);
        }
    })
}   // gamePurchase end





