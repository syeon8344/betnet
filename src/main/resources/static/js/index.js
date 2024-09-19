console.log('index.js')
let memberInfo = {}   // 멤버정보 저장하는 변수 
console.log(memberInfo);
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
    // 경기 고를때마다 출력
    let purchaseCartBox = document.querySelector(".purchaseCartBox");
    let winandlossStr = "";
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
    oddses.push(oods)
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
            url:"/member/logincheck",
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





