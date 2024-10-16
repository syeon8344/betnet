// 메시지를 대화 상자에 추가하는 함수
function addMessage(message, type) {
    let messageElement = document.createElement("p");
    messageElement.textContent = message;
    messageElement.classList.add(type);
    document.getElementById("messageBox").appendChild(messageElement);
    document.getElementById("messageBox").scrollTop = document.getElementById("messageBox").scrollHeight;
}

// 팀 선택 후 동적으로 다른 버튼 생성
function showExtraButtons(team) {
    // 기존의 추가 버튼들을 초기화
    let extraButtonsDiv = document.getElementById("extraButtons");
    extraButtonsDiv.innerHTML = "";
    extraButtonsDiv.style.display = "block";

    let extraInfo = {
        "두산 베어스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "키움 히어로즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "삼성 라이온즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "LG 트윈스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "NC 다이노스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "KIA 타이거즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "롯데 자이언츠": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "SSG 랜더스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "한화 이글스": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"],
        "KT 위즈": ["선수 정보", "경기 일정", "최근 경기 결과", "역대 기록", "역사", "연혁", "구장 위치", "굿즈"]
    };

    let buttons = extraInfo[team];
    buttons.forEach(function(info) {
        let button = document.createElement("button");
        button.className = "extra-button";
        button.textContent = info;
        button.onclick = function() {
            sendMessage(this.textContent, team);  // 클릭 시 해당 정보를 서버로 보냄
        };
        extraButtonsDiv.appendChild(button);
    });
}

// 메시지를 서버로 보내는 함수 (jQuery의 $.ajax 사용)
function sendMessage(info, team) {
    let message = info || team;  // 정보가 있다면 정보, 없으면 팀명을 전송
    addMessage("You: " + message, "user");

    // jQuery의 $.ajax()를 사용한 AJAX 요청
    $.ajax({
        url: 'http://127.0.0.1:5000/gemini',  // 서버의 엔드포인트
        type: 'GET',
        contentType: 'application/json',
        data: JSON.stringify({
            keyword: message,
            team: team
        }),
        success: function(response) {
            // 서버 응답을 대화 상자에 표시
            if (response.response) {
                addMessage("Bot: " + response.response, "bot");
            }
        },
        error: function(xhr, status, error) {
            console.error("Error communicating with backend:", error);
            addMessage("Bot: 오류가 발생했습니다. 나중에 다시 시도해주세요.", "bot");
        }
    });
}
