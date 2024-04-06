// document.addEventListener("DOMContentLoaded", function() {
//     const loginForm = document.getElementById("loginForm");
//
//     loginForm.addEventListener("submit", function(event) {
//         event.preventDefault(); // 폼의 기본 동작 방지
//
//         // 폼 데이터 가져오기
//         const formData = new FormData(loginForm);
//         const username = formData.get("username");
//         const password = formData.get("password");
//
//         // 서버로 로그인 요청 보내기
//         fetch("/login", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/x-www-form-urlencoded"
//             },
//             body: formData
//         })
//             .then(response => {
//                 if (response.ok) {
//                     // 로그인 성공 시 처리할 내용 작성
//                     console.log("로그인 성공");
//                     // 예시: 로그인 후 페이지 이동
//                     window.location.href = "/dashboard";
//                 } else {
//                     // 로그인 실패 시 처리할 내용 작성
//                     console.error("로그인 실패");
//                     alert("아이디 또는 비밀번호가 올바르지 않습니다.");
//                 }
//             })
//             .catch(error => {
//                 console.error("로그인 요청 실패:", error);
//                 alert("로그인 요청 중 오류가 발생했습니다.");
//             });
//     });
// });

function goToFindId() {
    window.location = window.location.origin + "/findId";
}

function goToSignup() {
    window.location = window.location.origin + "/signup";
}

function goToFindPassword() {
    window.location = window.location.origin + "/findPassword";
}
