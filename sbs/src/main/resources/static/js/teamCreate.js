/**
 * teamCreate.js || teamCreate.html
 */




window.addEventListener('DOMContentLoaded', function() {

    // 최대 멤버 수 표시 관련 js
    const mom = document.querySelector('#rangeMaxMember');
    mom.addEventListener('mousemove', function() {

        let momCount = mom.value;        
        const m = document.querySelector('#maxMember');        
        m.innerText = `최대 인원 수 : ${momCount} `;
        
    });
    
    // 아이디 사용 가능 여부 확인하는 js 만들기 (TODO)
    const teamNameInput = document.querySelector('#teamName');
    const okDiv = document.querySelector('#availableId');
    const nokDiv = document.querySelector('#unavailableId');
    const btnCreateTeam = document.querySelector('#btnSubmit');
    const createTeamForm = document.querySelector('#createTeamForm');
    
    teamName.addEventListener('change', function() {
       const teamName = teamNameInput.value;
       
       axios
       .get('/team/checkTeamId?teamName=' + teamName)
       .then(response => { teamCheckResult(response.data) })
       .catch(err => { console.log(err); });        
    });
    
    function teamCheckResult(result) {
        if (result == 'ok') {
            okDiv.className = 'my-2';
            nokDiv.className = 'my-2 d-none';
            btnCreateTeam.classList.remove('disabled');
            
        } else {
            okDiv.className = 'my-2 d-none';
            nokDiv.className = 'my-2';
            btnCreateTeam.classList.add('disabled');

        }
    }
    
        btnCreateTeam.addEventListener('click', function() {
        const result = confirm('팀을 정말 생성할까요?');
        if (result) {
            createTeamForm.action = '/team/teamCreate';
            createTeamForm.method = 'post';
            createTeamForm.submit();
            
            alert("생성완료! 활동하러 갈까요?");
        }
    })

});