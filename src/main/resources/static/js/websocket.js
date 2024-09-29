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

notificationSocket.onmessage = function(event) {
    const data = JSON.parse(event.data);  // 수신된 JSON 데이터를 파싱
    console.log('수신한 알림 데이터:', data);

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
                icon: 'warning',
                title: '공지사항 알림',
                text: data.notificationContent
            });
            break;
        default:
            console.log("알 수 없는 알림 유형:", data.notificationType);
    }
};


// 알림을 <li> 요소로 추가하는 함수
function addNotificationToList(title, content) {
    const notificationList = document.getElementById('notification-list');  // 알림 목록 <ul> 요소를 가져오기
    const newNotification = document.createElement('li');  // 새로운 <li> 요소 생성
    newNotification.classList.add('notification-item');  // 클래스 추가

    // 알림 아이콘과 내용을 설정
    newNotification.innerHTML = `
        <i class="bi bi-exclamation-circle text-warning"></i>
        <div>
            <h4>${title}</h4>
            <p>${content}</p>
            <p>${new Date().toLocaleTimeString()}</p> <!-- 알림 받은 시간 -->
        </div>
    `;

    notificationList.appendChild(newNotification);  // 알림 목록에 새 알림 추가
}

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



function showScheduleNotification(message) {
    alert(`일정 알림: ${message}`);
}

function showApprovalNotification(message) {
    alert(`결재 알림: ${message}`);
}

function showNoticeNotification(message) {
    alert(`공지사항 알림: ${message}`);
}


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


