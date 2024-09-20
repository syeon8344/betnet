console.log("gameDetailList.js");

let listid = new URL( location.href ).searchParams.get('listid'); // 현재 URL 경로상의 bno 값 호출
console.log( listid );

getGameList();
function getGameList(){
    $.ajax({
        async : false , 
        method : "get" , 
        url : "/game/getlist" , 
        success : (r) => {
            console.log(r);
            let gameListBox = document.querySelector('.gameListBox');
            let html = ``;
            r.forEach(dto => {
                let point = Math.abs(dto.pointChange);
                html += `<tr>
                            <td> <a href="/game/view?listid=${dto.listid}"> ${dto.listid} </td> <td> ${dto.logDate} </td> <td> ${point} </td> <td> ${dto.gamestate} </td>
                        </tr>`;
            });
            gameListBox.innerHTML = html;
        } , 
        error : (e) => {
            console.log(e);
        }
    })
}