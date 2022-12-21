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

const err = document.querySelector('#signInErr');
    
    let url = window.location.search;
    console.log(url);
    if (url=='?error') {
        console.log('Sign In Error');
    err.className = 'd-on'
    }
