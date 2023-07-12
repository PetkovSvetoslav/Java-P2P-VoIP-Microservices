var socket = new WebSocket('ws://localhost:8080/messages');

function fetchMessages() {
    fetch('/messages/1/2')
        .then(response => response.json())
        .then(messages => {
            var chatBox = document.getElementById('chat-box');
            chatBox.innerHTML = '';

            messages.forEach(message => {
                addMessageToChat(message.content, message.timestamp);
            });
        });
}

setInterval(fetchMessages, 1000);

function addMessageToChat(message, timestamp) {
    var chatBox = document.getElementById('chat-box');
    var messageElement = document.createElement('p');

    var date = timestamp ? new Date(timestamp).toLocaleTimeString() : 'No timestamp';
    messageElement.textContent = message + ' (' + date + ')';
    chatBox.appendChild(messageElement);

    chatBox.scrollTop = chatBox.scrollHeight;
}

socket.onmessage = function(event) {
    var message = JSON.parse(event.data);
    addMessageToChat(message.content, message.timestamp);
};

var loggedInUserId = 1;
var selectedContactId = 2;

document.getElementById('message-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var messageInput = document.getElementById('message-input');
    var message = messageInput.value;

    if (message) {
        fetch('/messages', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfTokenValue()
            },
            body: JSON.stringify({
                content: message,
                senderId: loggedInUserId,
                receiverId: selectedContactId,
            }),
        })
            .then(response => response.json())
            .then(data => {
                addMessageToChat(message, data.timestamp);
                messageInput.value = '';
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }
});

function openConversation(senderId, receiverId) {
    var chatBox = document.getElementById('chat-box');
    chatBox.innerHTML = '';

    fetch('/messages/' + senderId + '/' + receiverId)
        .then(response => response.json())
        .then(data => {
            data.forEach(message => {
                addMessageToChat(message.content, message.timestamp);
            });
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

var contactElements = document.getElementsByClassName('contact');

for (var i = 0; i < contactElements.length; i++) {
    contactElements[i].addEventListener('click', function() {
        var receiverId = this.getAttribute('data-id');
        openConversation(loggedInUserId, receiverId);
    });
}

function getCsrfTokenValue() {
    return document.querySelector('meta[name="_csrf"]').getAttribute('content');
}

socket.onopen = function(event) {
    console.log('WebSocket connection opened');
};

socket.onclose = function(event) {
    console.log('WebSocket connection closed');
};

socket.onerror = function(error) {
    console.log('WebSocket error: ' + error.message);
};

function sendMessage(content) {
    var message = {
        content: content
    };
    socket.send(JSON.stringify(message));
}
