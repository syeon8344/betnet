console.log('cheerChat.js');

// 로그인 체크
let memberid = ``
let userName = ``;

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

kakao.maps.load(function () {
    // 지도를 생성합니다
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

    map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 지도를 클릭했을때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
        // 클릭한 위치에 마커를 표시합니다 
        addMarker(mouseEvent.latLng);             
    });

    // // 배열에 추가된 마커들을 지도에 표시
    // addMarker(new kakao.maps.LatLng(33.450701, 126.570667));
});

// 기존 방 불러오기
function readRoom(){
    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        let msg = {
            'type': 'read',
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
        
            let position = {}
            position.latitude = value.latitude; // 위도
            position.longitude = value.longitude; // 경도
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
                alert(`'${value.roomTitle}'방으로 입장합니다.`);
                enterChat();
                showCheerRoom(event);
        });
    });       
} // existRoom() end

// 마커 추가 함수
function addMarker(position) {
    console.log('addmarker')
    console.log(position)
   
    var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);
    
    var marker = new kakao.maps.Marker({
        position: position, // 마커 위치 위도 경도
        image: markerImage  // 마커 이미지
    });

    marker.setMap(map);
    markers.push(marker); 

    let roomTitle = addRoom(position);  // 방만들기 함수

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function(event) {
        // 마커 위에 인포윈도우를 표시합니다
        // infowindow.open(map, marker);  
        alert(`'${roomTitle}'방으로 입장합니다.`);
        enterChat();
        showCheerRoom(event);
    });
}

// cheerRoom 표시 함수
function showCheerRoom(event) {
    var cheerRoom = document.getElementById('cheerRoom');
    cheerRoom.style.display = 'block'; // cheerRoom 표시
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

let cheerclientSocket; // WebSocket 인스턴스

function initializeWebSocket() {
    if (cheerclientSocket && cheerclientSocket.readyState !== WebSocket.CLOSED) {
        console.log("WebSocket이 이미 초기화되어 있습니다.");
        return; // 이미 연결된 경우 종료
    }

    cheerclientSocket = new WebSocket("ws://localhost:8080/cheer/conn");

    cheerclientSocket.onopen = () => {
        console.log("WebSocket 연결 성공");
        readRoom();
    };

    cheerclientSocket.onmessage = (messageEvent) => {
        handleMessage(messageEvent); // 메시지 처리 함수
    };

    cheerclientSocket.onclose = (e) => {
        console.log("WebSocket 연결 종료. 재연결 시도 중...");
        setTimeout(initializeWebSocket, 5000); // 5초 후에 재연결
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

        if (msg.type === 'alarm') {
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
        // 마커 메세지 처리
        if (msg[0].type === null) {
            console.log(msg);
            console.log("null");
            existRoom(msg);
        }
    } catch (error) {
        console.error("메시지 처리 중 오류 발생:", error);
    }
}

// 방 만들기
function addRoom(position) {
    console.log(position)
    let roomTitle = prompt("채팅방 이름을 입력해주세요.");
    alert("채팅방을 생성합니다.");
    let roomID = generateUUID();

    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        let msg = {
            'type': 'marker',
            'message': roomID,
            'position' : position , 
            'date': new Date().toLocaleString() , 
            'memberid' : memberid , 
            'roomTitle' : roomTitle
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
    return roomTitle;
}

// [1] clientSocket 의 onclose , onerror , onmessage , onopen 정의 해야한다.
    // (1) WebSocket객체내 onopen 속성은 서버소켓과 접속을 성공했을때 발동되는 함수 정의해서 대입
// 방 입장
function enterChat() {
    if (cheerclientSocket && cheerclientSocket.readyState === WebSocket.OPEN) {
        let msg = {
            'type': 'alarm',
            'message': `${userName}님이 입장했습니다.`
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
        'userName' : userName
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

// 채팅방 목록을 표시하는 함수
function showChatRooms() {
    let roomList = document.querySelector('.roomList');
    roomList.innerHTML = ''; // 기존 목록 초기화

    for (let roomId in chatRooms) {
        roomList.innerHTML += `<button onclick="openChat(${roomId})">채팅방 ${roomId}</button>`;
    }
}

// 메시지 로드 (기본 기능, 필요에 따라 구현)
function loadMessages(roomId) {
    let msgBox = document.querySelector('.msgBox');
    msgBox.innerHTML = chatRooms[roomId].map(msg => `
        <div>${msg.from}: ${msg.message}</div>
    `).join('');
}

