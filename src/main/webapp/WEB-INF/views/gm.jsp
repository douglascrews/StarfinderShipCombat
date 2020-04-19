<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
</head>
<body>
	<h1>${title}</h1>

	<h2>Ships:</h2>
	<c:if test="${not empty ships}">
		<ul>
			<c:forEach var="ship" items="${ships}">
			<form action="updateShip" method="POST">
		         Ship Name: <input type="text" name="name" value="${ship.getName()}">
		                  <input type="submit" value="Submit" />
		         
		         Faction:
		         <input type="radio" name="faction" checked=${ship.getFaction().equals("PLAYER") ? "checked" : ""} value="PLAYER">PLAYER
				<input type="radio" name="faction" checked=${ship.getFaction().equals("NPC") ? "checked" : ""} value="NPC">NPC
				${ship.getName()} - ${ship.getFaction()}<br/><pre>${ship.getStatus()}</pre>
			</form>
			</c:forEach>
		</ul>
	</c:if>

      <form action="updateShip" method="POST">
         Ship Name: <input type="text" name="ship_name">
         <br/>
         Last Name: <input type="text" name="last_name" />
         <br />
         Player Name: <input type="text" name="playerName" />
         <br />
         <input type="submit" value="Submit" />
      </form>

</body>
</html>
