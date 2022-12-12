/**
 * sbs의 cash.js
 */
 
 window.addEventListener('DOMContentLoaded', function() {

    // 패스워드 체크
    const userId = document.querySelector('#userId');
    const password = document.querySelector('#passwordn');
    const pcheck = document.querySelector('#pcheck');
    const btnCash = document.querySelector('#btnCash');
    const userIdValue = userId.value;

    password.addEventListener('change', function() {

        const passwordValue = password.value;

        axios.get('/user/checkpw?password=' + passwordValue + '&userId=' + userIdValue)
            .then(response => { checkPwResult(response.data) })
            .catch(err => { console.log(err) });
    });

    function checkPwResult(data) {
        if (data == 'ok') {
            pcheck.className = 'my-2 d-none';
            btnCash.classList.remove('disabled');
        } else {
            pcheck.className = 'my-2';
            btnCash.classList.add('disabled');
        }

    }

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
    //const formPasswordChange = document.querySelector('#formPasswordChange');
    btnCancel.addEventListener('click', function() {
        const result = confirm('뒤로 가시겠습니까?');
        if (result) {
            formCash.action = '/user/myPage?userId=' + userId.value;
            formCash.method = 'POST';
            formCash.submit();
        }

    });


});