/**
 * member.js
 * /membet/signup.html
 */
 
 /*!
* Start Bootstrap - Freelancer v7.0.6 (https://startbootstrap.com/theme/freelancer)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-freelancer/blob/master/LICENSE)
*/
//
// Scripts
// 

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
window.addEventListener('DOMContentLoaded', function() {


    // 로그인 유저 체크
    const myPageDiv = document.querySelector('#myPageDiv');
    const errorDiv = document.querySelector('#errorDiv');
    console.log(loginUser);
    console.log(userName);
        if(userName == loginUser) {
            myPageDiv.className = '';
            
        } else{
            errorDiv.className = 'my-2 p-4 text-danger text-center rounded';
        }

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