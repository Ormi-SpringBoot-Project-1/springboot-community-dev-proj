// 정렬 기준, 정렬 방식에 따른 회원 목록 업데이트에 사용되는 파라메터 변수들
const parseUrl = new URL(window.location.href);
const page = parseUrl.searchParams.get("page") === null ? "1" : parseUrl.searchParams.get("page");
const orderBy = parseUrl.searchParams.get("orderby") === null ? "userId" : parseUrl.searchParams.get("orderby");
const sort = parseUrl.searchParams.get("sort") === null ? "asc" : parseUrl.searchParams.get("sort");

function setUserBlock(userId) {
  let request = {
    userId: userId
  };

  $.ajax({
    type: "post",
    url: "http://localhost:8080/api/admin/user/blocked",
    headers: {
      "Content-Type": "application/json; charset=utf-8"
    },
    data: JSON.stringify(request),
    dataType: "json"
  }).done((response) => {
    alert(response.result);

    if (response.result === "가입 제한으로 설정되었습니다.") {
      window.location.replace(window.location.href);
    }
  }).fail((err) => {
    alert(err);
  })
}

function setUserUnblock(userId) {
  let request = {
    userId: userId
  };

  $.ajax({
    type: "delete",
    url: "http://localhost:8080/api/admin/user/unblocked",
    headers: {
      "Content-Type": "application/json; charset=utf-8"
    },
    data: JSON.stringify(request),
    dataType: "json"
  }).done((response) => {
    alert(response.result);

    if (response.result === "가입 제한을 해제했습니다.") {
      window.location.replace(window.location.href);
    }
  }).fail((err) => {
    alert(err);
  })
}

function changeUserLevel(userId, select) {
  let request = {
    userId: userId,
    level: select
  };

  $.ajax({
    type: "put",
    url: "http://localhost:8080/api/admin/user/level",
    headers: {
      "Content-Type": "application/json; charset=utf-8"
    },
    data: JSON.stringify(request),
    dataType: "json"
  }).done((response) => {
    alert(response.result);

    if (response.result === "등급이 정상적으로 변경되었습니다.") {
      window.location.replace(window.location.href);
    }
  }).fail((err) => {
    alert(err);
  })
}

function searchUser() {
  let nickname = document.getElementsByClassName("search")[0].value;

  if (nickname === "") {
    alert("검색을 위해 회원 닉네임을 입력해주세요!");
    return;
  }

  window.location = ("http://localhost:8080/admin/user/management/search?nickname=" + nickname);
}

function changeOrder(order) {
  window.location = ("http://localhost:8080/admin/user/management?page" + page + "&orderby=" + order + "&sort=" + sort);
}

function changeSort(sort) {
  window.location = ("http://localhost:8080/admin/user/management?page" + page + "&orderby=" + orderBy + "&sort=" + sort);
}