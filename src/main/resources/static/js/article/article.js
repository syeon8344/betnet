console.log("article.js");

// getKboArticle();
function getKboArticle(){
    console.log("getKboArticle()");
    $.ajax({
        async : false , 
        method : "get" , 
        url : "http://127.0.0.1:5000/article/kbo" , 
        success : (r) =>{
            console.log(r);
        }
    })  // ajax end

}