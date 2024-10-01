
function salary(){ console.log('salary()');
    let name=document.querySelector('.name').value;
    console.log(name);
    
    $.ajax({
        method:"GET",
        data:{"name":name},
        url:"http://127.0.0.1:5000/salary",
        success: (result) => {
            console.log(result);
            let stat=document.querySelector(".player-profile");
            let html=''
            result.forEach(r =>{
                html+=`<div class="flex-container">
                <div class="player-image">
                    <img src="${r.사진}" alt="선수 이미지">
                </div>
                <div class="player-info">
                    <p><strong>선수명:</strong> ${r.선수명}</p>
                    <p><strong>타율:</strong> ${r.타율}</p>
                    <p><strong>타석:</strong> ${pr.타석}</p>
                    <p><strong>안타:</strong> ${r.안타}</p>
                    <p><strong>홈런:</strong> ${r.홈런}</p>
                </div>
                <div class="player-stats">
                    <p><strong>타점:</strong> ${r.타점}</p>
                    <p><strong>도루:</strong> ${r.도루}</p>
                    <p><strong>볼넷:</strong> ${r.볼넷}</p>
                    <p><strong>장타율:</strong> ${r.장타율}</p>
                    <p><strong>출루율:</strong> ${r.출루율}</p>
                </div>
                <div class="player-salary">
                    예상 연봉: ${r.예상연봉}억 원
                </div>
            </div>`
            })
            stat.innerHTML=html;
        }
    })
 }