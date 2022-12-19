/**
 * member.js
 * /membet/signup.html
 */
 
 /*!
* Start Bootstrap - Simple Sidebar v6.0.5 (https://startbootstrap.com/template/simple-sidebar)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-simple-sidebar/blob/master/LICENSE)
*/
// 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});


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
    
     function displayCheckResult(data) {
        console.log(data);
        if (data == 'ok') {
            okDiv.className = 'my-2';
            nokDiv.className = 'my-2 d-none';
            btnSubmit.classList.remove('disabled'); // 버튼 활성화
        } else {
            okDiv.className = 'my-2 d-none';
            nokDiv.className = 'my-2';
            btnSubmit.classList.add('disabled');
        }

    }





});