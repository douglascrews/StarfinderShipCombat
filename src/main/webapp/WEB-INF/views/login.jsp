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

      <form action="#" method="POST">
      	<b>Select a player:</b>
         <select name="playerName" onchange="submit();">
            <option value="">Choose a crewmember</option>
         	<c:forEach items="${players}" var="player">
	         	<option value="${player.getName()}">${player.getName()}</option>
         	</c:forEach>
         </select>
         <input type="hidden" name="action" value="login" />
      </form><br/>
		<br/>
      <form action="#" method="POST">
      	<b>Create a character:</b>
         Player Name: <input type="text" name="playerName" value="${player == null ? 'James T. Kirk' : player.getName()}" /><br />
         Computers Bonus: <input type="text" name="computersBonus" value="${player == null ? '+20' : player.getComputersBonus()}" /><br />
         Diplomacy Bonus: <input type="text" name="diplomacyBonus" value="${player == null ? '+20' : player.getDiplomacyBonus()}" /><br />
         Engineering Bonus: <input type="text" name="engineeringBonus" value="${player == null ? '+20' : player.getEngineeringBonus()}" /><br />
         Gunnery Bonus: <input type="text" name="gunneryBonus" value="${player == null ? '+20' : player.getGunneryBonus()}" /><br />
         Intimidate Bonus: <input type="text" name="intimidateBonus" value="${player == null ? '+20' : player.getIntimidateBonus()}" /><br />
         Pilot Bonus: <input type="text" name="pilotBonus" value="${player == null ? '+20' : player.getPilotBonus()}" /><br />
         My Ship: <select name="shipName">
         	<c:forEach items="${ships}" var="ship">
	         	<option value="${ship.getName()}">${ship.getName()}</option>
         	</c:forEach>
         </select><br />
         <input type="hidden" name="action" value="createPlayer" />
         <input type="submit" value="Submit" />
      </form>

	<c:if test="${not empty player}">
		You are logged in as "${player.getName()}".
	</c:if>
	<c:if test="${empty player}">
		You are not logged in.
	</c:if>
</body>
</html>
