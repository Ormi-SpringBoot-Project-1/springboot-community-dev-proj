document.addEventListener("DOMContentLoaded", function() {
    const findPasswordBtn = document.getElementById("findPasswordBtn");
    const newPasswordModal = document.getElementById("newPasswordModal");
    const saveNewPasswordBtn = document.getElementById("saveNewPasswordBtn");

    findPasswordBtn.addEventListener("click", function() {
        const username = document.getElementById("username").value;
        if (username.trim() === "") {
            alert("아이디를 입력하세요.");
            return;
        }
        // 여기서 서버에 아이디(이메일)를 전송하여 비밀번호가 있는지 확인하는 요청을 보냅니다.
        // 성공 시 모달을 띄우고, 실패 시 알림을 표시합니다.
        newPasswordModal.modal("show");
    });

    saveNewPasswordBtn.addEventListener("click", function() {
        const newPassword = document.getElementById("newPassword").value;
        const confirmNewPassword = document.getElementById("confirmNewPassword").value;

        if (newPassword.trim() === "" || confirmNewPassword.trim() === "") {
            alert("새로운 비밀번호를 입력하세요.");
            return;
        }

        if (newPassword !== confirmNewPassword) {
            alert("새로운 비밀번호가 일치하지 않습니다.");
            return;
        }

        // 여기서 서버에 새로운 비밀번호를 전송하는 요청을 보냅니다.
        // 성공 시 모달을 닫고, 실패 시 알림을 표시합니다.
        newPasswordModal.modal("hide");
    });
});
