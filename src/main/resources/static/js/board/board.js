console.log("board.js")


// 전체 조회 함수
raadAll()
function raadAll(){


    let teamcode = document.querySelector("#teamcode").value;

    console.log( 'teamcode' )
    console.log( teamcode )

    console.log("raadAll()")
    $.ajax({
       async : false, method : "get" , url : "/board/readAll" ,
       data : { teamcode : teamcode } ,
       success : r => {console.log(r);
           let tbody = document.querySelector('tbody')
           let html = '';
           r.forEach( 게시판 => {
               html += `<tr>
                       <th>${게시판.postid}</th>
                       <th>${게시판.teamname}</th>
                       <th><a href="/board/view?bno=${게시판.postid}">${게시판.title}</th>
                       <th>${게시판.memberid}</th>
                       <th>${게시판.createdat}</th>
                       <th>${게시판.views}</th>
                       <th>${게시판.likes}</th>
                       </tr>`
               })
           tbody.innerHTML = html
           }
    })
}

