loginCheck()

let gameCode = new URL( location.href ).searchParams.get('gameCode'); // 현재 URL 경로상의 bno 값 호출
    console.log( gameCode );
    let selectedSeats = []; // 선택된 좌석 배열
let totalPrice = 0; // 총 가격 변수
const seatPrice = 18000; // 좌석당 가격
const maxSeats = 4; // 최대 선택 가능한 좌석 수
createSeats()
function createSeats() {
    let seatsContainer = document.getElementById('seats');
    let seatsHTML = `<div>`; // 좌석 HTML 문자열 초기화
    let bookedSeats = []; // 예매 완료된 좌석 배열 초기화

    $.ajax({
        async: false,
        method: 'get',
        url: "/bus/check",
        data: { gameCode: gameCode },
        success: result => {
            console.log(result);
            result.forEach(log => {
                if (log.sumStatus == -1) {
                    bookedSeats.push(log.seat);
                }
            });
        }
    });

    for (let i = 1; i <= 24; i++) {
        let isBooked = bookedSeats.includes(i);
        let buttonClass = isBooked ? 'seat unavailable' : 'seat available';
        let buttonDisabled = isBooked ? 'disabled' : '';
        let buttonOnClick = isBooked ? '' : `onclick="toggleSeat(${i})"`; // 클릭 이벤트 추가

        // 버튼 HTML 생성
        seatsHTML += `<button class="${buttonClass}" id="seat${i}" ${buttonDisabled} ${buttonOnClick}>${i}</button>`;
    }

    seatsHTML += `</div>`; // div 종료
    seatsContainer.innerHTML = seatsHTML; // 생성된 HTML 삽입
}

// 좌석 상태 변경 함수 (4개 좌석까지 선택 가능)
function toggleSeat(seatNumber) {
    let seat = document.getElementById('seat' + seatNumber); // 좌석 ID는 번호로 생성됨
    let cartBox = document.querySelector(".purchaseCartBox"); // 선택된 좌석을 표시할 위치

    if (selectedSeats.includes(seatNumber)) {
        // 좌석이 이미 선택된 경우 (취소 로직)
        seat.classList.remove('unavailable');
        seat.classList.add('available');
        selectedSeats = selectedSeats.filter(num => num !== seatNumber); // 선택 해제된 좌석 배열에서 제거
        totalPrice -= seatPrice; // 총 가격에서 해당 좌석 가격 제거
        document.getElementById(`cart-seat-${seatNumber}`).remove(); // 선택된 좌석 Cart에서 제거
    } else {
        if (selectedSeats.length >= maxSeats) {
            alert(`최대 ${maxSeats} 좌석까지만 선택할 수 있습니다.`);
            return; // 선택을 중단
        }

        // 좌석 선택
        seat.classList.remove('available');
        seat.classList.add('unavailable');
        selectedSeats.push(seatNumber); // 선택된 좌석 배열에 추가
        totalPrice += seatPrice; // 총 가격에 좌석 가격 추가

        // 선택된 좌석을 Cart에 추가
        let seatInfo = document.createElement('tr');
        seatInfo.innerHTML = `<td>좌석 번호: ${seatNumber} ${seatPrice}포인트</td>`;
        seatInfo.id = `cart-seat-${seatNumber}`; // 각 좌석에 고유 ID 할당
        cartBox.appendChild(seatInfo); // 좌석 정보 추가
    }

    // 총 가격 표시 업데이트
    updateTotalPrice();
}

// 총 가격 업데이트 함수
function updateTotalPrice() {
    let priceDisplay = document.getElementById('totalPrice');
    
    // 총 가격을 표시할 div가 없으면 새로 추가
    if (!priceDisplay) {
        priceDisplay = document.createElement('div');
        priceDisplay.id = 'totalPrice';
        priceDisplay.style.marginTop = '20px'; // 총 가격 표시를 맨 밑에 여백을 주어 표시
        document.querySelector('.purchaseCartBox').appendChild(priceDisplay);
    }

    // 총 가격 내용 갱신
    priceDisplay.innerHTML = `<strong>총 가격: ${totalPrice} 포인트</strong>`;
}



    // 취소 버튼 클릭 시 선택된 좌석을 다시 가능 좌석으로 변경
    function resetSeats() {
        if (selectedSeat !== null) {
            let seat = document.getElementById('seat'+selectedSeat);
            seat.classList.remove('unavailable');
            seat.classList.add('available');
            seat.disabled = false;
            console.log(`좌석 ${seat.textContent} 다시 가능 상태로 변경됨`);
            selectedSeat = null;  // 선택된 좌석 초기화

            // Cart에서 선택된 좌석 정보 삭제
            let cartBox = document.querySelector(".purchaseCartBox");
            let seatInfo = document.getElementById("cart-seat");
            if (seatInfo) {
                cartBox.removeChild(seatInfo);  // 선택된 좌석 정보를 Cart에서 삭제
            }
        } else {
            alert("선택된 좌석이 없습니다.");
        }
    }




// 게임구매
function busPurchase(){
    console.log("busPurchase()")
    let close=true;
    $.ajax({
        async:false,
        method:'get',
        url:"/bus/check",
        data:{gameCode:gameCode},
        success: (result)=>{
            result.forEach(log =>{
                if(log.sumStatus==-1 && log.seat==selectedSeat){
                    alert('이미 예약된 좌석입니다.')
                    selectedSeat = null;
                    createSeats()
                    close=false;
                }
            })
        }
    })
    if(close==false){return}
    $.ajax({
            async : false ,
            method:"post",
            url:"/bus/Reservation",
            data:{gameCode:gameCode,pointChange:-18000,description:9,seat:selectedSeat,reStatus:-1},
            success: (r) => {
                console.log(r);
                if(r){alert('예약이 완료되었습니다.')
                    location.href="/"
                }else{alert('포인트가 부족합니다.')}
            } //success end
    }) // ajax end
}   // gamePurchase end


//로그인 체크
    //로그인한 회원만 예약 가능
function loginCheck() {
    $.ajax ({
        async : false,
        method : "get",
        url : "/member/logincheck",
        success : r => {    console.log(r);
            if ('' == r) {
                alert('버스 예매는 로그인 후 가능합니다');
                location.href = '/member/login';
            }
        }

    })
}



