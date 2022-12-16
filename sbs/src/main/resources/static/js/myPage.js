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
    
    const img = document.querySelector('#userImage');
            func();
            
            function func() {
                axios
                .get('/image/display?fid=' + usersFid)
                .then(response => {  
                    console.log('axios 실행');
                    console.log(response.data);
                    img.src = '/image/display?fid=' + usersFid;
                    console.log(img.src);
                    })
                .catch(err => { console.log(err); }); 

            }


});