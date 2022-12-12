/**
 * sbs의 passwordChage
 */
 
 window.addEventListener('DOMContentLoaded', function() {

    // 패스워드 체크
    const userId = document.querySelector('#userId');
    const password = document.querySelector('#passwordn');
    const pcheck = document.querySelector('#pcheck');
    const btnChange = document.querySelector('#btnChange');
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
            btnChange.classList.remove('disabled');
            if (passwordc.value != passwordcc.value) {
                btnChange.classList.add('disabled');
            }
        } else {
            pcheck.className = 'my-2';
            btnChange.classList.add('disabled');
        }

    }

    // 패스워드 확인
    const pokDiv = document.querySelector('#pok');
    const pnokDiv = document.querySelector('#pnok');
    const passwordc = document.querySelector('#passwordc')
    const passwordcc = document.querySelector('#password')

    passwordcc.addEventListener('change', function() {
        if (password.value == '') {
            btnChange.classList.add('disabled');
        } else if (passwordc.value == passwordcc.value) {
            pokDiv.className = 'my-2 ';
            pnokDiv.className = 'my-2 d-none';
            btnChange.classList.remove('disabled');
            if (pcheck.className == 'my-2') {
                pokDiv.className = 'my-2 d-none';
                pnokDiv.className = 'my-2';
                btnChange.classList.add('disabled');
            }
        } else {
            pokDiv.className = 'my-2 d-none';
            pnokDiv.className = 'my-2';
            btnChange.classList.add('disabled');
        }




    });
    // 뒤로가기 버튼
    const btnCancel = document.querySelector('#btnCancel');
    const formPasswordChange = document.querySelector('#formPasswordChange');
    btnCancel.addEventListener('click', function() {
        const result = confirm('뒤로 가시겠습니까?');
        if (result) {
            formPasswordChange.action = '/user/myPage?userId=' + userIdValue;
            formPasswordChange.method = 'POST';
            formPasswordChange.submit();
        }

    });

    // 수정 완료 버튼 찾아서 이벤트 리스너 등록 TODO
    btnChange.addEventListener('click', function() {
        const result = confirm('비밀번호 변경?');
        if (result) {
            formPasswordChange.action = '/user/passwordChange';
            formPasswordChange.method = 'post';
            formPasswordChange.submit();

        }
    })



});