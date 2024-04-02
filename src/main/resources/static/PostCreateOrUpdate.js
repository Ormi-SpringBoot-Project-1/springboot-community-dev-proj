const form = document.getElementsByClassName("send-post-info")[0];

form.addEventListener("submit", event => {
  event.preventDefault();

  $.ajax({
    type: "post",
    url: form.getAttribute("action"),
    data: new FormData(form),
    enctype: 'multipart/form-data',
    processData: false,
    contentType: false,
    statusCode: {
      201: (response) => {
        alert(response)
        history.back();
      },
      404: (response) => { alert(response);}
    }
  })
})