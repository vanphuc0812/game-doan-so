<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="cybersoft.javabackend.java18.gamedoanso.utils.UrlUtils" %>
<%@ page import="cybersoft.javabackend.java18.gamedoanso.model.Guess" %>
<%@ page import="java.util.List" %>
<%@ page import="cybersoft.javabackend.java18.gamedoanso.model.GameSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <title>Đoán Số</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand font-weight-bold" href="#">GUESS GAME</a>
    <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link font-weight-bold" href="<%=request.getContextPath() + UrlUtils.HOME%>">Home<span
                        class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link font-weight-bold" href="<%=request.getContextPath() + UrlUtils.GAME%>">Game</a>
            </li>
            <li class="nav-item">
                <a class="nav-link font-weight-bold"
                   href="<%=request.getContextPath() + UrlUtils.RANK%>">Ranking</a>
            </li>
        </ul>
    </div>
    <div class="nav-item dropdown">
        <a class="nav-link dropdown-toggle font-weight-bold" href="#" role="button" data-toggle="dropdown"
           aria-expanded="false">
            ${sessionScope.currentUser.username}
        </a>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<%=request.getContextPath() + UrlUtils.LOGOUT%>">Log out</a></li>
        </ul>
    </div>
</nav>

<form action="<%=request.getContextPath() + UrlUtils.NEW_GAME%>" method="post" class="mt-2 mr-5 float-right">
    <input type="text" name="game-session" value="${game.id}" hidden>
    <div class="form-row align-items-center">
        <button type="submit" class="btn btn-outline-success btn-lg">NEW GAME</button>
    </div>
</form>
<div class="container">
    <div class="row justify-content-center mt-5 clearfix">
        <div class="col-md-8" ${game.isCompleted ? 'hidden': ''}>
            <h2 class="text text-primary text-center">LET'S GUESS</h2>
        </div>
        <div class="col-md-8" ${game.isCompleted ? '': 'hidden'}>
            <h2 class="text text-success text-center">PINGO!!! PINGO!!! PINGO!!!</h2>
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <form action="<%=request.getContextPath() + UrlUtils.GAME%>"
                  method="post" ${game.isCompleted ? 'hidden': ''}>
                <input type="text" name="game-session" value="${game.id}" hidden>
                <div class="form-group form-row">
                    <label for="number"></label>
                    <input type="number" name="guess" class="form-control form-control-lg text-center col-4 offset-4"
                           id="number" required ${game.isCompleted ? 'readonly': ''}>
                </div>
                <div class="form-row align-items-center">
                    <button type="submit" class="btn btn-outline-primary btn-lg col-4 offset-4">Guess
                    </button>
                </div>
            </form>
        </div>
    </div>
    <div class="row justify-content-center mt-5">
        <div class="col-md-8">
            <div>
                <h5 font-weight-bold>${game.id}</h5>
            </div>
            <div class="table-responsive">
                <table class="table table-borderless">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Guess number</th>
                        <th scope="col">Message</th>
                        <th scope="col">Guess time</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="guess" items="${game.getReversedGuess()}" varStatus="loop">
                        <c:choose>
                            <c:when test="${guess.result == 0}">
                                <tr class="table-success">
                                    <th scope="row">${loop.index + 1}</th>
                                    <td>${guess.value}</td>
                                    <td>PINGO!!!</td>
                                    <td>${guess.timestamp}</td>
                                </tr>
                            </c:when>
                            <c:when test="${guess.result == -1}">
                                <tr class="table-danger">
                                    <th scope="row">${loop.index + 1}</th>
                                    <td>${guess.value}</td>
                                    <td>Guess number is lower than target</td>
                                    <td>${guess.timestamp}</td>
                                </tr>
                            </c:when>
                            <c:when test="${guess.result == 1}">
                                <tr class="table-warning">
                                    <th scope="row">${loop.index + 1}</th>
                                    <td>${guess.value}</td>
                                    <td>Guess number is higher than target</td>
                                    <td>${guess.timestamp}</td>
                                </tr>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
