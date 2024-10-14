// 메시지를 채팅박스에 추가하는 함수
function appendMessage(message, sender) {
    var chatbox = document.getElementById('chatbox');
    var messageElement = document.createElement('div');
    messageElement.className = sender;
    messageElement.textContent = message;
    chatbox.appendChild(messageElement);
}

// 구단 버튼 클릭 시 호출되는 함수
function sendKeyword(keyword) {
    // 사용자가 선택한 구단 메시지 추가
    appendMessage("선택한 구단: " + keyword, 'userMessage');

    // AJAX 요청을 통해 서버로 키워드 보내기
    $.ajax({
        url: "http://127.0.0.1:5000/gemini/chatbotBox",  // 서버 엔드포인트 (Flask 서버와 연결)
        type: "GET",
        contentType: "application/json",
        data: JSON.stringify({ "keyword": keyword }), // 구단명 전달
        success: (result) => {
            if (result == ""){

            },
        error: (error) => {
        console.error("에러 발생:", error);
        appendMessage("서버와의 연결에 실패했습니다.", 'botMessage');
        }
    });
}