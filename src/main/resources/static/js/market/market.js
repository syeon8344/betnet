/*
티켓단체구매 -> (중고나라) 사고팔기
야구 관련 중고물건 사고팔기 (회원제)
글 클릭시 나오는 내용:
사진 1~3장 (멀티파트 사용, 써머노트와는 따로)
소개글 (써머노트)
판매금액
회원이 접속했을 시에 뜨는 [연락? 버튼]
눌렀을 시 상대 회원에게 쪽지처럼 템플릿 메시지 전송- 
"{name}님이 회원님의 {prodname} 판매물품 구매 의사가 있습니다. {name}님의 연락처는 {phone}입니다."
현재 몇명이 버튼을 눌렀는지 버튼 옆에 출력
DB:
쪽지 느낌 테이블: 번호 메시지 보낸사람 받는사람 날짜
게시글 테이블: 글번호 제목 작성자 구매제안수 조회수 날짜
*/

console.log("market.js");

// 현재 페이지 번호를 쿼리스트링으로
let urlParams = new URL(location.href).searchParams;
let pageNo = parseInt(urlParams.get("page")) //페이지번호
//let category = parseInt(urlParams.get("cat")) //카테고리
if (isNaN(pageNo)){pageNo=1}

// 페이지화 변수
let totalBoardSize;
let totalPage;
let startBtn;
let endBtn;

// 페이지 정보 관리 객체
let pageInfo ={
    page : 1,   // 1. page : 현재페이지 기본값 : 1
    category : 0,   // 2. bcno : 카테고리번호 기본값 : 0
    searchKeyword : ''      // 4. searchKeyword : 검색필드값 기본값 : ""
}

//페이지 오픈시 자동 실행
getall();
paging();

// 5. 검색 초기화
function searchClear(){
    // 입력창 초기화
    document.querySelector("#searchKeyword").value = "";
    // 전역변수 초기화
    pageInfo.searchKeyword = '';
    getall()
}

// 한 화면에 뜨는 게시글 수
const size = 20;
sizeSelect.addEventListener('change', function() {
    size = this.value;  // 선택된 값 가져오기
});

//검색버튼 클릭
function search(){
    let mkState = document.querySelector('#mkState');
    let sKeyword = document.querySelector("#searchKeyword").value
    pageInfo.searchKeyword = sKeyword;
    getall()
}

//게시글 출력
function getall(){ //getall(page, bcno)
    let board=document.querySelector('#list');
    let html='';
    getCategory()
    $.ajax({
        async : false,
        method:'get',
        url:"/board/all",
        data : {bcno : pageInfo.category, page : pageNo, searchKey : pageInfo.searchKey, searchKeyword : pageInfo.searchKeyword},
        success:result =>{
            totalBoardSize = result.totalBoardSize;
            totalPage = result.totalPage;
            startBtn = result.startBtn;
            endBtn = result.endBtn;
            result.data.forEach(result =>{
                html+=`<tr>
                        <th>${result.bno}</th>
                        <td>${result.bcname}</td>
                        <td><a href="/board/getread?page=${pageNo}&bno=${result.bno}" >${result.btitle}</a></td>
                        <td>${result.id}</td>
                        <td>${result.bdate}</td>
                        <td>${result.bview}</td>
                    </tr>`;
            });
        }
    })
    board.innerHTML=html;
    paging()
}

// 게시글 쓰기 버튼
function boardwrite(){
    console.log('boardwrite()');
    location.href='/board/write';
}

// 페이지 번호 출력
function paging(){
    let pageHtml = ``;
    // 이전 버튼
    pageHtml += `<li class="page-item"><a class="page-link" href="/board/getall?page=${pageNo-1 == 0 ? 1 : pageNo-1}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    </a></li>`
    // 페이지버튼
        // 페이지마다 시작 버튼 수
        // 페이지마다 끝 버튼 수
        // 최대 페이지 수
    for (i=startBtn;i<=endBtn;i++){
        pageHtml += `<li class="page-item"><a class="page-link ${i==pageNo?'active':''}" href="/board/getall?page=${i}">${i}</a></li>`
        
    }   
    // 다음버튼
    pageHtml += `<li class="page-item"><a class="page-link" href="/board/getall?page=${pageNo+1 <totalPage ? pageNo+1 : totalPage}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    </a></li>`
    document.querySelector(".pagination").innerHTML = pageHtml;
}