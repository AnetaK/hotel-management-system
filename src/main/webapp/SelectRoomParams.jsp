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
    <title>Room params</title>

</head>
<body>

<t:navbar></t:navbar>

<div class="container lower forms-template">
    <h1>Hotel Management System HireMe</h1>

    <h3>Select parameters for room filtering</h3>

    <form metod="GET" action="availableRooms">

        <div class="form-group row lower">
            <label class="col-lg-3 control-label"><b>Room type</b></label>

            <div class="col-lg-6">
                <select name="roomType"  class="selectpicker show-tick form-control" data-live-search="true">
                    <c:forEach items="${roomType}" var="roomType">
                        <option
                                value="${roomType}"><c:out value="${roomType}"/></option>
                    </c:forEach>
                </select>

            </div>
        </div>


        <div class="form-group row lower">
            <label class="col-lg-3 control-label"><b>Room exposure</b></label>

            <div class="col-lg-6">
                <select name="windowsExposure"  class="selectpicker show-tick form-control" data-live-search="true">
                    <c:forEach items="${windowsExposure}" var="windowsExposure">
                        <option
                                value="${windowsExposure}"><c:out value="${windowsExposure}"/></option>
                    </c:forEach>
                </select>

            </div>
        </div>

        <div class="form-group row lower">
            <label class="col-lg-3 control-label"><b>Available from</b></label>

            <div class="col-lg-6">
                <select name="availableFrom"  class="selectpicker show-tick form-control" data-live-search="true">
                    <c:forEach items="${calendar}" var="calendar">
                        <option
                                value="${calendar}"><c:out value="${calendar}"/></option>
                    </c:forEach>
                </select>

            </div>
        </div>

        <div class="form-group row lower">
            <label class="col-lg-3 control-label"><b>Available to</b></label>

            <div class="col-lg-6">
                <select name="availableTo"  class="selectpicker show-tick form-control" data-live-search="true">
                    <c:forEach items="${calendar}" var="calendar">
                        <option
                                value="${calendar}"><c:out value="${calendar}"/></option>
                    </c:forEach>
                </select>

            </div>
        </div>

        <button type="submit"
                name="search">Search
        </button>
    </form>
</div>

</body>
</html>

