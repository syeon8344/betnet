/*
요약
HTML에서 테이블을 만들고 버튼을 추가합니다.
CSS로 테이블의 너비를 설정하고 고정 레이아웃을 사용합니다.
JavaScript로 페이지네이션 기능을 구현하여 테이블을 원하는 대로 나눕니다.
이렇게 하면 사용자가 "이전"과 "다음" 버튼을 클릭할 때마다 테이블의 내용이 변경되어 상위 <div>의 크기를 넘지 않도록 할 수 있습니다. 필요에 따라 디자인이나 기능을 더 커스터마이즈할 수 있습니다.
*/

let sel_year = 0
getYearTable(1)

function getYearTable(num){
    console.log("getYearTable");
    
    let year = document.querySelector(".year").value
    if (year == sel_year){
        return
    } else {
        sel_year = year
    }
    $.ajax({
        async: false,
        method: "GET",
        data: {year: year},
        url: "http://127.0.0.1:5000/gethittertable",
        success: (resp) => {
            response = JSON.parse(resp)
            console.log(response);
            let html1 = ""
            let html2 = ""
            for (let i = 0; i < response.length; i++){
                console.log(i);
                let keyCount = 0
                html1 += `<tr>`
                html2 += `<tr>`
                for (const key in response[i]){
                    console.log(key);
                    if (keyCount < 13){
                        html1 += `<td>${response[i][key]}</td>`
                    } else {
                        html2 += `<td>${response[i][key]}</td>`
                    }
                    keyCount++
                }
                html1 += `</tr>`
                html2 += `</tr>`
            }
            document.querySelector(".historyTbody1").innerHTML = html1
            document.querySelector(".historyTbody2").innerHTML = html2
        }
    })
}

// === 반응형 테이블 GPT ===
let currentPage = 0;
const rowsPerPage = 5; // 페이지당 행 수
const table = document.getElementById("myTable");
const tbody = table.getElementsByTagName("tbody")[0];
const rows = tbody.getElementsByTagName("tr");
const totalRows = rows.length;
const totalPages = Math.ceil(totalRows / rowsPerPage);

function updateTable() {
    for (let i = 0; i < rows.length; i++) {
        rows[i].style.display = "none"; // 모든 행 숨기기
    }

    for (let i = currentPage * rowsPerPage; i < (currentPage + 1) * rowsPerPage && i < totalRows; i++) {
        rows[i].style.display = ""; // 현재 페이지의 행만 표시
    }
}

function nextPage() {
    if (currentPage < totalPages - 1) {
        currentPage++;
        updateTable();
    }
}

function prevPage() {
    if (currentPage > 0) {
        currentPage--;
        updateTable();
    }
}

// 초기 테이블 업데이트
updateTable();