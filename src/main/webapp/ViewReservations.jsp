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

    <h3>Hi ${firstName} ${lastName} <br> Find your reservations below</h3>
    <form method="POST" action="cancelReservation">
        <div class="form-group row lower">

            <div class="col-lg-6">
                <select name="reservation" class="selectpicker show-tick form-control" data-live-search="true">
                    <c:forEach items="${reservation}" var="reservation">
                        <option
                                value="${reservation.id}">
                            Room type: <c:out value="${reservation.room.roomType}"/>. Windows exposure: <c:out
                                value="${reservation.room.windowsExposure}"/>. Booked for following days:
                            <c:forEach items="${reservation.room.bookedDates}" var="list">
                                ${list}
                            </c:forEach>

                            <c:choose>
                                <c:when test="${reservation.cancelledFlag == '1'}">
                                    Cancelled
                                </c:when>
                                <c:otherwise>
                                    <button type="submit"
                                            name="cancel">Cancel reservation
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

    </form>

</div>

</body>
</html>

