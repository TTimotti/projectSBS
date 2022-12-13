/**
 * team.js
 * home에 있는 가입하기 버튼 삽입하는 자바스크립트
 */

/*
   함수보다 다른 것들이 먼저 실행됨.
   그래서 함수로 설정하는 것이 속도조절에 편한듯.
   나는 showshowshow()에 teamList를 넣지 않았음.
   그래서 느려서 반응이 없었음
   그래서 showshowshow()에 data, teamList를 넣어서 속도 조절.
*/

window.addEventListener('DOMContentLoaded', () => {
	if (searchedTeams != null) {
		for (let team of searchedTeams) {
			console.log(team.teamName);
		}

		joinedTeamsIdMethod();

		function joinedTeamsIdMethod() {
			axios
				.post('/user/readByLoginUser/', loginUser)
				.then(response => { showSearchedTeams(response.data) })
				.catch(err => { console.log(err) });
		}

		function showSearchedTeams(data) {
			let joinedTeamsId = [];

			for (let d of data) {
				console.log(d.teamId);
				joinedTeamsId.push(d.teamId);
			}

			console.log(joinedTeamsId);

			const showSearchedTeams = document.querySelector('#showSearchedTeams');

			//***** 
			let str = '';

			for (let st of searchedTeams) {
				str += '<tr>'
					+ '<td>' + st.teamId + '</td>'
					+ '<td>' + st.teamName + '</td>'
					+ '<td>' + st.purpose + '</td>'
					+ '<td>' + st.teamLeader + '</td>'
					+ '<td>' + st.maxMember + '</td>'

				if (st.teamLeader != loginUser && !joinedTeamsId.includes(st.teamId)) {
					str += '<td>'
						+ `<button type="button" class="btnJoin btn btn-outline-primary" data-teamId="${st.teamId}">가입하기</button>`
						+ '</td>'
				} else if (st.teamLeader != loginUser && joinedTeamsId.includes(st.teamId)) {
					str += '<td>'
						+ `<button type="button" class="btnJoin btn btn-outline-primary" data-teamId="${st.teamId}">활동하기</button>`
						+ '</td>'
				} else if (st.teamLeader == loginUser) {
					str += '<td>'
						+ `<button type="button" class="btnJoin btn btn-outline-primary" data-teamId="${st.teamId}">관리하기</button>`
						+ '</td>'
				}
				+ '</tr>';
			}

			showSearchedTeams.innerHTML = str;

			const btnJoin = document.querySelectorAll('.btnJoin');

			btnJoin.forEach(btn => {
				btn.addEventListener('click', getJoinModal);
			});
			//*****

		}

		// ---------------------------------------------------------------- //
		function getJoinModal(event) {
			console.log("버튼의 속성을 가져옵니다.", event.target);
			// 클릭된 버튼의 속성값을 읽어오기.
			const teamId = event.target.getAttribute('data-teamId');
			console.log("버튼에 해당하는 아이디값은...", teamId);

			// 해당 번호의 Team 객체를 Ajax GET 방식으로 요청 후 모달 띄우기 준비과정.
			axios
				.get('/team/join/' + teamId)
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
	}
});