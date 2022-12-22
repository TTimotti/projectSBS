/**
 * update.js || update.html
 */


window.addEventListener('DOMContentLoaded', function() {
    const btnPasswordChange = document.querySelector('#btnPasswordChange');
    const btnDelete = document.querySelector('#btnDeleteUser');
    const userName = document.querySelector('#userName');
    console.log(userName);
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
        const result = confirm('정보를 수정하시겠습니까?');
        if (result) {
            formUpdate.action = '/user/userUpdate';
            formUpdate.method = 'post';
            formUpdate.submit();

        }
    })

    // 회원 탈퇴 버튼
    btnDelete.addEventListener('click', function() {
        const result = confirm('정말 계정을 삭제하시겠습니까?');
        if (result) {
            formUpdate.action = '/user/delete';
            formUpdate.method = 'post';
            formUpdate.submit();
        }
    });



    // 비밀번호 변경 버튼
    btnPasswordChange.addEventListener('click', function() {
        const result = confirm('비밀번호를 변경하시겠습니까?');
        if (result) {
            formUpdate.action = '/user/passwordChange';
            formUpdate.method = 'get';
            formUpdate.submit();
        }
    })

    // 로그인 유저 체크
    const updateDiv = document.querySelector('#updateDiv');
    const errorDiv = document.querySelector('#errorDiv');
        if(userName.value == loginUser) {
            updateDiv.className = 'my-2';
            
        } else{
            errorDiv.className = 'my-2 p-4 text-danger text-center rounded';
        }

});
window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar 
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 72,
        });
    };

    // Collapse responsive navbar when toggler is visible
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });

});