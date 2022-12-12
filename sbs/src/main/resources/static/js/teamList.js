/**
 * 
 */
 
 window.addEventListener('DOMContentLoaded', () => {
    prepareReadTeamList();
    
    // function 모음
    function prepareReadTeamList() {
        axios
        .get('/home/all/')
        .then(response => { readTeamList(response.data) })
        .catch(err => { console.log(err) });
    } // prepareReadTeamList() 끝.
    
    //----------------------------------------------------------//
    function readTeamList(data) {
        // field
        const tbodyTeams = document.querySelector('#teams');
        let joinedTeamsId = [];
        //console.log(joinedTeamsId2);
        
        joinedTeamsIdMethod(data);
        
        function joinedTeamsIdMethod(data) {
            axios
            .post('/user/readByLoginUser/', loginUser)
            .then(response => {showshowshow(response.data, data)})
            .catch(err => { console.log(err)});
        }
        
        /*
            함수보다 다른 것들이 먼저 실행됨.
            그래서 함수로 설정하는 것이 속도조절에 편한듯.
            나는 showshowshow()에 teamList를 넣지 않았음.
            그래서 느려서 반응이 없었음
            그래서 showshowshow()에 data, teamList를 넣어서 속도 조절.
         */
        
        
        function showshowshow(data, teamList) {
            for (let r of data) {
                console.log(r.teamId);
                joinedTeamsId.push(r.teamId);
                
            }
            console.log(joinedTeamsId);
            
            //***** 
            let str = '';
            
            for (let r of teamList) {
                str += '<tr>'
                    + '<td>' + r.teamName + '</td>'
                    + '<td>' + r.teamLeader + '</td>'
                    + '<td>' + r.purpose + '</td>'
                    + '<td>' + r.maxMember + '</td>'
                    // + `<td data-teamid="${r.teamId}"></td>`
                    // 가입된 회원인 경우에도 보이고 싶지 싶음.
                
                if (r.teamLeader != loginUser && !joinedTeamsId.includes(r.teamId)) {
                    str += '<td>' 
                        + `<button type="button" class="btnJoin btn btn-outline-primary" data-rid="${ r.teamId }">들어가기</button>` 
                        + '</td>'
                }
                
                    + '</tr>';
            }
            tbodyTeams.innerHTML = str;
            
            const btnJoin = document.querySelectorAll('.btnJoin');
                
            btnJoin.forEach(btn => {
                btn.addEventListener('click', getJoinModal);
            });
            //*****
            
        }
    
    
    } // readTeamList(data) 끝.
    
    
    // ---------------------------------------------------------------- //
    function getJoinModal(event) {
        console.log(event.target);
        // 클릭된 버튼의 속성값을 읽어오기.
        const rid = event.target.getAttribute('data-rid');
        console.log(rid);
        
        // 해당 번호의 관련된 Team 객체를 Ajax GET 방식으로 요청 후 모달 띄우기 준비과정.
        axios
        .get('/home/join/' + rid)
        .then(response => { showModal(response.data) })
        .catch(err => { console.log(err) });
    }
    
    const divModal = document.querySelector('#teamJoinModal');
    const teamJoinModal = new bootstrap.Modal(divModal);
    const modalTeamId = document.querySelector('#modalTeamId');
    const modalTeamPassword = document.querySelector('#modalTeamPassword');
    const modalTeamPasswordInput = document.querySelector('#modalTeamPasswordInput');
    const modalBtnJoin = document.querySelector('#modalBtnJoin');
    
    
    function showModal(team) {
        modalTeamId.value = team.teamId;
        modalTeamPassword.value = team.teamPassword;
        
        teamJoinModal.show();
    }
    
    modalBtnJoin.addEventListener('click', joinTeam);
    
    function joinTeam(event) {
        const joinTeamId = modalTeamId.value;
        const joinTeamPassword = modalTeamPassword.value;
        const joinTeamPasswordInput = modalTeamPasswordInput.value;
        
        
        if (joinTeamPassword == joinTeamPasswordInput) {
            console.log("성공~");
            const data = {
                teamId: joinTeamId,
                userName: loginUser
            }
            
            axios
            .post('/home/success', data)
            .then(function() {
                teamJoinModal.hide();
            }
            );
        }
    }
    

    

});/**
 * 
 */