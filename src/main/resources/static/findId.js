const findIdForm = document.getElementById("findIdForm");
const loadingDiv = document.querySelector("#loading");

findIdForm.addEventListener("submit", (e) => {
    e.preventDefault();
    findId();
})

function findId() {
    var formData = new FormData();
    formData.append("phoneNumber", document.getElementById("phoneNumber").value);

    loadingDiv.style.display = "block";

    fetch('/findId', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('아이디 찾기에 실패했습니다.');
            }
        })
        .then(data => {
            loadingDiv.style.display = "none";
            alert("회원님의 아이디는 " + data + " 입니다.");
        })
        .catch(error => {
            loadingDiv.style.display = "none";
            console.log('Error: ', error);
            alert('전화번호에 해당하는 아이디를 찾을 수 없습니다.');
        });
}
