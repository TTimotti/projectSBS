/**
 * 
 */
 
 window.addEventListener('DOMContentLoaded', () => {
    readByloginUser();
    
    
    
    function readByloginUser() {
        
        axios
        .post('/user/readByLoginUser/', loginUser)
        .then(response => {showYourList(response.data)})
        .catch(err => { console.log(err)});
    }
    
    function showYourList(data) {
        const tbodyTeams = document.querySelector('#teams');
        
        let str = '';
        
        for (let r of data) {
            str += '<tr>'
                + '<td>' + r.teamName + '</td>' 
                + '<td>' + r.createdTime + '</td>'
                + '</tr>';
        }
        
        tbodyTeams.innerHTML = str;
    }
});