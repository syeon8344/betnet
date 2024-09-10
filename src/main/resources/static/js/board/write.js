// 3. 썸머노트 실행
$(document).ready(function() {
    // - 썸머노트 옵션
    let option = {
        height : 500 , // 에디터 높이
        lang : 'ko-KR' // 도움말이 한글로 표기
    }
  $('#summernote').summernote( option );
});


function doBoardWrite(){
    console.log('doBoardWrite()');
    let title = document.querySelector('.btitle').value;
    let content = document.querySelector('.bcontent').value;
    let memberid = 1;
    let teamcode = 1;
    let data = { title : title, content : content,
            memberid:memberid, teamcode: teamcode}
    console.log(data);

    $.ajax({
            async : false,
            method: "post",
            url : "/board/write",
            data : data ,
            success : (r)=>{ console.log(r);
                if( r ){ // 4. 통신 결과에 실행문
                    alert('글쓰기성공');
                    location.href="/board";

                }else{ alert('글쓰기실패'); }
            } ,
            error : (e)=>{ console.log(e); }
    })


}