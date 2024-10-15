console.log('cheerChat.js');
let cheerclientSocket; 
console.log(cheerclientSocket);

// 로그인 체크
let memberid = ``
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
                memberid = `${result.userName}(${result.teamName})`
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

// 마커 추가 함수
function addMarker(position) {
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

// // "마커 보이기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에 표시하는 함수입니다
// function showMarkers() {
//     setMarkers(map);    
// }

// // "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
// function hideMarkers() {
//     setMarkers(null);    
// }
// 방번호 만들기
function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0; // 0-15 사이의 랜덤 값
        const v = c === 'x' ? r : (r & 0x3 | 0x8); // 4로 시작하는 랜덤 값
        return v.toString(16); // 16진수로 변환
    });
}   // generateUUID() end

// 방만들기
function addRoom(position){
    let roomTitle = prompt("채팅방 이름을 입력해주세요.")
    alert("채팅방을 생성합니다.");
    let roomID = generateUUID();
    cheerclientSocket = new WebSocket("ws://localhost:8080/cheer/conn");
    cheerclientSocket.onopen = ( e ) => {
        // 1. 클라이언트 서버와 접속을 성공했을때 (알림) 메시지 구성
        let msg = {
            'type' : 'marker' , // (알림)메시지
            'message' : roomID , position
        }
        console.log(msg);
        // 2. 보내기
        cheerclientSocket.send( JSON.stringify( msg ) );
    }
    return roomTitle;
}
// [1] clientSocket 의 onclose , onerror , onmessage , onopen 정의 해야한다.
    // (1) WebSocket객체내 onopen 속성은 서버소켓과 접속을 성공했을때 발동되는 함수 정의해서 대입
    // 서버 소켓이 들어왔을때 
function enterChat(){
    cheerclientSocket = new WebSocket("ws://localhost:8080/cheer/conn");
    cheerclientSocket.onopen = ( e ) => {
        // 1. 클라이언트 서버와 접속을 성공했을때 (알림) 메시지 구성
        let msg = {
            'type' : 'alarm' , // (알림)메시지
            'message' : `${ nickName }님이 입장 했습니다.`
        }
        // 2. 보내기
        cheerclientSocket.send( JSON.stringify( msg ) );
    }
     // (2) WebSocket객체내 onmessage 속성은 서버소켓이 메시지를 보내왔을때 발동되는 함수 정의해서 대입
    cheerclientSocket.onmessage = ( messageEvent ) => { // e : 매개변수
    console.log( messageEvent );
    console.log( messageEvent.data ) // 받은 내용물이 들어있는 속성
    // 1. 받은 메시지를 출력할 HTML 호출
    let cheerMsgBox = document.querySelector('.cheerMsgBox')
    // 2. 받은 메시지의 내용물( .data 속성) 를 HTML 에 대입
    msg = JSON.parse( messageEvent.data  )  // - JSON.parse( 문자열  ) : 문자열타입(JSON형식) --> js객체타입(JSON형식)
        // 2-1 알람 메시지
        if( msg.type == 'alarm'){
            cheerMsgBox.innerHTML += `<div class="alarmMsgBox">
                                    <span>${ msg.message }</span>
                                </div>`;
            return // 알람 메시지 HTML 출력후 일반 메시지 HTML 코드가 실행되지 않도록 함수 종료
        }
            // 2-2 일반 메시지
        if( msg.from == nickName  ){ // - 내가 보낸 메시지
            cheerMsgBox.innerHTML += `<div class="fromMsgBox">
                                    <div> <i> ${ msg.date.split(' ')[4] } </i>  ${ msg.from } </div>
                                    <div>
                                        <span> ${ msg.message } </span>
                                    </div>
                                </div>`
        }else{  // - 남이 보낸 메시지
            cheerMsgBox.innerHTML += `<div class="toMsgBox">
                                    <div> ${ msg.from }  <i> ${ msg.date.split(' ')[4] } </i> </div>
                                    <div>
                                        <span> ${ msg.message } </span>
                                    </div>
                                </div>`
        }
    }
}

// [2] 메시지 전송 이벤트
function sendM(){
    //
    let cheerMsgInput = document.querySelector('.cheerMsgInput')
    // - (일반)메시지 내용 들을 객체 형식으로 구성
    let msg = {
        'type' : 'msg' ,  // (일반)메시지
        'message' : cheerMsgInput.value ,
        'from' : nickName ,
        'date' : new Date().toLocaleString()
    }
    // - 현재 클라이언트소켓과 연결 유지된 서버소켓에게 메시지 전송 # .send( ) : 서버소켓에게 메시지 전송
    cheerclientSocket.send( JSON.stringify( msg )  );
        // msg ----> [object Object]
        // JSON.stringify( msg ) ---> {"message":"DFGDFG","from":"익명209","date":"2024. 9. 12. 오후 12:18:21"}

        // - JSON.stringify( js객체  ) : js객체타입(JSON형식) --> 문자열타입(JSON형식)
        // -  "3"(문자열타입 숫자형식) VS 3(정수타입 숫자형식)
        // -  { key : value } (객체타입 JSON형식 )  vs   "{ key : value }" ( 문자열타입 JSON형식 )
    //
    cheerMsgInput.value = "";
}
// 채팅방 생성
function createChatRoom() {
    let roomId = Object.keys(chatRooms).length + 1; // 새로운 방 ID
    chatRooms[roomId] = []; // 새로운 방 초기화
    console.log(`채팅방 ${roomId}가 생성되었습니다.`);
    return roomId;
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

