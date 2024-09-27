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

let currentPage = 0;
const size = 20;

function loadProducts(page) {
    $.ajax({
        url: `/api/products?page=${page}&size=${size}`,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            const products = data.content;
            const totalPages = data.totalPages;

            const tbody = document.querySelector('#product-table tbody');
            let rows = ''; // 빈 문자열로 초기화

            products.forEach(product => {
                rows += `
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                    </tr>
                `;
            });

            tbody.innerHTML = rows; // 전체 행을 한 번에 추가

            document.getElementById('page-info').textContent = `Page: ${page + 1} of ${totalPages}`;
            document.getElementById('prev').disabled = page === 0;
            document.getElementById('next').disabled = page === totalPages - 1;
        },
        // ajax 요청 실패시 실행, jqXHR: jQuery의 XMLHttpRequest 객체, strStatus: 실패 원인 문자열, strError: 실패 응답 상세정보
        error: function(jqXHR, strStatus, strError) {
            // 404 Not Found
            if (jqXHR.status === 404) {
                alert('Products not found (404)');
            // 500 Internal Server Error
            } else if (jqXHR.status === 500) {
                alert('Server error (500). Please try again later.');
            } else {
                alert('Unexpected error: ' + strStatus);
            }
            console.error('Error:', strError);
        }
    });
}

document.getElementById('prev').addEventListener('click', function() {
    if (currentPage > 0) {
        currentPage--;
        loadProducts(currentPage);
    }
});

document.getElementById('next').addEventListener('click', function() {
    currentPage++;
    loadProducts(currentPage);
});

// Initial load