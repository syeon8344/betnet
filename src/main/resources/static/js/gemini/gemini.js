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
    // 키워드 메시지 버튼 클릭 시 로딩 스피너 표시
    document.querySelector('#loadingSpinner').style.display = 'block';
}
function sendMessage(message) {
    console.log(message);
    // 로딩 스피너를 표시
    document.querySelector('#loadingSpinner').style.display = 'block';

    // 서버로 GET 요청을 보내기
    $.ajax({
        url: 'http://127.0.0.1:5000/gemini',
        type: 'GET',
        data: { keyword: message },
        success: function(result) {
            console.log(result);  // 서버에서 받은 응답을 콘솔에 출력

            // 응답 텍스트 가져오기
            let responseText = result.response;

            // **로 구분된 텍스트를 처리 (1개면 그대로 출력, 2개면 줄바꿈 처리)
            let formattedResponse = responseText.split("**").map((part, index) => {
                if (index > 0) {
                    return `\n${part}`;  // 2개 이상의 **가 있을 때 줄바꿈
                }
                return part;
            }).join('');

            // * 문자를 제거한 상태로 응답을 표시
            let finalResponse = formattedResponse.replace(/\*/g, '');  // * 문자 제거

            // 키워드 메시지 박스에 출력
            document.querySelector("#messageBox").innerHTML += `<p>검색한 키워드 : ${message}</p>`;

            // 한 글자씩 출력하는 함수
            function printResponseCharacterByCharacter(response, interval) {
                let charIndex = 0;  // 현재 글자의 인덱스
                const messageBox = document.querySelector("#messageBox");
                const totalLength = response.length;

                // 일정 간격으로 한 글자씩 출력
                const intervalId = setInterval(() => {
                    if (charIndex < totalLength) {
                        messageBox.innerHTML += response.charAt(charIndex);  // 한 글자씩 추가

                        // 전체 페이지 스크롤을 부드럽게 하단으로 이동
                        window.scroll({
                            top: document.body.scrollHeight,
                            left: 0,
                            behavior: 'smooth'  // 부드럽게 스크롤 이동
                        });
                        charIndex++;
                    } else {
                        clearInterval(intervalId);  // 모든 글자가 출력되면 인터벌 정지
                        // 로딩 스피너 숨기기
                        document.querySelector('#loadingSpinner').style.display = 'none';
                    }
                }, interval);  // 100ms 간격으로 한 글자씩 출력

            }

            // 응답을 한 글자씩 출력, 100ms 간격으로 출력
            // 응답을 한 글자씩 출력하고 스크롤을 천천히 하단으로 이동
            printResponseCharacterByCharacter(finalResponse, 60, 600);  // 텍스트는 100ms 간격, 스크롤은 300ms 간격
            document.querySelector('#loadingSpinner').style.display = 'none';
        },
        error: function() {
            console.error("서버에 문제가 발생했습니다.");
            document.querySelector('#loadingSpinner').style.display = 'none';
        }
    });
}



