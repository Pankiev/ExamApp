setInterval(updateTimer, 1000);

function updateTimer() {
    const timerSpan = document.getElementById("form:timeLeft");
    const currentValue = timerSpan.innerText;
    const updatedValue = parseInt(currentValue) - 1;
    timerSpan.innerText = updatedValue.toString();
    if (updatedValue < -2)
        location.reload();
}