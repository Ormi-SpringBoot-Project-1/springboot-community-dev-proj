function searchPost() {
  const searchKeyword = document.getElementsByClassName("search-post")[0].value;

  window.location = (window.location.origin + window.location.pathname
      + "?search=" + searchKeyword);
}

function moveBoard(boardName) {
  $.ajax({
    type: "get",
    url: window.location.origin + "/posts/" + boardName,
    statusCode: {
      200: (html) => {
        window.location = window.location.origin + "/posts/" + boardName;
      },
      403: () => {
        let accessLevel;

        if (boardName === "그룹 모집 게시판") {
          accessLevel = "Tier 4";
        } else {
          accessLevel = "Tier 3";
        }

        alert("해당 게시판에 대한 접근 권한이 없습니다.(" + accessLevel + " 이상 접근 가능)");
      }
    }
  });
}

function createPost(boardName) {
  window.location = (window.location.origin + "/posts/" + boardName + "/newPost");
}

function viewMyPost(boardName) {
  window.location = (window.location.origin + "/posts/" + boardName + "/myPost");
}

function viewPost(postId, boardName) {
  $.ajax({
        type: "get",
        url: window.location.origin + "/posts/" + boardName + "/" + postId + "?message=true"
      }
  ).done(function (data, textStatus, xhr) {
    window.location = (window.location.origin + "/posts/" + boardName + "/" + postId + "?duplicate=true");
  })
  .fail(function (data, textStatus, xhr) {
    alert(data.responseJSON.message);
  })
}