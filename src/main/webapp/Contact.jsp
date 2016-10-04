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

    <title>Contact</title>

</head>
<body>

<t:navbar></t:navbar>

<style>
    <%@ include file="css/forms-template.css"%>
</style>
<div class="container lower forms-template">
    <h1>Hotel Management System HireMe</h1>

    <h3>Contact</h3>

    Hotel name:<br>
    <b><c:out value="${hotelName}"/></b><br><br>
    Address: <br>
    <c:out value="${street}"/><br>
    <c:out value="${zipcode} ${city}"/>
</div>

</body>
</html>

