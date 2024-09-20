console.log("gameDetailList.js");

let listid = new URL( location.href ).searchParams.get('listid'); // 현재 URL 경로상의 bno 값 호출
console.log( listid );

printListid(listid);
function printListid(listid){
    let html = `<h3> ${listid}번 구매내역 </h3>`;
    document.querySelector(".printListid").innerHTML = html;
}

getSchedule();
function getSchedule(){
    let gameSchedule = []
    let myGame = []
    let printMyGame = []
    $.ajax({
        async : false , 
        method: "GET",
        url: "http://127.0.0.1:5000/getschedule",
        success: (result) => {
            result = JSON.parse(result);
            console.log(result);
            // 경기일정 가지고 와서 배열에 저장 // 경기코드랑 for문 돌려서 그 열 가지고 오기.
            gameSchedule = result;
        }
    })
    $.ajax({
        async : false , 
        method: "GET",
        url: "/game/detail",
        data : {listid : listid} , 
        success: (result) => {
            console.log(result);
            // 게임 상세출력 가져와서 배열에 저장하기
            myGame = result;
        }
    })
    console.log(gameSchedule);
    console.log(myGame);
    for(let i = 0; i < myGame.length; i++){
        for(let j = 0; j < gameSchedule.length; j++){
            if(myGame[i].matchid == gameSchedule[j].경기코드){
                console.log(myGame[i])
                console.log(gameSchedule[j])
                // 한 줄에 출력
                const mergedObject = Object.assign({}, myGame[i], gameSchedule[j]);
                printMyGame.push(mergedObject);
            }
        }
    }
    console.log(printMyGame);
    let gameDetailListBox = document.querySelector(".gameDetailListBox");
    let html = ``;
    for(let i = 0; i < printMyGame.length; i++){
        let winandlossStr = "";
        if(printMyGame[i].winandloss == 1){
            winandlossStr = "승";
        }else{
            winandlossStr = "패";
        }
        html += `<tr>
                    <td> ${i+1} </td> 
                    <td> ${printMyGame[i].월}/${printMyGame[i].일} ${printMyGame[i].시작시간} </td> 
                    <td> KBO </td> <td> ${printMyGame[i].경기코드} </td> 
                    <td> ${printMyGame[i].홈팀명} vs ${printMyGame[i].어웨이팀명} </td> <td> ${winandlossStr}/배당률 </td> <td> ${printMyGame[i].월}/${printMyGame[i].일} ${printMyGame[i].시작시간} </td>
                </tr>`;
    }
    gameDetailListBox.innerHTML = html;
}