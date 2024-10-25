console.log("chatballpt.js")



function messageBox(){


    $.ajax({
    url: 'http://localhost:5000/chat',
    type: 'GET',
    data: {  },
    success: r => {    console.log(result)
    }



    })


}