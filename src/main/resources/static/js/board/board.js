console.log("board.js")



raadAll()
function raadAll(){
    console.log("raadAll()")
    $.ajax({
       async : false, method : "get" , url : "/board/readAll" ,
       success : r => {console.log(r);
           let tbody = document.querySelector('tbody')
           let html = '';
           r.forEach( 게시판 => {
               html += `<tr>
                       <th>${게시판.postid}</th>
                       <th>${게시판.title}</th>
                       <th>${게시판.content}</th>
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