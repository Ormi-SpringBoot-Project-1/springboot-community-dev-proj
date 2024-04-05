const form = document.getElementsByClassName("send-post-info")[0];

form.addEventListener("submit", (event) => {
  event.preventDefault();

  if (form.getAttribute("value") === "") {
    createPost();
  }
  else {
    updatePost();
  }
});

function createPost() {
  $.ajax({
    type: "post",
    url: form.getAttribute("action"),
    data: new FormData(form),
    enctype: 'multipart/form-data',
    processData: false,
    contentType: false,
    statusCode: {
      201: () => {
        alert("게시글이 성공적으로 등록되었습니다.")
        history.back();
      },
      404: () => {
        alert("게시글 등록에 실패했습니다.")
      }
    }
  })
}

function updatePost() {
  $.ajax({
    type: "put",
    url: form.getAttribute("action"),
    data: new FormData(form),
    enctype: 'multipart/form-data',
    processData: false,
    contentType: false,
    statusCode: {
      200: (response) => {
        console.log(response);
        alert("게시글이 성공적으로 수정되었습니다.")
        history.back();
      },
      404: () => {
        alert("게시글 수정에 실패했습니다.");
      },
      500: () => {
        alert("게시글 수정에 실패했습니다.");
      }
    }
  })
}