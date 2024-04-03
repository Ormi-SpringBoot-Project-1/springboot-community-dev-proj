function searchPost() {
  const searchKeyword = document.getElementsByClassName("search-post")[0].value;

  window.location = (window.location.origin + window.location.pathname + "?search=" + searchKeyword);
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
  window.location = ("http://localhost:8080/posts/" + boardName + "/newPost");
}

function viewMyPost(boardName) {
  window.location = ("http://localhost:8080/posts/" + boardName + "/myPost");
}

function viewPost(postId, boardName) {
  $.ajax({
    type: "get",
    url: "http://localhost:8080/posts/" + postId,
    statusCode: {
      200: () => {
        window.location = "http://localhost:8080/posts/" + boardName + "/" + postId;
      },
      404: () => {
        alert("해당 게시물을 찾을 수 없습니다.");
        history.back();
      }
    }
  })
}