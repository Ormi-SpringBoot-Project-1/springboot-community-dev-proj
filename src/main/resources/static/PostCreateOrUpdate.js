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
      201: (response) => {
        alert(response);
        history.back();
      },
      404: (response) => { alert(response);}
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
        alert(response);
        history.back();
      },
      404: (response) => { alert(response);}
    }
  });
}