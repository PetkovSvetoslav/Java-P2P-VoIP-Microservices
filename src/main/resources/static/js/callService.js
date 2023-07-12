class CallService {
    constructor(signalingService) {
        this.signalingService = signalingService;
        this.config = {
            'iceServers': [
                { 'urls': 'stun:stun.stunprotocol.org:3478' },
                // Include TURN servers if available
            ]
        };
        this.peerConnection = new RTCPeerConnection(this.config);
        this.inCall = false;
        this.setupPeerConnectionEventHandlers();
        this.setupSignalingEventHandlers();
    }

    setupPeerConnectionEventHandlers() {
        this.peerConnection.oniceconnectionstatechange = () => {
            switch(this.peerConnection.iceConnectionState) {
                case 'disconnected':
                case 'failed':
                    console.error('ICE connection failed or disconnected, ending call');
                    this.endCall();
                    break;
                default:
                    // Handle other cases if necessary
                    break;
            }
        };
    }

    setupSignalingEventHandlers() {
        this.signalingService.onOffer((offer) => {
            if(this.inCall) {
                console.error('Cannot handle offer: already in a call');
                return;
            }

            this.handleOffer(offer).catch((e) => {
                console.error('Error handling offer:', e);
                this.endCall();
            });
        });

        this.signalingService.onAnswer((answer) => {
            if(!this.inCall) {
                console.error('Cannot handle answer: not in a call');
                return;
            }

            this.handleAnswer(answer).catch((e) => {
                console.error('Error handling answer:', e);
                this.endCall();
            });
        });

        this.signalingService.onIceCandidate((candidate) => {
            if(!this.inCall) {
                console.error('Cannot handle ICE candidate: not in a call');
                return;
            }

            this.handleIceCandidate(candidate).catch((e) => {
                console.error('Error adding received ICE candidate:', e);
                this.endCall();
            });
        });
    }

    async handleOffer(offer) {
        try {
            await this.peerConnection.setRemoteDescription(offer);
            const answer = await this.peerConnection.createAnswer();
            await this.peerConnection.setLocalDescription(answer);
            this.signalingService.sendAnswer(answer);
            this.inCall = true;
        } catch (error) {
            console.error('Error handling offer:', error);
            // Display an error message to the user
        }
    }

    async handleAnswer(answer) {
        await this.peerConnection.setRemoteDescription(answer);
    }

    async handleIceCandidate(candidate) {
        await this.peerConnection.addIceCandidate(candidate);
    }

    async startCall() {
        const constraints = { audio: true, video: true };
        const stream = await navigator.mediaDevices.getUserMedia(constraints);
        stream.getTracks().forEach((track) => this.peerConnection.addTrack(track, stream));
        this.inCall = true;
    }

    async endCall() {
        this.peerConnection.close();
        this.peerConnection = new RTCPeerConnection(this.config);
        this.setupPeerConnectionEventHandlers();
        this.inCall = false;
    }
}
