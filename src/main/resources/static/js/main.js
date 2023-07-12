const signalingService = new SignalingService();
const callService = new CallService(signalingService);


document.querySelector("#start-button").addEventListener("click", () => {
    callService.startCall();
});

document.querySelector("#end-button").addEventListener("click", () => {
    callService.endCall();
});

document.querySelector("#mute-button").addEventListener("click", () => {
    callService.muteAudio();
});

document.querySelector("#unmute-button").addEventListener("click", () => {
    callService.unmuteAudio();
});


function muteAudio() {
    callService.muteAudio();
}

function unmuteAudio() {
    callService.unmuteAudio();
}