console.log('cheerChat.js');
let matchId = new URL( location.href ).searchParams.get('gameCode'); // 현재 URL 경로상의 bno 값 호출

// 로그인 체크
let memberid = ``
let userName = ``;
let roomID = "";

function doLoginCheck(){
    $.ajax({
        async:false,
        method:'get',
        url:"/member/logcheck",
        success:(result)=>{
            if(result == ""){
                alert("로그인 후 이용 가능합니다.");
                location.href = "/member/login";
            }
            else{console.log(result);
                memberid = `${result.memberid}`
                userName = `${result.userName}(${result.teamName})`
            }
        }
    })
}
doLoginCheck();

var map; // map 변수를 전역으로 선언

// 마커 이미지의 주소
var markerImageUrl = 'http://localhost:8080/img/cheerchat.png' , 
    markerImageSize = new kakao.maps.Size(40, 42), // 마커 이미지의 크기
    markerImageOptions = { 
        offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
    };

// 지도에 표시된 마커 객체를 가지고 있을 배열입니다
var markers = [];
// 내 위치 정보 가져오기
// 위치 정보를 가져오는 함수 (Promise 반환)
function getGeoLocation() {
    return new Promise((resolve, reject) => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(resolve, reject);
        } else {
            reject(new Error("Geolocation is not supported by this browser."));
        }
    });
}

// 전역 변수 선언
let lat, long;

// 위치 정보 처리 함수
async function accessToGeo() {
    try {
        const position = await getGeoLocation();
        const positionObj = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
        };
        return positionObj; // 객체로 반환
    } catch (error) {
        console.error("Error retrieving location: ", error);
    }
}

// 위치 접근 함수 호출
async function main() {
    const { latitude, longitude } = await accessToGeo(); // 비동기 처리
    lat = latitude;   // 전역 변수에 저장
    long = longitude; // 전역 변수에 저장
    console.log(lat, long); // 위도와 경도 출력

    // 카카오 지도 로드 후 지도를 생성합니다
    kakao.maps.load(function () {
        // 지도 초기화
        const mapContainer = document.getElementById('map');
        const mapOption = {
            center: new kakao.maps.LatLng(lat, long),
            level: 3
        };

        map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 지도를 클릭했을 때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(map, 'click', function(mouseEvent) {   
            console.log(mouseEvent.latLng);     
            // 클릭한 위치에 마커를 표시합니다 
            addMarker(mouseEvent.latLng);             
        });
    });
}

// 위치 접근 함수 호출
main();

// 기존 방 불러오기
function readRoom(){
    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        let msg = {
            'type': 'read',
            'matchId' : matchId
        };
        console.log(msg);
        try {
            cheerclientSocket.send(JSON.stringify(msg));
        } catch (error) {
            console.error("메시지 전송 중 오류 발생:", error);
        }
    } else {
        console.error("WebSocket이 열리지 않았습니다.");
    }

} // readRoom() end

// 기존방 마커 찍기
function existRoom(msg){
    console.log('existRoom()');
    console.log(msg);
    Object.entries(msg).forEach(([key, value]) => {
        console.log(`Key: ${key}, Value: ${value}`);
        console.log(value);
        value.forEach( value2 => {
            console.log(value2);
            let roomID = value2.roomId;
            let position = {}
            position.latitude = value2.latitude; // 위도
            position.longitude = value2.longitude; // 경도
            console.log(position);
            var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);
        
            var marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(position.latitude, position.longitude), // LatLng 객체 사용
                image: markerImage  // 마커 이미지
            });
            marker.setMap(map);
            console.log(marker);
            markers.push(marker);
            
            // 마커에 클릭이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'click', function(event) {
                // 마커 위에 인포윈도우를 표시합니다
                // infowindow.open(map, marker);  
                alert(`'${value2.roomTitle}'방으로 입장합니다.`);
                // enterChat();
                showCheerRoom(event , roomID);
            });
        });
    }); 
} // existRoom() end

// 마커 추가 함수
async function addMarker(position) {
    console.log('addMarker 호출됨', position);
   
    const markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);
    
    const marker = new kakao.maps.Marker({
        position: position, // 마커 위치 위도 경도
        image: markerImage  // 마커 이미지
    });

    marker.setMap(map);
    markers.push(marker); 

    // 방 만들기 함수 호출
    const { roomTitle, roomID } = await addRoom(position); // 방 만들기 함수를 비동기 처리
    console.log(roomTitle, roomID);

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function(event) {
        alert(`'${roomTitle}' 방으로 입장합니다.`);
        showCheerRoom(event, roomID);
    });
}

// 방 만들기
async function addRoom(position) {
    console.log(position);
    console.log(matchId);
    let roomTitle = prompt("채팅방 이름을 입력해주세요.");
    alert("채팅방을 생성합니다.");
    roomID = generateUUID();

    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        let msg = {
            'type': 'marker',
            'message': roomID,
            'position': position, 
            'date': new Date().toLocaleString(), 
            'memberid': memberid, 
            'roomTitle': roomTitle , 
            'matchId' : matchId
        };
        console.log(msg);
        try {
            cheerclientSocket.send(JSON.stringify(msg));
        } catch (error) {
            console.error("메시지 전송 중 오류 발생:", error);
        }
    } else {
        console.error("WebSocket이 열리지 않았습니다.");
    }

    // 지도 새로고침
    readRoom();

    return { roomTitle, roomID };
}

// cheerRoom 표시 함수
function showCheerRoom(event = null, roomID) {
    console.log(roomID)
    var cheerRoom = document.getElementById('cheerRoom');
    cheerRoom.style.display = 'block'; // cheerRoom 표시
    enterChat(roomID);
}
// 채팅방을 나가는 함수
function leaveChat() {
    console.log(leaveChat);
    var cheerRoom = document.getElementById('cheerRoom');
    cheerRoom.style.display = 'none'; // cheerRoom 숨기기
    cheerMsgBox.innerHTML = "";
}

// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
function setMarkers(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }            
}

// 방번호 만들기
function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0; // 0-15 사이의 랜덤 값
        const v = c === 'x' ? r : (r & 0x3 | 0x8); // 4로 시작하는 랜덤 값
        return v.toString(16); // 16진수로 변환
    });
}   // generateUUID() end

// 소켓 초기화 함수
let cheerclientSocket; // WebSocket 인스턴스

window.onbeforeunload = function() {
    // 페이지를 떠날 때 세션 종료 알림
    sendDisconnectMessage();
};

function initializeWebSocket() {
    if (cheerclientSocket && cheerclientSocket.readyState !== WebSocket.CLOSED) {
        console.log("WebSocket이 이미 초기화되어 있습니다.");
        return; // 이미 연결된 경우 종료
    }

    cheerclientSocket = new WebSocket("ws://localhost:8080/cheer/conn");

    cheerclientSocket.onopen = () => {
        console.log("WebSocket 연결 성공");// matchId를 서버에 전송
        readRoom();
    };

    cheerclientSocket.onmessage = (messageEvent) => {
        handleMessage(messageEvent); // 메시지 처리 함수
    };

    cheerclientSocket.onclose = (e) => {
        // setTimeout(function() {
        //     // 재연결 시도
        //     socket = new WebSocket("ws://yourserver.com/socket");
        // }, 1000); // 1초 후 재연결 시도
    };

    cheerclientSocket.onerror = (e) => {
        console.error("WebSocket 오류:", e);
    };
}
initializeWebSocket();

// 메시지 처리 함수
function handleMessage(messageEvent) {
    try {
        let cheerMsgBox = document.querySelector('.cheerMsgBox');
        let msg = JSON.parse(messageEvent.data);
        console.log(msg);
        // 방 목록을 새로 고치기
        if (msg.type === 'read') {
            console.log(msg);
            existRoom(msg); // 방 목록 갱신
        }

        // 회원이 방을 만들었을때
        if (msg.type === 'marker') {
            console.log(msg.message);
            let event = null;
            showCheerRoom(event , msg.message);
        }
        // 회원이 방에 들어갔을 때
        if (msg.type === 'alarm') {
            cheerMsgBox.innerHTML += `<div class="alarmMsgBox"><span>${msg.message}</span></div>`;
            return;
        }
        // 회원이 방을 나갔을때
        if (msg.type === 'out' || msg.type === 'disconnect') {
            console.log(msg.message);
            cheerMsgBox.innerHTML += `<div class="alarmMsgBox"><span>${msg.message}</span></div>`;
            return;
        }
        // 일반 메시지 처리
        if(msg.type === 'msg'){
        cheerMsgBox.innerHTML += `<div class="${msg.from === userName ? 'fromMsgBox' : 'toMsgBox'}">
                                    <div>${msg.from} <i>${msg.date.split(' ')[4]}</i></div>
                                    <div><span>${msg.message}</span></div>
                                </div>`;
        }
    } catch (error) {
        console.error("메시지 처리 중 오류 발생:", error);
    }
}

// [1] clientSocket 의 onclose , onerror , onmessage , onopen 정의 해야한다.
    // (1) WebSocket객체내 onopen 속성은 서버소켓과 접속을 성공했을때 발동되는 함수 정의해서 대입
let separateRoom = "";
// 방 입장
function enterChat(roomID) {
    console.log('enterChat()');
    console.log(roomID)
    separateRoom = roomID;
    // 방 입장했을 때 룸아이디 별로 선별.
    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        let msg = {
            'type': 'alarm',
            'message': `${userName}님이 입장했습니다.` , 
            'userName'  : userName , 
            'roomId' : separateRoom
        };
        try {
            cheerclientSocket.send(JSON.stringify(msg));
        } catch (error) {
            console.error("메시지 전송 중 오류 발생:", error);
        }
    } else {
        console.error("WebSocket이 열리지 않았습니다.");
    }
}

// 메시지 전송 이벤트
function sendM() {
    let cheerMsgInput = document.querySelector('.cheerMsgInput');
    let msg = {
        'type': 'msg',
        'message': cheerMsgInput.value,
        'from': userName,
        'date': new Date().toLocaleString() , 
        'roomId' : separateRoom
    };

    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        try {
            cheerclientSocket.send(JSON.stringify(msg));
            cheerMsgInput.value = "";
        } catch (error) {
            console.error("메시지 전송 중 오류 발생:", error);
        }
    } else {
        console.error("WebSocket이 열리지 않았습니다.");
    }
}

// 채팅방 나가기
function outChat(){
    console.log('outChat()');
    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        let msg = {
            'type': 'out',
            'message': `${userName}님이 퇴장하셨습니다.` , 
            'userName'  : userName , 
            'roomId' : separateRoom
        };
        try {
            cheerclientSocket.send(JSON.stringify(msg));
        } catch (error) {
            console.error("메시지 전송 중 오류 발생:", error);
        }
    } else {
        console.error("WebSocket이 열리지 않았습니다.");
    }
    leaveChat();
}
// 페이지 나갔을 때
function sendDisconnectMessage() {
    const disconnectMessage = JSON.stringify({
        type: "disconnect" ,
        'message': `${userName}님이 퇴장하셨습니다.` , 
        'userName'  : userName , 
        'matchId' : matchId , 
        'roomId' : separateRoom
    });
    cheerclientSocket.send(disconnectMessage);
    leaveChat();
    cheerclientSocket.close(); // 세션을 닫음
}


