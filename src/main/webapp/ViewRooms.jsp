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

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"/>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <title>Available rooms for period of time</title>

</head>
<body>
<t:navbar></t:navbar>
<style>
    <%@ include file="css/forms-template.css"%>
</style>
<div class="container lower forms-template">
    <h1>Hotel Management System HireMe</h1>

    <h3>Available rooms</h3>
    <c:choose>
        <c:when test="${not empty errorMessage}">
            <c:out value="${errorMessage}"/>
        </c:when>
        <c:otherwise>
            <form method="POST" action="bookRoom">
                <div class="form-group row lower">

                    <div class="col-lg-offset-3  col-lg-6">
                        <select name="availableRooms" class="selectpicker show-tick form-control"
                                data-live-search="true">
                            <c:forEach items="${availableRooms}" var="availableRooms">
                                <option
                                        value="${availableRooms.id};${availableRooms.roomType};${availableRooms.windowsExposure}">
                                    Room type: <c:out value="${availableRooms.roomType}"/>. Windows exposure: <c:out
                                        value="${availableRooms.windowsExposure}"/>. Room number: <c:out
                                        value="${availableRooms.id}"/>
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
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>

