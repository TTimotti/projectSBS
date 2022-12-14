/**
 * teamCreate.js || teamCreate.html
 */




window.addEventListener('DOMContentLoaded', function() {

    // 최대 멤버 수 표시 관련 js
    const mom = document.querySelector('#rangeMaxMember');
    mom.addEventListener('mousemove', function() {

        let momCount = mom.value;        
        const m = document.querySelector('#maxMember');        
        m.innerText = `Maximum Of Members : ${momCount} `;
        
    });
    
    // 아이디 사용 가능 여부 확인하는 js 만들기 (TODO)
    const ok = document.querySelector('#availableId');
    const nok = document.querySelector('#unavailableId');


});