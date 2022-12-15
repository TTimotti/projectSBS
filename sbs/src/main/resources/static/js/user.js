/**
 * member.js
 * /membet/signup.html
 */


window.addEventListener('DOMContentLoaded', function() {

    const pokDiv = document.querySelector('#pok');
    const pnokDiv = document.querySelector('#pnok');
    const btnSubmit = document.querySelector('#btnSubmit');
    const password = document.querySelector('#password');
    const passwordcheckInput = document.querySelector('#passwordcheck')

    // 패스워드 확인
    passwordcheckInput.addEventListener('change', function() {
        if (password.value == passwordcheckInput.value) {
            pokDiv.className = 'my-2 ';
            pnokDiv.className = 'my-2 d-none';
            btnSubmit.classList.remove('disabled'); // 버튼 활성화

        } else {
            pokDiv.className = 'my-2 d-none';
            pnokDiv.className = 'my-2';
            btnSubmit.classList.add('disabled');

        }
    });
    
    // 패스워드확인 확인
    password.addEventListener('change', function() {
        if (password.value == passwordcheckInput.value) {
            pokDiv.className = 'my-2 ';
            pnokDiv.className = 'my-2 d-none';
            btnSubmit.classList.remove('disabled'); // 버튼 활성화

        } else {
            pokDiv.className = 'my-2 d-none';
            pnokDiv.className = 'my-2';
            btnSubmit.classList.add('disabled');

        }
    });

    // 아이디 중복 체크
    const usernameInput = document.querySelector('#userName');
    const okDiv = document.querySelector('#ok');
    const nokDiv = document.querySelector('#nok');

    usernameInput.addEventListener('change', function() {
        const userName = usernameInput.value;

        axios.get('/user/checkid?userName=' + userName)
            .then(response => { displayCheckResult(response.data) })
            .catch(err => { console.log(err); });
    });

    // 가입 확인 버튼 활성화
    function displayCheckResult(data) {

        if (data == 'ok') {
            okDiv.className = 'my-2 ';
            nokDiv.className = 'my-2 d-none';
            btnSubmit.classList.remove('disabled'); // 버튼 활성화
        } else {
            okDiv.className = 'my-2 d-none';
            nokDiv.className = 'my-2';
            btnSubmit.classList.add('disabled');
        }

    }




});