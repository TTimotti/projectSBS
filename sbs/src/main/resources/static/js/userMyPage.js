/**
 * sbs의 myPage.js
 * /membet/signup.html
 */


window.addEventListener('DOMContentLoaded', function() {

    const btnCash = document.querySelector('#btnCash')
    const form = document.querySelector('#form')
    btnCash.addEventListener('click', function() {
        const result = confirm('캐쉬 충전?');
        if (result) {
            form.action = '/user/cash';
            form.method = 'get';
            form.submit();

        }
    })



});