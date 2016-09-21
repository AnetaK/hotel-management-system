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

    <link href="css/starter-template.css" rel="stylesheet">
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


    <form method="POST" action="cancelReservation">
        <c:choose>
            <c:when test="${emptyList}">
                You have no reservations
            </c:when>
            <c:otherwise>
                <h3>Hi ${guest.firstName} ${guest.lastName}! </h3>
                <h4>See your reservations below:</h4>
                <ul class="list-group button-middle ">
                    <c:forEach items="${reservation}" var="reservation">

                        <div class="col-lg-12 ">
                            <div class="row">
                                Room type: <c:out value="${reservation.room.roomType}"/>
                                <br>Windows exposure: <c:out value="${reservation.room.windowsExposure}"/>
                                <br> Booked from <c:out value="${reservation.bookedFrom} to ${reservation.bookedTo}"/>
                                <br> Room number <c:out value="${reservation.room.id}"/>
                                <c:choose>
                                    <c:when test="${reservation.cancelledFlag}">
                                        Cancelled
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" value="${reservation.id};${reservation.bookedFrom};${reservation.bookedTo}"
                                                name="cancel">Cancel reservation
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <br><br>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </form>

</div>

</body>
</html>

