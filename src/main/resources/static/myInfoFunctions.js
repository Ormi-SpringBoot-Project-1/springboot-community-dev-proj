function logout() {
  if (confirm("로그 아웃 하시겠습니까?")) {
    window.location = (window.location.origin + "/logout");
  }
}

function accountDelete() {
  if (confirm("회원 탈퇴 하시겠습니까?")) {
    window.location = (window.location.origin + "/delete-account");
  }
}

function goToUserManagement() {
  window.location = (window.location.origin + "/admin/user/management");
}

function goToFreeBoard() {
  window.location = (window.location.origin + "/posts/자유 게시판");
}