console.log("marketview.js");

// AJAX 요청으로 게시글 데이터 불러오기
$.ajax({
    url: 'getPost.php', // 서버 측 스크립트 URL
    type: 'GET',
    data: { id: postId }, // 게시글 ID 전송
    dataType: 'json',
    success: function(data) {
        // 데이터가 성공적으로 불러와지면 HTML에 추가
        $('#postTitle').text(data.title);
        $('#postAuthor').text(data.author);
        $('#postDate').text(data.date);
        $('#postContent').html(data.content);
    },
    error: function(xhr, status, error) {
        console.error('게시글을 불러오는 중 오류 발생:', error);
        $('#postContent').html('<p>게시글을 불러오는 데 실패했습니다.</p>');
    }
});

// 돌아가기 버튼 클릭 시 이전 페이지로 이동
window.goBack = function() {
    window.history.back();
};

// 게시글 삭제 함수
window.deletePost = function() {
    if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
        // 삭제 로직 (서버에 삭제 요청 등을 추가)
        alert("게시글이 삭제되었습니다.");
        goBack(); // 삭제 후 목록으로 돌아가기
    }
};

// 수정 모달에서 수정 버튼을 눌렀을 때 실행되는 이벤트 리스너
document.querySelector('#submitEdit').addEventListener('click', function() {
    // 제목과 내용 가져오기
    const newTitle = document.querySelector('#newTitle').value;
    const newContent = document.querySelector('#newContent').value;

    // 수정 로직 (여기서는 콘솔에 출력, 실제로는 서버에 AJAX 요청 등을 통해 수정)
    console.log('수정된 제목:', title);
    console.log('수정된 내용:', content);

    // 모달 닫기
    $('#editModal').modal('hide');

    // 이전 페이지로 이동 (일반적으로 서버에서 데이터 수정 후 리다이렉트하는 경우가 많습니다)
    // 예시로는 간단히 history.back() 사용
    // history.back();
});
console.log("marketview.js");

// AJAX 요청으로 게시글 데이터 불러오기
$.ajax({
    url: 'getPost.php', // 서버 측 스크립트 URL
    type: 'GET',
    data: { id: postId }, // 게시글 ID 전송
    dataType: 'json',
    success: function(data) {
        // 데이터가 성공적으로 불러와지면 HTML에 추가
        $('#postTitle').text(data.title);
        $('#postAuthor').text(data.author);
        $('#postDate').text(data.date);
        $('#postContent').html(data.content);
    },
    error: function(xhr, status, error) {
        console.error('게시글을 불러오는 중 오류 발생:', error);
        $('#postContent').html('<p>게시글을 불러오는 데 실패했습니다.</p>');
    }
});

// 돌아가기 버튼 클릭 시 이전 페이지로 이동
window.goBack = function() {
    window.history.back();
};

// 게시글 삭제 함수
window.deletePost = function() {
    if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
        // 삭제 로직 (서버에 삭제 요청 등을 추가)
        alert("게시글이 삭제되었습니다.");
        goBack(); // 삭제 후 목록으로 돌아가기
    }
};

// 수정 모달에서 수정 버튼을 눌렀을 때 실행되는 이벤트 리스너
document.querySelector('#submitEdit').addEventListener('click', function() {
    // 제목과 내용 가져오기
    const newTitle = document.querySelector('#newTitle').value;
    const newContent = document.querySelector('#newContent').value;

    // 수정 로직 (여기서는 콘솔에 출력, 실제로는 서버에 AJAX 요청 등을 통해 수정)
    console.log('수정된 제목:', title);
    console.log('수정된 내용:', content);

    // 모달 닫기
    $('#editModal').modal('hide');

    // 이전 페이지로 이동 (일반적으로 서버에서 데이터 수정 후 리다이렉트하는 경우가 많습니다)
    // 예시로는 간단히 history.back() 사용
    // history.back();
});