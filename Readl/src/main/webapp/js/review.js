button = document.getElementById('button');

if (button !== null) {
    button.addEventListener('click', function (e) {
        e.preventDefault();

        var description = document.getElementById('description').value.replace(/[</>]+/, '');
        if (description.trim() === '') {
            document.getElementById('message').value = 'Заполните поле';
            return false;
        }

        var xhr = new XMLHttpRequest();
        xhr.open('post', urlSend, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send("content=" + encodeURIComponent(description) + "&bookId=" + bookId);

        document.getElementById('description').value = '';
    });
};

count = 0;
getReviews();

function getReviews() {
    var xhr = new XMLHttpRequest();
    xhr.open('post', urlGet, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send('count=' + count + '&bookId=' + bookId);

    xhr.onreadystatechange = function() {
        if(xhr.readyState == 4) {
            if(xhr.status == 200) {
                var data = xhr.responseText;

                if(data != "no reviews") {
                    data = JSON.parse(data);

                    var comments = document.getElementById('reviews');
                    for(var i = 0; i < data.length; i++) {
                        comments.appendChild(createReview(data[i]));
                    }

                    if(count < data[data.length-1].id){
                        count = data[data.length-1].id;
                    }
                }
            }
        }else {
            document.getElementById('reviews').value = 'Loading';
        }
    };
    setTimeout(function() {
        getReviews();
    }, 7000);

    createReview = function (element){
        var review = document.createElement('div');
        review.className = 'review';

        var top = document.createElement('div');
        top.className = 'row border-bottom';

        var author = document.createElement('div');
        author.className = 'col-9';
        authorText = document.createTextNode(element.nickname);
        author.appendChild(authorText);

        var date = document.createElement('div');
        date.className = 'col-3';
        dateText = document.createTextNode(getDate(element.date));
        date.appendChild(dateText);

        var content = document.createElement('div');
        contentText = document.createTextNode(element.content);
        content.appendChild(contentText);

        top.appendChild(author);
        top.appendChild(date);
        review.appendChild(top);
        review.appendChild(content);
        return review;
    }

    function getDate(timestamp){
        return timestamp.substring(0, 16);
    };
}