/**
 * home.js || home.html
 * home(메인 페이지)에 있는 기능들 모음
 * 1. 검색한 리스트 목록 띄우기
 * 2. 가입하기/활동하기/관리하기 등등 활동 버튼 기능 활성화.
 * @author 서범수
 */
 
window.addEventListener('DOMContentLoaded', () => {
    
if (searchedTeams != null) {
    for (let team of searchedTeams) {
        console.log("검색된 팀들: ", team.teamName);
    }
}



// 사용자가 가입한 팀들의 번호(teamId)를 배열에 저장함.
joinedTeams();

function joinedTeams() {
    axios
    .post('/user/readByLoginUser/', loginUser)
    .then(response => { prepareSearchedTeams(response.data) })
    .catch(err => { console.log(err) });
}

// 검색된 팀들을 보여줌 (함수가 상당히 길음)
function prepareSearchedTeams(teamIds) {
    let joinedTeamsId = [];
    
    for (let t of teamIds) {
    console.log(t.teamId);
    joinedTeamsId.push(t.teamId);
    }
    
    console.log("가입된 팀들의 teamId는... ", joinedTeamsId);
        
    const showSearchedTeams = document.querySelector('#showSearchedTeams');
    // home.html의 <tbody id = showSearchedTeams>
        
    // tbody 채우기 시작 
    let str = '';
    
    // searchedTeams: teamController -> home.html (list라는 이름으로 데이터를 넘김)
    // home.html -> home.js (list라는 이름을 searchedTeams라는 이름으로 바꾸어 자바스크립트로 데이터 넘김)
    for (let st of searchedTeams) { 
        
        // (1) 팀의 현 인원수를 가져옵니다
        countMembers();
            
        function countMembers() {
            axios
            .get('/team/countMembers/' + st.teamId)
            .then(response => { readSearchedTeams(response.data) })
            .catch(err => { console.log(err) });
        }   
        
        // (2) 팀의 정보 + 팀 현 인원수 보여주기    
        function readSearchedTeams(memberNumbers) {
            console.log("가입된 인원=", memberNumbers);
            
            str += '<tr>'
                + '<td>' + st.teamId + '</td>'
                + '<td>' + st.teamName + '</td>'
                + '<td>' + st.purpose + '</td>'
                + '<td>' + st.leader + '</td>'
                + '<td>' + st.maxMember + '</td>'
                + '<td>' + memberNumbers + '</td>'
                
                // (3) 버튼의 경우의 수
                // 팀 리더와 로그인 유저가 같은 경우 (= 팀 리더)
                // 태그를 <button>으로 하면 페이지 이동이 안되서 관리하기, 활동하기 버튼은 <a><div>로 교체함.
                // 다른 것들도 통일 예정                                                                                     
                if (st.leader == loginUser) { 
                    str  
                        += '<td>'
                        + '<a href="/team/teamActivity?teamId='
                        +  st.teamId
                        + '">'
                        + `<div id="teamPage" class="btnGoToTeam btn btn-outline-primary" data-teamId="${ st.teamId }">관리하기</div>` 
                        + '</a>'
                        + '</td>'
                // 팀 리더와 로그인 유저가 같지 않으나, 유저가 이 모임에 가입했을 경우. (= 팀 회원)
                } else if (st.leader != loginUser && joinedTeamsId.includes(st.teamId)) {
                    str += '<td>'
                        + '<a href="/team/teamActivity?teamId='
                        +  st.teamId
                        + '">'
                        + `<button type="button" class="btnGoToTeam btn btn-outline-primary" data-teamId="${ st.teamId }">활동하기</button>` 
                        + '</td>'
                // 팀의 인원수가 다 찼을 경우.
                } else if (st.maxMember <= memberNumbers ) {
                    str += '<td>' 
                        + `<button type="button" class="btnForbidden btn btn-outline-primary" data-teamId="${ st.teamId }">관리자 문의</button>` 
                        + '</td>'
                // 팀 비회원
                } else if (st.leader != loginUser && !joinedTeamsId.includes(st.teamId)) {
                    str += '<td>' 
                        + `<button type="button" class="btnJoin btn btn-outline-primary" data-teamId="${ st.teamId }">가입하기</button>` 
                        + '</td>'
                }   
                + '</tr>';
                
                // (4) 완성된 항목 html로 옮기기.
                showSearchedTeams.innerHTML = str;
                
                // (5) 버튼 기능 활성화
                // 가입하기 모달
                const btnJoin = document.querySelectorAll('.btnJoin');
                
                // 관리자문의 모달
                const btnForbidden = document.querySelectorAll('.btnForbidden');
                
                btnJoin.forEach(btn => {
                    btn.addEventListener('click', getJoinModal);
                });
                
                btnForbidden.forEach(btn => {
                    btn.addEventListener('click', getForbiddenModal);
                });
        } // readSearchedTeams 끝.
    } // 반복문 끝.
} // prepareSearchedTeams 끝.
    
// ---------------------------------------------------------------- //

// 가입하기 모달
function getJoinModal(event) {
    const teamId = event.target.getAttribute('data-teamId');
    console.log("버튼에 해당하는 팀 아이디값은...", teamId);
    
    // 해당 번호의 Team 객체를 Ajax GET 방식으로 요청 후 모달 띄우기 준비과정.
    axios
    .get('/team/join/' + teamId)
    .then(response => { showModal(response.data) })
    .catch(err => { console.log(err) });
}

// 모달
const teamJoinDivModal = document.querySelector('#teamJoinModal');
const teamJoinModal = new bootstrap.Modal(teamJoinDivModal);
// 팀 아이디 칸
const modalTeamId = document.querySelector('#modalTeamId');
// 팀 비밀번호 칸
const modalTeamPassword = document.querySelector('#modalTeamPassword');
// 팀 비밀번호 입력 칸
const modalTeamPasswordInput = document.querySelector('#modalTeamPasswordInput');
// 들어가기 버튼
const modalBtnJoin = document.querySelector('#modalBtnJoin');

// (home.html에 있는) 모달에 데이터 전달 후, 모달을 보여주세요.
function showModal(team) {
    // 칸(input)에 값 집어넣기.
    modalTeamId.value = team.teamId;
    modalTeamPassword.value = team.password;
    teamJoinModal.show();
}

if (loginUser != 'anonymousUser') {
    modalBtnJoin.addEventListener('click', joinTeam);
}

function joinTeam() {
    const joinTeamId = modalTeamId.value;
    const joinTeamPassword = modalTeamPassword.value;
    const joinTeamPasswordInput = modalTeamPasswordInput.value;
    
    if (joinTeamPassword == joinTeamPasswordInput) {
        console.log("성공~");
        const data = {
            teamId: joinTeamId,
            userName: loginUser
    }
    
    alert("가입 성공!");
    
    axios
    .post('/home/success', data)
    .then(function() {
        teamJoinModal.hide();
        }
    );
    }
}

// 관리자 문의 모달

const divForbiddenModal = document.querySelector('#teamForbiddenModal');
const teamForbiddenModal = new bootstrap.Modal(divForbiddenModal);
function getForbiddenModal() {
    console.log("팀에 가입할 수 없음");
    teamForbiddenModal.show();
}
      
}); // 맨 윗줄 window.addEventListener 끝.