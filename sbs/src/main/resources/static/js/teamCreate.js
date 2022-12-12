/**
 * 
 */
 
 window.addEventListener('DOMContentLoaded', function() {
    
        const btnSubmit = document.querySelector('#btnSubmit');
    
        // 아이디 중복 체크
        const teamNameInput = document.querySelector('#teamName');
        const okDiv = document.querySelector('#ok');
        const nokDiv = document.querySelector('#nok');

    teamNameInput.addEventListener('change', function() {
        const teamName = teamNameInput.value;

        axios.get('/team/checkid?teamName=' + teamName)
            .then(response => { displayCheckResult(response.data) })
            .catch(err => { console.log(err); });
    });

    function displayCheckResult(data) {

        if (data == 'ok') {
            okDiv.className = 'my-2 ';
            nokDiv.className = 'my-2 d-none';
            btnSubmit.classList.remove('disabled'); // 버튼 활성화
        } else {
            okDiv.className = 'my-2 d-none';
            nokDiv.className = 'my-2';
            btnSubmit.classList.add('disabled');
        }

    }
    
});