button = document.getElementById('button');
const add = "Add";
const del = "Delete";

if (button !== null) {
    button.addEventListener('click', function (e) {
        e.preventDefault();

        var xhr = new XMLHttpRequest();
        xhr.open('post', url, false);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        if (button.value === add) {
            xhr.send("bookId=" + bookId + "&action=" + encodeURIComponent(add));
            button.value = del;
        } else {
            xhr.send("bookId=" + bookId + "&action=" + encodeURIComponent(del));
            button.value = add;
        }
    })
};