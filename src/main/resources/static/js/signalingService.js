class SignalingService {
    constructor() {
        this.connect();

        // Handlers are empty functions by default
        this.offerHandler = () => {};
        this.answerHandler = () => {};
        this.iceCandidateHandler = () => {};
    }

    connect() {
        // replace 'wss://your-signaling-server.com' with the real URL of your signaling server
        this.websocket = new WebSocket('wss://your-signaling-server.com');

        this.websocket.onopen = () => {
            console.log("Connected to the signaling server");
            //  add code to authenticate the user if required by your server
        };

        this.websocket.onerror = (error) => {
            console.error("WebSocket error:", error);
            // add code to attempt to reconnect after an error
        };

        this.websocket.onclose = (event) => {
            console.log("WebSocket connection closed:", event);
            // add code to attempt to reconnect after the connection is closed
        };

        this.websocket.onmessage = (message) => {
            let data;
            try {
                data = JSON.parse(message.data);
            } catch (e) {
                console.error("Failed to parse message data:", e);
                return;
            }

            this.websocket.onerror = (error) => {
                console.error("WebSocket error:", error);
                // Display an error message to the user
                document.getElementById('error-message').textContent = "An error occurred. Please try again.";
            };

            switch(data.type) {
                case 'offer':
                    this.offerHandler(data.offer);
                    break;
                case 'answer':
                    this.answerHandler(data.answer);
                    break;
                case 'candidate':
                    this.iceCandidateHandler(data.candidate);
                    break;
                default:
                    console.error("Unknown message type:", data.type);
                    break;
            }
        };
    }

    send(data) {
        if (this.websocket.readyState === WebSocket.OPEN) {
            this.websocket.send(JSON.stringify(data));
        } else {
            console.error("WebSocket is not open: ReadyState is ", this.websocket.readyState);
        }
    }

    sendOffer(offer) {
        this.send({ type: 'offer', offer: offer });
    }

    sendAnswer(answer) {
        this.send({ type: 'answer', answer: answer });
    }

    sendIceCandidate(candidate) {
        this.send({ type: 'candidate', candidate: candidate });
    }

    onOffer(handler) {
        this.offerHandler = handler;
    }

    onAnswer(handler) {
        this.answerHandler = handler;
    }

    onIceCandidate(handler) {
        this.iceCandidateHandler = handler;
    }


}
