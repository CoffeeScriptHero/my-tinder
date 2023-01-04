<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>Like page</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body style="background-color: #f5f5f5;">

<div class="col-4 offset-4">
    <div class="card">
        <form action="/like-form?name=${user.name}&img=${user.img}" method="post">
            <div class="card-body">
                <div class="row">
                    <div class="col-12 col-lg-12 col-md-12 text-center">
                        <a href="/messages/${user.id}">
                            <img src="${user.img}" alt=""
                                 class="mx-auto rounded-circle img-fluid profile-like-icon" />
                        </a>
                        <h3 class="mb-0 text-truncated">${user.name}</h3>
                        <br>
                    </div>
                    <div class="col-12 col-lg-6">
                        <button type="submit" value="false" name="submit" class="btn btn-outline-danger btn-block"><span
                                    class="fa fa-times"></span> Dislike
                        </button>
                    </div>
                    <div class="col-12 col-lg-6">
                        <button type="submit" value="true" name="submit" class="btn btn-outline-success btn-block"><span
                                    class="fa fa-heart"></span> Like
                        </button>
                    </div>
                    <!--/col-->
                </div>
                <!--/row-->
            </div>
            <!--/card-block-->
        </form>
    </div>
</div>

</body>
</html>