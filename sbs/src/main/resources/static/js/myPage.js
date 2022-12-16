/**
 * member.js
 * /membet/signup.html
 */
window.addEventListener('DOMContentLoaded', function() {

    // 캐쉬충전 홈페이지로 이동
    const btnCash = document.querySelector('#btnCash');
    const form = document.querySelector('#form');
    
    btnCash.addEventListener('click', function() {
        const result = confirm('캐쉬 충전하시겠습니까?');
        if (result) {
            form.action = '/user/cash';
            form.method = 'get';
            form.submit();

        }
    })



});