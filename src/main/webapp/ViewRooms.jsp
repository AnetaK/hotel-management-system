<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

    <link href="css/forms-template.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
    <title>Available rooms for period of time</title>

</head>
<body>
<t:navbar></t:navbar>

<div class="container lower forms-template">
    <h1>Hotel Management System HireMe</h1>

    <h3>Available rooms</h3>
    <form method="POST" action="bookRoom">
        <div class="form-group row lower">

            <div class="col-lg-6">
                <select name="availableRooms" class="selectpicker show-tick form-control" data-live-search="true">
                    <c:forEach items="${availableRooms}" var="availableRooms">
                        <option
                                value="${availableRooms.id};${availableRooms.roomType};${availableRooms.windowsExposure}">
                            Room type: <c:out value="${availableRooms.roomType}"/>. Windows exposure: <c:out
                                value="${availableRooms.windowsExposure}"/>. Room number: <c:out value="${availableRooms.id}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <input type="hidden" value="${availableFrom}" name="availableFrom"/>
        <input type="hidden" value="${availableTo}" name="availableTo"/>

        <h4>Type your first name</h4>
        <input type="text" name="firstName"/>

        <h4>Type your last name</h4>
        <input type="text" name="lastName"/>
        <br>
        <button type="submit"
                name="book">Book
        </button>
    </form>

</div>

</body>
</html>

