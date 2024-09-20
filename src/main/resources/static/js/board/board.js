console.log("board.js")


// 전체 조회 함수
raadAll(0)
function raadAll(value){

    let teamcode = document.querySelector(".teamcode").value;

    console.log( 'teamcode' )
    console.log( teamcode )

    console.log("raadAll()")
    $.ajax({
       async : false, method : "get" , url : "/board/readAll" ,
       data : { teamcode : value } ,
       success : r => {console.log(r);
           let tbody = document.querySelector('tbody')
           let html = '';
           r.forEach( 게시판 => {
               html += `
                   <tr>
                       <td>${게시판.postid}</td>
                       <td>${게시판.teamname}</td>
                       <td><a href="/board/view?bno=${게시판.postid}" class="text-decoration-none">${게시판.title}</a></td>
                       <td>${게시판.content}</td>
                       <td>${게시판.memberid}</td>
                       <td>${게시판.createdat}</td>
                       <td>${게시판.views}</td>
                   </tr>
               `;
               })
           tbody.innerHTML = html
           }
    })
}

