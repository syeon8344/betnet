

function survey(){
    // 각 질문에 대해 선택된 값 가져오기
    const questions = ['question1', 'question2', 'question3', 'question4', 'question5',
         'question6', 'question7', 'question8', 'question9', 'question10'];
    let result = '';

    // 질문 배열을 순회하며 선택된 라디오 버튼의 값을 찾음
    questions.forEach((question) => {
        const selectedRadio = document.querySelector(`input[name="${question}"]:checked`);
        if (selectedRadio) {
            result += `${question}: ${selectedRadio.value}\n`; // 선택된 값 대입
        } else {
            result += `${question}: 선택되지 않음\n`;
        }
    });
    alert(result);


    $.ajax({
        async : false,
        method : "post",
        url : "/survey/save",
        data : result,
        success :(result2 =>{ console.log(result2)
         
     

        }) // successs
        
    
    }) // ajax
} // survey

