// board.js 에서 view 페이지 이동 코드  <th> <a href="/board/view?bno=${ b.bno }">${ b.btitle }</a>
    // JS 코드가 HTML 를 만들어내고 사용자는 표현된 HTML 에서 a 클릭이벤트
    // <a href="/board/view?bno=3">안녕하세요</a>    ---> /board/view?bno=3

// URL 상의 쿼리스트링 매개변수를 JS에서 꺼내는방법
    // JAVA SPRING 에서 HTTP URL 상의 쿼리스트링 매개변수를 꺼내는 방법
        // @RequestParam 이용한 쿼리스트링 매개변수 매핑
    // JS 에서 HTTP URL 상의 쿼리스트링 매개변수를 꺼내는 방법
        // 1. new URL( location.href )  : 현재 JS가 포함된 URL 경로 의 정보가 담긴 객체 호출
        // 2. .searchParams;            : 현재경로상의 쿼리스트링 매개변수 속성 호출
        // 3. .get( key )               : 쿼리스트링 매개변수의 key에 해당 하는 value 호출
let bno = new URL( location.href ).searchParams.get('bno'); // 현재 URL 경로상의 bno 값 호출
console.log( bno );


boardView( bno )
function boardView( bno ){
    let board = {}
    $.ajax({ // AJAX
        async : false , method : "get" ,
        url :"/board/find/bno", data : { bno : bno } ,
        success : r => { console.log(r); board = r}
    }) // AJAX END
    document.querySelector('.teamname').innerHTML = `${ board.teamname }`;
    document.querySelector('.etcBox').innerHTML = `<span> 작성자 ${ board.memberid } </span>
                                                    <span> 조회수 ${ board.views } </span>
                                                    <span> 작성일 ${ board.createdat } </span>`;

    document.querySelector('.title').innerHTML = `${ board.title }`;
    document.querySelector('.content').innerHTML = `${ board.content }`;


    document.querySelector('.btnBox').innerHTML =
            `
            <button type="button" class="btn btn-primary" >수정</button>
            <button type="button" class="btn btn-primary" onclick="bDelete(${bno})" >삭제</button>
            `;
}

function bDelete( bno ){
    console.log(bno);
    console.log('bDelete()');
    $.ajax({
            async : false,
            method : 'delete',
            data : {postid : bno} ,
            url : "/board/delete",
            success:(r) =>{
                console.log(r);
                if(r){
                    alert('삭제성공');
                    location.href="/board"
                }else{
                    alert('본인이 작성한 글만 삭제 가능합니다.');
                }
            } ,
            error : (e) => {
                console.log(e)
            }
    })
}