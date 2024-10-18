console.log("gemin.js")

// 팀 선택 후 동적으로 다른 버튼 생성
//function showExtraButtons(team) {
//    // 기존의 추가 버튼들을 초기화
//    let extraButtonsDiv = document.getElementById("extraButtons");
//    extraButtonsDiv.innerHTML = "";
//    extraButtonsDiv.style.display = "block";
//
//    let extraInfo = {
//        "두산 베어스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "키움 히어로즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "삼성 라이온즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "LG 트윈스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "NC 다이노스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "KIA 타이거즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "롯데 자이언츠": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "SSG 랜더스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "한화 이글스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
//        "KT 위즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"]
//    };
//
//    // extraInfo 객체에서 team에 해당하는 정보를 가져옴
//    let buttons = extraInfo[team];
//    // team에 해당하는 각 정보에 대해 반복 처리
//    buttons.forEach((Info) => {
//        // 버튼 요소 생성
//        let button = document.createElement("button");
//        // 버튼에 클래스 이름 추가
//        button.className = "extra-button";
//        // 버튼의 텍스트 내용을 정보로 설정
//        button.textContent = info;
//        // 버튼 클릭 시 실행될 함수 정의
//        button.onclick = function() {
//            // 클릭된 버튼의 텍스트 내용과 팀을 서버로 전송
//            sendMessage(this.textContent, team);
//        };
//        // 생성된 버튼을 extraButtonsDiv 요소에 추가
//        extraButtonsDiv.appendChild(button);
//    });
//}

// 입력된 키워드메시지를 서버로 보내는 함수
function sendMessage() {
    let message = document.getElementById("#teamButtons");

    // 서버로 GET 요청을 보내기
    $.ajax({
        url: 'http://127.0.0.1:5000/gemini',
        type: 'GET',
        data: { keyword: message },
        success: function(result) {
        console.log(result);  // 서버에서 받은 응답을 콘솔에 출력

        // 응답을 messageBox에 표시
        document.querySelector("#messageBox").innerHTML = `<p>${result.response}</p>`;
        // 스크롤을 맨 아래로 이동
        document.getElementById("messageBox").scrollTop = document.getElementById("messageBox").scrollHeight;
        },
    });
}



