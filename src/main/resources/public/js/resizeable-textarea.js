
function activateTextFieldResize(textarea) {
    var x = textarea.scrollHeight - 8;
    console.log(x);
    textarea.setAttribute('style', 'height:' + x + 'px;overflow-y:hidden;');
    textarea.addEventListener("input", OnInput, false);
}

function OnInput() {
    this.style.height = 'auto';
    this.style.height = (this.scrollHeight)  + 'px';
}