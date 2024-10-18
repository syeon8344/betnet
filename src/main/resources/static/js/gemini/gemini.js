console.log("gemin.js")

function sendMessages( message ) {
    let subButtons = document.querySelector('#subButtons')

    let html = '';
    if (message === '두산 베어스') {
        const buttons = [
            { text: '역사', keyword: '두산 베어스 역사' },
            { text: '역대 선수', keyword: '두산 베어스 역대 선수단' },
            { text: '역대 기록', keyword: '두산 베어스 역대 기록' },
            { text: '역대 감독', keyword: '두산 베어스 역대 감독' },
            { text: '구장 위치', keyword: '두산 베어스 구장 위치' },
            { text: '역대 성적', keyword: '두산 베어스 역대 성적' },
            { text: '응원가', keyword: '두산 베어스 응원가' },
            { text: '팬 문화', keyword: '두산 베어스 팬 문화' },
            { text: '구장 안 먹거리', keyword: '두산 베어스 구장 안 먹거리' },
            { text: '역대 코치들', keyword: '두산 베어스 역대 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }
    if (message === '키움 히어로즈') {
        const buttons = [
            { text: '역사', keyword: '키움 히어로즈 역사' },
            { text: '역대 선수', keyword: '키움 히어로즈 역대 선수단' },
            { text: '역대 기록', keyword: '키움 히어로즈 역대 기록' },
            { text: '역대 감독', keyword: '키움 히어로즈 역대 감독' },
            { text: '구장 위치', keyword: '키움 히어로즈 구장 위치' },
            { text: '역대 성적', keyword: '키움 히어로즈 역대 성적' },
            { text: '응원가', keyword: '키움 히어로즈 응원가' },
            { text: '팬 문화', keyword: '키움 히어로즈 팬 문화' },
            { text: '구장 안 먹거리', keyword: '키움 히어로즈 구장 안 먹거리' },
            { text: '역대 코치들', keyword: '키움 히어로즈 역대 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }
    // 삼성 라이온즈
    if (message === '삼성 라이온즈') {
        const buttons = [
            { text: '역사', keyword: '삼성 라이온즈 역사' },
            { text: '역대 선수', keyword: '삼성 라이온즈 역대 선수단' },
            { text: '역대 기록', keyword: '삼성 라이온즈 역대 기록' },
            { text: '역대 감독', keyword: '삼성 라이온즈 역대 감독' },
            { text: '구장 위치', keyword: '삼성 라이온즈 구장 위치' },
            { text: '역대 성적', keyword: '삼성 라이온즈 역대 성적' },
            { text: '응원가', keyword: '삼성 라이온즈 응원가' },
            { text: '팬 문화', keyword: '삼성 라이온즈 팬 문화' },
            { text: '구장 안 먹거리', keyword: '삼성 라이온즈 구장 안 먹거리' },
            { text: '역대 코치들', keyword: '삼성 라이온즈 역대 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }
    // LG 트윈스
    if (message === 'LG 트윈스') {
        const buttons = [
            { text: '역사', keyword: 'LG 트윈스 역사' },
            { text: '역대 선수', keyword: 'LG 트윈스 역대 선수단' },
            { text: '역대 기록', keyword: 'LG 트윈스 역대 기록' },
            { text: '역대 감독', keyword: 'LG 트윈스 역대 감독' },
            { text: '구장 위치', keyword: 'LG 트윈스 구장 위치' },
            { text: '역대 성적', keyword: 'LG 트윈스 역대 성적' },
            { text: '응원가', keyword: 'LG 트윈스 응원가' },
            { text: '팬 문화', keyword: 'LG 트윈스 팬 문화' },
            { text: '구장 안 먹거리', keyword: 'LG 트윈스 구장 안 먹거리' },
            { text: '역대 코치들', keyword: 'LG 트윈스 역대 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }
   // NC 다이노스
   if (message === 'NC 다이노스') {console.log(message);
       const buttons = [
           { text: '역사', keyword: 'NC 다이노스 역사' },
           { text: '역대 선수', keyword: 'NC 다이노스 역대 선수단' },
           { text: '역대 기록', keyword: 'NC 다이노스 역대 기록' },
           { text: '역대 감독', keyword: 'NC 다이노스 역대 감독' },
           { text: '구장 위치', keyword: 'NC 다이노스 구장 위치' },
           { text: '역대 성적', keyword: 'NC 다이노스 역대 성적' },
           { text: '응원가', keyword: 'NC 다이노스 응원가' },
           { text: '팬 문화', keyword: 'NC 다이노스 팬 문화' },
           { text: '구장 안 먹거리', keyword: 'NC 다이노스 구장 안 먹거리' },
           { text: '역대 코치들', keyword: 'NC 다이노스 역대 코치들' }
       ];

       buttons.forEach(button => {
           html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
       });
   }
    // 기아 타이거즈
    if (message === 'KIA 타이거즈') {console.log(message);
        const buttons = [
            { text: '역사', keyword: '기아 타이거즈 역사' },
            { text: '역대 선수', keyword: '기아 타이거즈 역대 선수단' },
            { text: '역대 기록', keyword: '기아 타이거즈 역대 기록' },
            { text: '역대 감독', keyword: '기아 타이거즈 역대 감독' },
            { text: '구장 위치', keyword: '기아 타이거즈 구장 위치' },
            { text: '역대 성적', keyword: '기아 타이거즈 역대 성적' },
            { text: '응원가', keyword: '기아 타이거즈 응원가' },
            { text: '팬 문화', keyword: '기아 타이거즈 팬 문화' },
            { text: '구장 안 먹거리', keyword: '기아 타이거즈 구장 안 먹거리' },
            { text: '역대 코치들', keyword: '기아 타이거즈 역대 코치들' }
        ];

       buttons.forEach(button => {
                   html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
       });
    }
    // 롯데 자이언츠
    if (message === '롯데 자이언츠') {
        const buttons = [
            { text: '역사', keyword: '롯데 자이언츠 역사' },
            { text: '역대 선수', keyword: '롯데 자이언츠 역대 선수단' },
            { text: '역대 기록', keyword: '롯데 자이언츠 역대 기록' },
            { text: '역대 감독', keyword: '롯데 자이언츠 역대 감독' },
            { text: '구장 위치', keyword: '롯데 자이언츠 구장 위치' },
            { text: '역대 성적', keyword: '롯데 자이언츠 역대 성적' },
            { text: '응원가', keyword: '롯데 자이언츠 응원가' },
            { text: '팬 문화', keyword: '롯데 자이언츠 팬 문화' },
            { text: '구장 안 먹거리', keyword: '롯데 자이언츠 구장 안 먹거리' },
            { text: '역대 코치들', keyword: '롯데 자이언츠 역대 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }
    // SSG 랜더스
    if (message === 'SSG 랜더스') {
        const buttons = [
            { text: '역사', keyword: 'SSG 랜더스 역사' },
            { text: '역대 선수', keyword: 'SSG 랜더스 역대 선수단' },
            { text: '역대 기록', keyword: 'SSG 랜더스 역대 기록' },
            { text: '역대 감독', keyword: 'SSG 랜더스 역대 감독' },
            { text: '구장 위치', keyword: 'SSG 랜더스 구장 위치' },
            { text: '역대 성적', keyword: 'SSG 랜더스 역대 성적' },
            { text: '응원가', keyword: 'SSG 랜더스 응원가' },
            { text: '팬 문화', keyword: 'SSG 랜더스 팬 문화' },
            { text: '구장 안 먹거리', keyword: 'SSG 랜더스 구장 안 먹거리' },
            { text: '역대 코치들', keyword: 'SSG 랜더스 역대 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }
    // 한화 이글스
    if (message === '한화 이글스') {
        const buttons = [
            { text: '역사', keyword: '한화 이글스 역사' },
            { text: '역대 선수', keyword: '한화 이글스 역대 선수단' },
            { text: '역대 기록', keyword: '한화 이글스 역대 기록' },
            { text: '역대 감독', keyword: '한화 이글스 역대 감독' },
            { text: '구장 위치', keyword: '한화 이글스 구장 위치' },
            { text: '역대 성적', keyword: '한화 이글스 역대 성적' },
            { text: '응원가', keyword: '한화 이글스 응원가' },
            { text: '팬 문화', keyword: '한화 이글스 팬 문화' },
            { text: '구장 안 먹거리', keyword: '한화 이글스 구장 안 먹거리' },
            { text: '역대 코치들', keyword: '한화 이글스 역대 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }
    // KT 위즈
    if (message === 'KT 위즈') {
        const buttons = [
            { text: '역사', keyword: 'KT 위즈 역사' },
            { text: '역대 선수', keyword: 'KT 위즈 역대 선수단' },
            { text: '역대 기록', keyword: 'KT 위즈 역대 기록' },
            { text: '역대 감독', keyword: 'KT 위즈 역대 감독' },
            { text: '구장 위치', keyword: 'KT 위즈 구장 위치' },
            { text: '역대 성적', keyword: 'KT 위즈 역대 성적' },
            { text: '응원가', keyword: 'KT 위즈 시즌별 전체 응원가' },
            { text: '팬 문화', keyword: 'KT 위즈 팬 문화' },
            { text: '구장 안 음식점', keyword: 'KT 위즈 구장 안 음식점' },
            { text: '역대 코치들', keyword: 'KT 위즈 역대 전체 코치들' }
        ];

        buttons.forEach(button => {
            html += `<button class="team-button" onclick="sendMessage('${button.keyword}')">${button.text}</button>`;
        });
    }



    subButtons.innerHTML = html;
}
// 입력된 키워드메시지를 서버로 보내는 함수
function sendMessage( message ) {
    //let message = document.getElementById("#teamButtons");
    console.log( message );
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



