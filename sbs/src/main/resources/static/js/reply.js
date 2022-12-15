/**
 * reply.js
 * 댓글 관련 Ajax 기능 구현.
 * /post/detail.html 에 포함됨.
 추
 */
window.addEventListener('DOMContentLoaded', () => {
    
    readAllReplies(); // 전체 댓글 출력.
    
    const btnReplyRegister = document.querySelector('#btnReplyRegister');
    btnReplyRegister.addEventListener('click', registerNewReply);
    
    /**
    댓글 작성
     */
    function registerNewReply() {
       
        const postId = document.querySelector('#postId').value;
        const writer = document.querySelector('#writer').value;
        const replyText = document.querySelector('#replyText').value;
       
        if (writer == '' || replyText == '') {
            alert('댓글 내용은 반드시 입력!')
            return; 
        }
        
        const data = {
            postId: postId, 
            replyText: replyText, 
            writer: writer 
        }; 
        
        axios.post('/api/reply', data) 
            .then(response => { 
                console.log(response);
                alert('#' + response.data + ' 댓글 등록 성공');
                clearInputs(); 
                readAllReplies(); // 작성한 댓글을 포함해 재호출
            })
            .catch(error => { 
                console.log(error);
            });
        
    }
    
    /**
    작성할때 텍스트만 비우기 작성자는 두기
     */
    function clearInputs() {
        document.querySelector('#replyText').value = '';
    }
    
    /**
    전체 댓글 호출
     */
    function readAllReplies() {
        const postId = document.querySelector('#postId').value; 
        
        axios
        .get('/api/reply/all/' + postId) 
        .then(response => { updateReplyList(response.data) })
        .catch(err => { console.log(err) });
    }
    
    /**
    댓글 수정창 모달로 그리기 
     */
    function updateReplyList(data) {
      
        const divReplies = document.querySelector('#replies');
        
        let str = ''; 
        for (let r of data) {
            str += '<div class="card my-2">'
                + '<div class="card-header">'
                + '<h5>' + r.writer + '</h5>'
                + '</div>'
                + '<div class="card-body">'
                + '<p>' + r.replyText + '</p>'
                + '<p> 작성 시간: ' + r.createdTime + '</p>'
                + '<p> 수정 시간: ' + r.modifiedTime + '</p>'
                + '</div>';
        
            if (r.writer == r.writer) {
                str += '<div class="card-footer">'
                    + `<button type="button" class="btnModifies btn btn-outline-primary" data-rid="${r.replyId}">수정</button>`
                    + '</div>';
            }
            str += '</div>';
        }
        divReplies.innerHTML = str;
        
     
        const buttons = document.querySelectorAll('.btnModifies');
        buttons.forEach(btn => {
            btn.addEventListener('click', getReply);
        });
    }
    
    /**
    수정할 댓글 가져오기
     */
    function getReply(event) {
    
        const rid = event.target.getAttribute('data-rid');
  
        axios
        .get('/api/reply/' + rid)
        .then(response => { showModal(response.data) })
        .catch(err => { console.log(err) });
    }
    
    const divModal = document.querySelector('#replyModal');
    const replyModal = new bootstrap.Modal(divModal); 
    const modalReplyId = document.querySelector('#modalReplyId'); 
    const modalReplyText = document.querySelector('#modalReplyText');
    const modalBtnDelete = document.querySelector('#modalBtnDelete'); 
    const modalBtnUpdate = document.querySelector('#modalBtnUpdate'); 
    
    function showModal(reply) {
    
        modalReplyId.value = reply.replyId;
        modalReplyText.value = reply.replyText;
        
        replyModal.show(); 
    }
    
    modalBtnDelete.addEventListener('click', deleteReply);
    modalBtnUpdate.addEventListener('click', updateReply);
    
    /**
    댓글 삭제.
     */
    function deleteReply(event) {
        const replyId = modalReplyId.value; 
        const result = confirm('정말 삭제?');
        if (result) {
            axios
            .delete('/api/reply/' + replyId) 
            .then(response => { 
                alert(`#${response.data} 댓글 삭제 성공`);
                readAllReplies(); 
             }) 
            .catch(err => { console.log(err) }) 
            .then(function () {
              
                replyModal.hide(); // 모달 닫기
            });
        }
    }
    
    /**
    댓글 수정 함수.
     */
    function updateReply(event) {
        
        const replyId = modalReplyId.value; 
        const replyText = modalReplyText.value; 
        if (replyText == '') {
            alert('댓글 내용은 반드시 입력');
            return;
        }
        
        const result = confirm('수정 완료?');
        if (result) {
            const data = { replyText: replyText }; 
            axios
            .put('/api/reply/' + replyId, data) 
            .then(response => { 
                alert('#' + response.data + ' 댓글 수정 성공');
                readAllReplies(); 
             }) 
            .catch(err => { console.log(err) }) 
            .then(function () { 
                replyModal.hide(); 
            });
        }
    }
    
});