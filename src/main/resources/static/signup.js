const signupForm = document.getElementsByClassName("form-data")[0];

signupForm.addEventListener("submit", function (event) {
  event.preventDefault(); // 폼의 기본 동작 방지

  // 폼 데이터 가져오기
  const formData = new FormData(signupForm);
  const email = formData.get("email");
  const nickname = formData.get("nickname");
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirm-password").value;
  const tel = formData.get("phoneNum");
  const description = formData.get("description");

  // 비밀번호 대소문자 영문 + 숫자 8자리 이상 확인
  const pattern = new RegExp("[a-zA-Z0-9]+{8, }")
  if (!pattern.test(password)) {
    alert("비밀번호는 대소문자 영문 + 숫자 8자리 이상이어야 합니다.")
    return;
  }

  alert(password);
  alert(confirmPassword);

  // 비밀번호 확인
  if (password !== confirmPassword) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  // 서버로 회원가입 요청 보내기
  fetch("/user", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: formData
  })
  .then(response => {
    console.log(response);
    if (response.ok) {
      // 회원가입 성공 시 처리할 내용 작성
      console.log("회원가입 성공");
      // 예시: 회원가입 후 페이지 이동
      window.location.href = "/login";
    } else {
      // 회원가입 실패 시 처리할 내용 작성
      console.error("회원가입 실패");
      alert("회원가입에 실패했습니다. 다시 시도해주세요.");
    }
  })
  .catch(error => {
    console.error("회원가입 요청 실패:", error);
    alert("회원가입 요청 중 오류가 발생했습니다.");
  });
});
