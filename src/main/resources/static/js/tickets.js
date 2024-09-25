/*
    티켓 예매 페이지
    - 당월 경기일정 CSV 기준으로 2주치 경기를 불러온다 (월 넘어가면 다음 CSV도 읽기 필요)
    - 정렬 버튼들: 홈팀, 어웨이팀, 2주 안의 날짜
    - 정렬 결과는 경기 시작순으로, 이미 예약한 경기 별도 표시, 예약 마감 또는 취소된 경기 별도 표시
    - 예매시 포인트 10000점 차감, 예약 DB에 추가
    - 경기 예매 인원이 30명이 되면 예매 확정으로 친다
    - 예매 확정 전일 경우 자유롭게 취소 가능 및 100퍼센트 포인트 환급, 확정 이후에는 90퍼센트 환급
    - 번호/날짜/경기시간/예약마감시간/홈팀/원정팀/예약버튼/구장정보
*/
let dateInput = document.getElementById('dateInput');

// 현재 날짜와 2주 후 날짜 계산
const today = new Date();
const twoWeeksLater = new Date();
twoWeeksLater.setDate(today.getDate() + 14);

// 날짜 포맷을 YYYY-MM-DD로 변환
function formatDate(date){
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
};

// 최소 및 최대 날짜 설정
dateInput.min = formatDate(today);
dateInput.max = formatDate(twoWeeksLater);

// 현재 검색된 경기 구장 지도 API
stadiumMap()

function getMonthlySchedule(){
    // 월간경기일정 CSV에서 2주치 
    $.ajax({
        async: false,
        url: '/getMonthlySchedule',
        method: 'GET',
        data: {"home": homeName, "away": awayName, "date": date},
        success: (result) => {
            console.log(result);
            
        },
        
    })
}

function reserveMatch(){
    // 현재 세션에서 회원정보, ajax 월간경기CSV에서 경기코드
}

function stadiumMap(){
    $.ajax({
        async: false, url:'http://localhost:5000', method: 'get',
        success: resp => {  // 플라스크로부터 받은 데이터
            console.log(resp)
            var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = {
		        center: new kakao.maps.LatLng(37.56127, 126.98511), // 지도의 중심좌표
		        level: 6, // 지도의 확대 레벨
		        mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
		    }; 

            // 지도를 생성한다 
            var map = new kakao.maps.Map(mapContainer, mapOption); 

            // 지도 타입 변경 컨트롤을 생성한다
            var mapTypeControl = new kakao.maps.MapTypeControl();

            // 지도의 상단 우측에 지도 타입 변경 컨트롤을 추가한다
            map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);	

            // 지도에 확대 축소 컨트롤을 생성한다
            var zoomControl = new kakao.maps.ZoomControl();

            // 지도의 우측에 확대 축소 컨트롤을 추가한다
            map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

            // 지도에 마커를 생성하고 표시한다
            var marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(37.56332, 126.98230), // 마커의 좌표
                map: map // 마커를 표시할 지도 객체
            });

            // 마커 위에 표시할 인포윈도우를 생성한다
            var infowindow = new kakao.maps.InfoWindow({
                content : '<div style="padding:5px;">인포윈도우 :D</div>' // 인포윈도우에 표시할 내용
            });

            // 인포윈도우를 지도에 표시한다
            infowindow.open(map, marker);

            // 마커에 클릭 이벤트를 등록한다 (우클릭 : rightclick)
            kakao.maps.event.addListener(marker, 'click', function() {
                alert('마커를 클릭했습니다!');
            });
        }
    })
}


