function searchPost() {
  const searchKeyword = document.getElementsByClassName("search-post")[0].value;

  window.location.replace(window.location.origin + window.location.pathname + "?search=" + searchKeyword);
}

function moveBoard(boardName) {
  $.ajax({
    type: "get",
    url: window.location.origin + "/posts/" + boardName,
    statusCode: {
      200: (html) => {
        window.location = window.location.origin + "/posts/" + boardName;
      },
      403: () => { alert("해당 게시판에 대한 접근 권한이 없습니다.");}
    }
  });
}

function createPost(boardName) {

}

function viewMyPost(boardName) {
  window.location.replace("http://localhost:8080/posts/" + boardName + "/myPost");
}

function viewPost(postId, updatedAt) {

}