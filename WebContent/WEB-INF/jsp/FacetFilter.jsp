<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Spring MVC Form Handling</title>
</head>
<body>
	<h2>Check List</h2>

	<form:form method="POST" action="filterSubmit" accept-charset="utf-8">
		<table>
			<tr>
				<td><form:label path="Title_Value">Title: </form:label></td>
			</tr>
			<tr>
				<td><form:checkboxes items="${titleValue}" path="Title_Value" delimiter="<br/>"/></td>
			</tr>
			<tr>
				<td><form:label path="Keyword_Value">Key Word: </form:label></td>
			</tr>
			<tr>
				<td><form:checkboxes items="${keywordValue}" path="Keyword_Value" delimiter="<br/>" /><td>
			</tr>
			<tr>
				<td><form:label path="Publisher_Value">Publisher: </form:label></td>
			</tr>
			<tr>
				<td><form:checkboxes items="${publihserValue}" path="Publisher_Value" delimiter="<br/>" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
<%-- 
	<form action="clear" accept-charset="utf-8">
		<label for="clear">Clear All: </label> <input type="submit"
			value="Click">
	</form> --%>
</body>
</html>
