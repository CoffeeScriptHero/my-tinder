<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>People list</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-8 offset-2">
            <div class="panel panel-default user_panel">
                <div class="panel-heading">
                    <h3 class="panel-title">User List</h3>
                </div>
                <div class="panel-body">
                    <div class="table-container">
                        <table class="table-users table" border="0">
                            <tbody>
                                <#list users as u>
                                    <tr>
                                        <td width="10">
                                            <div class="avatar-img">
                                                <a href="/messages/${u.id}">
                                                    <img class="img-circle profile-list-icon" src="${u.img}" />
                                                </a>
                                            </div>
                                        </td>
                                        <td class="align-middle">
                                            ${u.name}
                                        </td>
                                        <td class="align-middle">
                                            Builder Sales Agent
                                        </td>
                                        <td  class="align-middle">
                                            Last Login:  6/10/2017<br><small class="text-muted">5 days ago</small>
                                        </td>
                                    </tr>
                                </#list>
                                <#if users?size == 0>
                                    <p class="empty-list">
                                        You don't like anyone!
                                    </p>
                                </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>