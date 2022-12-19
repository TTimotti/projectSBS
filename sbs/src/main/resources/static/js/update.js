/**
 * update.js || update.html
 */


window.addEventListener('DOMContentLoaded', function() {
    const btnPasswordChange = document.querySelector('#btnPasswordChange');
    const btnDelete = document.querySelector('#btnDeleteUser');
    const userId = document.querySelector('#userId');
    const password = document.querySelector('#password');
    const pcheck = document.querySelector('#pcheck');
    const pinput = document.querySelector('#pinput');
    const btnUpdate = document.querySelector('#btnUpdate');
    const userIdValue = userId.value;
    const pinfo = document.querySelector('#pinfo');

    // 패스워드 체크
    password.addEventListener('change', function() {

        const passwordValue = password.value;

        if (passwordValue != '') {
            pinput.className = 'my-2 d-none';
            btnUpdate.classList.remove('disabled');
            btnPasswordChange.classList.remove('disabled');
            btnDelete.classList.remove('disabled');
            pcheck.className = 'my-2 d-none';
            if (password.value != passwordcheckInput.value) {
                btnPasswordChange.classList.add('disabled');
                btnDelete.classList.add('disabled');
                btnUpdate.classList.add('disabled');

            }
        } else {
            pcheck.className = 'my-2 d-none';
            pinput.className = 'my-2';
            btnPasswordChange.classList.add('disabled');
            btnDelete.classList.add('disabled');
            btnUpdate.classList.add('disabled');
        }

        axios.get('/user/checkpw?password=' + passwordValue + '&userId=' + userIdValue)
            .then(response => { checkPwResult(response.data) })
            .catch(err => { console.log(err) });
    });

    function checkPwResult(data) {
        if (data == 'ok') {
            pcheck.className = 'my-2 d-none';
            btnUpdate.classList.remove('disabled');
            btnPasswordChange.classList.remove('disabled');
            btnDelete.classList.remove('disabled');
            if (password.value != passwordcheckInput.value) {
                btnPasswordChange.classList.add('disabled');
                btnDelete.classList.add('disabled');
                btnUpdate.classList.add('disabled');
            }
        } else {
            pcheck.className = 'my-2';
            btnPasswordChange.classList.add('disabled');
            btnDelete.classList.add('disabled');
            btnUpdate.classList.add('disabled');
            if (password.value == '') {
                pcheck.className = 'my-2 d-none';
            }
        }

    }


    // 패스워드 확인
    const pokDiv = document.querySelector('#pok');
    const pnokDiv = document.querySelector('#pnok');
    const passwordcheckInput = document.querySelector('#passwordcheck')

    passwordcheckInput.addEventListener('change', function() {
        if (password.value == passwordcheckInput.value) {
            pokDiv.className = 'my-2 ';
            pinfo.className = 'my-2 d-none';
            pnokDiv.className = 'my-2 d-none';
            btnUpdate.classList.remove('disabled');
            btnPasswordChange.classList.remove('disabled');
            btnDelete.classList.remove('disabled');
            pinfo.className = 'my-2 d-none';
            if (pcheck.className == 'my-2') {
                pokDiv.className = 'my-2 d-none';
                pnokDiv.className = 'my-2';
                pinfo.className = 'my-2';
                btnPasswordChange.classList.add('disabled');
                btnDelete.classList.add('disabled');
                btnUpdate.classList.add('disabled');
            }
        } else {
            pokDiv.className = 'my-2 d-none';
            pnokDiv.className = 'my-2';
            pinfo.className = 'my-2';
            btnPasswordChange.classList.add('disabled');
            btnDelete.classList.add('disabled');
            btnUpdate.classList.add('disabled');
        }



    });

    password.addEventListener('change', function() {
        if (password.value == passwordcheckInput.value) {
            pokDiv.className = 'my-2 ';
            pnokDiv.className = 'my-2 d-none';
            pinfo.className = 'my-2 d-none';
            btnUpdate.classList.remove('disabled');
            btnPasswordChange.classList.remove('disabled');
            btnDelete.classList.remove('disabled');
            if (pcheck.className == 'my-2') {
                pokDiv.className = 'my-2 d-none';
                pnokDiv.className = 'my-2';
                pinfo.className = 'my-2';
                btnPasswordChange.classList.add('disabled');
                btnDelete.classList.add('disabled');
                btnUpdate.classList.add('disabled');
            }
        } else {
            pokDiv.className = 'my-2 d-none';
            pnokDiv.className = 'my-2';
            pinfo.className = 'my-2';
            btnPasswordChange.classList.add('disabled');
            btnDelete.classList.add('disabled');
            btnUpdate.classList.add('disabled');
        }



    });

    // 뒤로가기 버튼
    const btnCancel = document.querySelector('#btnCancel');
    const formUpdate = document.querySelector('#formUpdate');
    btnCancel.addEventListener('click', function() {
        const result = confirm('뒤로 가시겠습니까?');
        if (result) {
            formUpdate.action = '/user/myPage?userId=' + userIdValue;
            formUpdate.method = 'get';
            formUpdate.submit();
        }

    });

    // 수정 완료 버튼 찾아서 이벤트 리스너 등록 TODO
    btnUpdate.addEventListener('click', function() {
        const result = confirm('정말 수정?');
        if (result) {
            formUpdate.action = '/user/update';
            formUpdate.method = 'post';
            formUpdate.submit();

        }
    })

    // 회원 탈퇴 버튼
    btnDelete.addEventListener('click', function() {
        const result = confirm('정말 삭제?');
        if (result) {
            formUpdate.action = '/user/delete';
            formUpdate.method = 'post';
            formUpdate.submit();
        }
    });



    // 비밀번호 변경 버튼
    btnPasswordChange.addEventListener('click', function() {
        const result = confirm('비밀번호 변경?');
        if (result) {
            formUpdate.action = '/user/passwordChange';
            formUpdate.method = 'get';
            formUpdate.submit();
        }
    })

});