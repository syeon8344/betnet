console.log("marketadd.js");

// Summernote 초기화
$('#summernote').summernote({
    height: 300, // 에디터 높이 설정
    placeholder: '여기에 내용을 입력하세요...', // 플레이스홀더 텍스트
    toolbar: [ // 툴바 설정
        ['style', ['bold', 'italic', 'underline', 'clear']],
        ['font', ['strikethrough', 'superscript', 'subscript']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['view', ['fullscreen', 'codeview', 'help']]
    ],
});

function mkWrite(){
    // Summernote 내용
    const content = document.querySelector('#summernote').value;
    // 첨부된 이미지 파일 가져오기
    const files = document.querySelector('#imageFiles').files;

    if (files.length > 3) {
        alert("이미지는 최대 3장까지 첨부할 수 있습니다.");
        return;
    }

    // FormData를 사용하여 폼 데이터 준비
    let formData = new FormData();
    formData.append("content", content);

    // 이미지 파일 추가
    for (let i = 0; i < files.length; i++) {
        formData.append("images", files[i]); // images로 DTO에 전달
    }

    // AJAX 요청으로 데이터 전송
    $.ajax({
        url: '/market/write', // 서버의 게시글 제출 엔드포인트
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(response) {
            alert("게시글 작성 완료. 게시판으로 돌아갑니다.");
            location.href="/market"
        },
        error: function() {
            alert("게시글 제출에 실패했습니다.");
        }
    });
};

$('#imageFiles').change(function() {
    const files = this.files;
    const previewContainer = $('#imagePreview'); // 미리보기 영역

    previewContainer.empty(); // 기존 미리보기 제거

    for (let i = 0; i < files.length && i < 3; i++) {
        const reader = new FileReader();
        reader.onload = function(e) {
            previewContainer.append(`<img src="${e.target.result}" width="100" style="margin: 5px;">`);
        };
        reader.readAsDataURL(files[i]);
    }
});