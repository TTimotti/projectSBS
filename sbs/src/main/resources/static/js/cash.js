/**
 * member.js
 * /membet/signup.html
 */


window.addEventListener('DOMContentLoaded', function() {

    
    const userId = document.querySelector('#userId');
    const password = document.querySelector('#passwordn');
    const pcheck = document.querySelector('#pcheck');
    const pinput = document.querySelector('#pinput');
    const btnCash = document.querySelector('#btnCash');
    const userIdValue = userId.value;

    // 패스워드 체크
    password.addEventListener('change', function() {

        const passwordValue = password.value;

        if (passwordValue != '') {
            pinput.className = 'my-2 d-none';
        } else {
            pcheck.className = 'my-2 d-none';
            pinput.className = 'my-2';
            btnCash.classList.add('disabled');
        }


        axios.get('/user/checkpw?password=' + passwordValue + '&userId=' + userIdValue)
            .then(response => { checkPwResult(response.data) })
            .catch(err => { console.log(err) });
    });

    // 패스워드 체크 결과를 적용
    function checkPwResult(data) {
        if (data == 'ok') {
            pcheck.className = 'my-2 d-none';
            btnCash.classList.remove('disabled');
        } else {
            pcheck.className = 'my-2';
            btnCash.classList.add('disabled');
            if (password.value == '') {
                pcheck.className = 'my-2 d-none';
            }
        }

    }


    // 캐쉬 충전 버튼 활성화
    btnCash.addEventListener('click', function() {
        const cash = document.querySelector('#cash');
        const formCash = document.querySelector('#formCash')
        const result = confirm(cash.value + '캐쉬 충전?');
        if (result) {
            formCash.action = '/user/cash';
            formCash.method = 'post';
            formCash.submit();
        }
    });


    // 뒤로가기 버튼
    const btnCancel = document.querySelector('#btnCancel');
    btnCancel.addEventListener('click', function() {
        const result = confirm('뒤로 가시겠습니까?');
        if (result) {
            formCash.action = '/user/myPage?userId=' + userId.value;
            formCash.method = 'post';
            formCash.submit();
        }

    });
});