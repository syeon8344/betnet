let gameCode = new URL( location.href ).searchParams.get('gameCode'); // 현재 URL 경로상의 bno 값 호출
    console.log( gameCode );
    let selectedSeat = null;  // 선택된 좌석 하나만 저장
    let bookedSeats = []
    createSeats()
    // 좌석 상태 변경 함수 (한 좌석만 선택 가능)
    function createSeats() {
    let seatsContainer = document.getElementById('seats');
    let seatsHTML = `<div>`; // 좌석 HTML 문자열 초기화

    for (let i = 1; i <= 8; i++) {
        // 예매 완료된 좌석 체크
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

// 좌석 상태 변경 함수
function toggleSeat(seatNumber) {
    let seat = document.getElementById('seat' + seatNumber); // 좌석 ID는 번호로 생성됨
    let cartBox = document.querySelector(".purchaseCartBox"); // 선택된 좌석을 표시할 위치

    if (selectedSeat === null) {
        // 좌석이 선택되지 않은 상태에서만 좌석 선택 가능
        seat.classList.remove('available');
        seat.classList.add('unavailable');
        seat.disabled = true;
        selectedSeat = seatNumber; // 선택된 좌석 저장
        console.log(`좌석 ${seatNumber} 선택됨`);

        // 선택된 좌석을 Cart에 추가
        let seatInfo = document.createElement('tr');
        seatInfo.innerHTML = `<td>좌석 번호: ${seatNumber} 18000포인트</td>`;
        seatInfo.id = "cart-seat";
        cartBox.appendChild(seatInfo); // 좌석 정보 추가
    } else {
        alert("이미 다른 좌석이 선택되어 있습니다. 취소 후에 다른 좌석을 선택하세요.");
    }
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
    $.ajax({
            async : false ,
            method:"post",
            url:"/bus/Reservation",
            data:{gameCode:gameCode,pointChange:-18000,description:9,seat:selectedSeat,reStatus:0},
            success: (r) => {
                console.log(r);
                if(r){alert('예약이 완료되었습니다.')
                    location.href="/"
                }else{alert('예약에 실패했습니다.')}
            } //success end
    }) // ajax end
}   // gamePurchase end






