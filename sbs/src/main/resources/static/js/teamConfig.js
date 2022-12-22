/**
 * teamConfig.js || teamConfig.html
 * @author 서범수
 */

window.addEventListener('DOMContentLoaded', function() {
    
        // 최대 멤버 수 표시 관련 js
    const mom = document.querySelector('#maxMember');
    mom.addEventListener('mousemove', function() {

        let momCount = mom.value;        
        const m = document.querySelector('#mMember');        
        m.innerText = `( ${momCount} 명)`;
        
    });
    
    // 팀 이름 중복 체크(teamCreate.js와 동일한 내용)
    const teamNameInput = document.querySelector('#teamName');
    const purposeInput = document.querySelector('#purpose');
    const okDiv = document.querySelector('#ok');
    const nokDiv = document.querySelector('#nok');
    const btnUpdateTeam = document.querySelector('#btnUpdateTeam');
    const updateTeamForm = document.querySelector('#updateTeamForm');
    
    teamNameInput.addEventListener('change', function() {
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
            btnUpdateTeam.classList.remove('disabled');
            
        } else {
            okDiv.className = 'my-2 d-none';
            nokDiv.className = 'my-2';
            btnUpdateTeam.classList.add('disabled');
            
        }
    }
    
    btnUpdateTeam.addEventListener('click', function() {
        const teamName = teamNameInput.value;
        const purpose = purposeInput.value;
        
        if (teamName.length == 0 || purpose.length == 0 || maxMember == 0) {
            alert("빈칸을 채워주세요!")
            
        } else {
        
        const result = confirm('팀 정보를 수정하겠습니까?');
        if (result) {
            updateTeamForm.action = '/team/updateTeam';
            updateTeamForm.method = 'post';
            updateTeamForm.submit();
            
            alert("수정이 완료되었습니다.");
        }
        }
    });
    
    // 패스워드 변경.
    const pokDiv = document.querySelector('#pok');
    const pnokDiv = document.querySelector('#pnok');
    const btnChangeTeamPassword = document.querySelector('#btnChangeTeamPassword');
    const changeTeamPasswordForm = document.querySelector('#changeTeamPasswordForm');
    
    const changePassword = document.querySelector('#changePassword');
    const changePassword2 = document.querySelector('#changePassword2');
    
    changePassword.addEventListener('keyup', checkPassword);
    changePassword2.addEventListener('keyup', checkPassword);
    
    
    function checkPassword() {
        if (changePassword.value == changePassword2.value) {
            pokDiv.className = 'my-2 ';
            pnokDiv.className = 'my-2 d-none';
            btnChangeTeamPassword.classList.remove('disabled');

        } else {
            pokDiv.className = 'my-2 d-none';
            pnokDiv.className = 'my-2';
            btnChangeTeamPassword.classList.add('disabled');
        }
    };
    
    btnChangeTeamPassword.addEventListener('click', function() {
        const password = changePassword.value;
        const password2 = changePassword2.value;
        
        if (password.length == 0 || password2.length == 0) {
            alert("빈칸을 채워주세요!");
            
            
        } else {
        
        const result = confirm('비밀번호를 수정할까요?');
        if (result) {
            changeTeamPasswordForm.action = '/team/changeTeamPassword';
            changeTeamPasswordForm.method = 'post';
            changeTeamPasswordForm.submit();
            
            alert("비밀번호 수정이 완료되었습니다.");
        }
        }
        });

    // 탈퇴하기
    const btnKickOutMembers = document.querySelector('#btnKickOutMembers');
    const memberForm = document.querySelector('#memberForm');
    
    btnKickOutMembers.addEventListener('click', function() {
        let checkedMember = document.querySelectorAll('[name="checkedMember[]"]:checked');
        
        if ( !checkedMember.length ) {
            alert("체크박스 하나 이상 선택해주세요.");
            return 
        }
        const result = confirm('멤버를 탈퇴시킬까요?');
        if (result) {
            memberForm.action = '/team/kickOutMembers';
            memberForm.method = 'post';
            memberForm.submit();
            
            alert("탈퇴가 완료되었습니다.");
        }
        
        
    })
    
    // 양도하기
    const btnHandOverLeader = document.querySelector('#btnHandOverLeader');
    
    btnHandOverLeader.addEventListener('click', function() {
        let checkedMember = document.querySelectorAll('[name="checkedMember[]"]:checked');
        
        if ( checkedMember.length != 1 ) {
            alert("양도할 멤버를 한 명만 선택해주세요.");
            return 
        }
        const result = confirm('정말로 양도합니까?');
        if (result) {
            memberForm.action = '/team/handOverLeader';
            memberForm.method = 'post';
            memberForm.submit();
            
            alert("양도가 완료되었습니다.");
        }
        
        
    });
    
    // 삭제하기.
    
    const btnDeleteTeam = document.querySelector('#btnDeleteTeam');
    const deleteTeamForm = document.querySelector('#deleteTeamForm');
    
    btnDeleteTeam.addEventListener('click', function() {
       const result = confirm('폐쇄하면 복구할 수 없습니다. 정말 폐쇄하겠습니까?');
       
       if (result) {
        deleteTeamForm.action = '/team/deleteTeam';
        deleteTeamForm.method = 'post';
        deleteTeamForm.submit();
        
        alert("모임 폐쇄가 되었습니다.");
    }
       
    });


});