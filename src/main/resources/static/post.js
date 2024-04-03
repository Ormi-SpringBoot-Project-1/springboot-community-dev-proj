function deletePost(postId) {
  if(confirm("게시글을 삭제하시겠습니까?")) {
    $.ajax({
      type: "delete",
      url: "http://localhost:8080/api/post/" + postId,
      statusCode: {
        200 : () => {
          alert("게시글이 성공적으로 삭제되었습니다.");
          history.back();
        },
        404: () => {
          alert("게시글 삭제 도중 문제가 발생했습니다.")
        }
      }
    })
  }
}