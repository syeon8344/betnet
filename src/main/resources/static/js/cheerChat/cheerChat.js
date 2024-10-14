console.log('cheerChat.js');

var map; // map 변수를 전역으로 선언

// 마커 이미지의 주소
var markerImageUrl = 'http://localhost:8080/img/cheerchat.png' , 
    markerImageSize = new kakao.maps.Size(40, 42), // 마커 이미지의 크기
    markerImageOptions = { 
        offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
    };

// 지도에 표시된 마커 객체를 가지고 있을 배열입니다
var markers = [];

// window.onload?
// 웹 페이지의 모든 요소가 완전히 로드된 후에 실행되는 JavaScript 이벤트 
// 즉, HTML 문서와 모든 이미지, 스타일시트, 스크립트 등 모든 리소스가 완전히 로드된 상태에서 코드를 실행하도록 보장
// window.onload = function() {
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

        // 배열에 추가된 마커들을 지도에 표시
        addMarker(new kakao.maps.LatLng(33.450701, 126.570667));
    });

// };


function addMarker(position) {
    // console.log(markers); // markers 배열 상태 확인
    var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);
    
    var marker = new kakao.maps.Marker({
        position: position,
        image: markerImage
    });

    marker.setMap(map);
    markers.push(marker); 

    // // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
    // var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    // iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

    // // 인포윈도우를 생성합니다
    // var infowindow = new kakao.maps.InfoWindow({
    // content : iwContent,
    // removable : iwRemoveable
    // });

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        // 마커 위에 인포윈도우를 표시합니다
        // infowindow.open(map, marker);  
        alert("? 채팅방으로 입장합니다.")
    });
}

// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
function setMarkers(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }            
}

// "마커 보이기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에 표시하는 함수입니다
function showMarkers() {
    setMarkers(map);    
}

// "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
function hideMarkers() {
    setMarkers(null);    
}
