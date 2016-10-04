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

    <title>Room params</title>

</head>
<body>

<t:navbar></t:navbar>
<style>
    <%@ include file="css/forms-template.css"%>
</style>
<div class="container lower forms-template">
    <h1>Hotel Management System HireMe</h1>

    <h3>Select parameters for room filtering</h3>

    <form method="GET" action="availableRooms">

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

