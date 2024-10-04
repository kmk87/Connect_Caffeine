document.addEventListener('DOMContentLoaded', function () {
toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": true,
    "progressBar": true,
    "positionClass": "toast-top-right",
    "preventDuplicates": true,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",  // 알림이 표시되는 시간 (5초)
    "extendedTimeOut": "1000",  // 마우스가 알림 위에 있을 때 추가로 표시되는 시간 (1초)
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};


	
const notificationSocket = new WebSocket('ws://localhost:8100/ws/notifications');

notificationSocket.onopen = function() {
    console.log('알림 WebSocket 연결 성공');
};

function timeSince(date) {
    const now = new Date();
    const seconds = Math.floor((now - new Date(date)) / 1000);
    let interval = Math.floor(seconds / 31536000);

    if (interval > 1) {
        return `${interval} 년 전`;
    }
    interval = Math.floor(seconds / 2592000);
    if (interval > 1) {
        return `${interval} 달 전`;
    }
    interval = Math.floor(seconds / 86400);
    if (interval > 1) {
        return `${interval} 일 전`;
    }
    interval = Math.floor(seconds / 3600);
    if (interval > 1) {
        return `${interval} 시간 전`;
    }
    interval = Math.floor(seconds / 60);
    if (interval > 1) {
        return `${interval} 분 전`;
    }
    return "방금 전";
}


// WebSocket 메시지를 받았을 때 상대 시간 계산
notificationSocket.onmessage = function(event) {
    const data = JSON.parse(event.data);
    const timeAgo = timeSince(data.notificationTime);  // 서버에서 받은 타임스탬프를 사용

    const newNotification = `
        <li class="notification-item" data-id="${data.notificationId}">
            <i class="bi bi-check-circle text-success"></i>
            <div>
                <a th:href="${data.relatedLink}">
                    <p class="notification-link" style="color:#333;">${data.notificationContent}</p>
                </a>
                <p>${timeAgo}</p>
            </div>
        </li>
    `;
 // 알림 목록에 새로운 알림 추가
   $('#notification-list').append(newNotification);  // 새로운 알림이 하단에 추가되도록 변경

	

 
    // 읽지 않은 알림 카운트 텍스트도 업데이트
    const unreadCountText = document.querySelector('.dropdown-header span');
    unreadCountText.textContent = parseInt(unreadCountText.textContent) + 1;

    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    });

    switch (data.notificationType) {
        case "SCHEDULE":
            Toast.fire({
                icon: 'success',
                title: '일정 알림',
                text: data.notificationContent
            });
            
            break;
        case "APPROVAL":
            Toast.fire({
                icon: 'info',
                title: '결재 알림',
                text: data.notificationContent
            });
            break;
        case "NOTICE":
            Toast.fire({
                icon: 'success',
                title: '공지사항 알림',
                text: data.notificationContent
            });
           
            break;
        default:
            console.log("알 수 없는 알림 유형:", data.notificationType);
    }
     // 알림 목록에 새로운 알림 추가 (헤더에 실시간 반영)
    const notificationList = document.getElementById('notification-list');
   /* const newNotification = `
        <li class="notification-item" data-id="${data.id}">
            <i class="bi bi-check-circle text-success"></i>
            <div>
                <a href="${data.relatedLink}" class="notification-link" style="color:#333;">${data.notificationContent}</a>
                <p>${data.relativeTime}</p>
            </div>
        </li>`;
    notificationList.insertAdjacentHTML('afterbegin', newNotification);  // 새 알림 추가*/

    // 읽지 않은 알림 개수 업데이트
    const badge = document.querySelector('.badge-number');
    badge.textContent = parseInt(badge.textContent) + 1;
};


 

notificationSocket.onclose = function() {
    console.log('WebSocket 연결 종료');
};

notificationSocket.onerror = function(error) {
    console.log('WebSocket 에러:', error);
    alert('서버와 연결이 끊겼습니다. 다시 시도 중입니다...');
    
    // 일정 시간 후 다시 연결 시도 (예: 5초 후)
    setTimeout(() => {
       notificationSocket = new WebSocket('ws://localhost:8100/ws/notifications');
    }, 5000);
};


/*============ 여기서부터 채팅 웹소켓 함수 적으시면되요 ======================*/



// 채팅 WebSocket 연결
const chatSocket = new WebSocket('ws://localhost:8100/ws/chat');

chatSocket.onopen = function() {
    console.log('채팅 WebSocket 연결 성공');
};


chatSocket.onmessage = function(event) {
    // 채팅 메시지 처리 로직
    console.log('채팅 메시지:', event.data);
};

chatSocket.onclose = function() {
    console.log('WebSocket 연결 종료');
};


});