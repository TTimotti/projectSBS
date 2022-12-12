/**
 * 
 */
 
 window.addEventListener('DOMContentLoaded', () => {
    // HTML의 Document Object들이 모두 로딩이 끝난 후에 코드들이 실행될 수 있도록 하기 위해서.
    
    const teamInfo = document.querySelector('#teamInfo');
    const teamPhoto = document.querySelector('#teamPhoto');
    const teamMember = document.querySelector('#teamMember');
    const teamManage = document.querySelector('#teamManage');
    const teamPost = document.querySelector('#teamPost');
    const teamId = document.querySelector('#teamId').value;
    
    teamInfo.addEventListener('click', function() {
        axios
        .post('/api/teamSetting/teamInfo/')
        .then(response => {
            readTeamInfo(response.data)
        })
        .catch(err => {
            console.log(err);
        });
    });
    
    function readTeamInfo(data) {
        if (data==ok) {
            
        }
    }
        
    })