<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ETRI Faceted Search Engine</title>
<style>
#button {
	width: 100px;
}

td {
	vertical-align: top;
}

#text {
	width: 100%
}

#tds {
	text-align: right;
	width: 100px;
}

#title {
	background-color: lightgray
}
</style>
</head>
<body>
	<table width="100%" id="title">
		<tr>
			<td><h1>ETRI Faceted Search Engine</h1></td>
		</tr>
	</table>

<table width="100%">
		<tr>
			<td width="30%"></td>
			<td width="600px">
				<form action="queries" accept-charset="utf-8">
					<table width="100%">
						<tr>
							<td><input type="text" name="query"
								placeholder="Enter keywords..." id="text"><br>
							</td>
						</tr>
						<tr>
							<td id="tds"><input type="submit" value="Search"></td>
						</tr>
					</table>
				</form>
			</td>
			<td width="30%"></td>
		</tr>
	</table>

	<table>
		<tr>
			<!-- Left column -->
			<td width="300px">
				<h2>Check List</h2> <form:form method="POST" action="filterSubmit"
					accept-charset="utf-8">
					<table>
						<%-- <tr>
							<td><form:label path="Title_Value">Title: </form:label></td>
						</tr> --%>
						<tr>
							<td><form:checkboxes items="${titleValue}"
									path="Title_Value" delimiter="<br/>" /></td>
						</tr>
						<tr>
							<td><form:label path="Keyword_Value">Key Word: </form:label></td>
						</tr>
						<tr>
							<td><form:checkboxes items="${keywordValue}"
									path="Keyword_Value" delimiter="<br/>" />
							<td>
						</tr>
						<tr>
							<td><form:label path="Publisher_Value">Publisher: </form:label></td>
						</tr>
						<tr>
							<td><form:checkboxes items="${publihserValue}"
									path="Publisher_Value" delimiter="<br/>" /></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" value="Filter"
								id="button" /></td>
						</tr>
					</table>
				</form:form>

				<%-- <form action="clear" accept-charset="utf-8">
					<input type="submit" value="Go to Main">
				</form> --%>
			</td>
			<!-- Right column -->
			<td width="50px"></td>
			<td><c:forEach items="${datasetResults}" var="dataset">
					<c:out value="${dataset.title}" />
					<br>
					<c:out value="${dataset.description}" />
					<br>
					<c:out value="${dataset.accessURL}" />
					<br>
					<c:out value="${dataset.publisher}" />
					<br>
					<c:out value="${dataset.licence}" />
					<br>
					<br>
				</c:forEach></td>
		</tr>
	</table>
</body>
</html>