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

	<h2>Links:</h2>
	<c:if test="${not empty links}">
		<ul>
			<c:forEach var="listValue" items="${links.keySet()}">
				<li><a href="${links.get(listValue)}">${listValue}</a></li>
			</c:forEach>
		</ul>
	</c:if>

</body>
</html>
