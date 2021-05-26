<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SearchEngine_5</title>
<style>
#text {
	width: 100%
}

#tds {
	text-align: center
}

#title {
	background-color: gray
}

</style>
</head>
<body>
	<table width="100%" id="title">
		<tr>
			<td><h1>ETRI Faceted Search Engine</h1></td>
		</tr>
	</table>
	<br>
	<br>
	<br>
	<br>
	<table width="100%">
		<tr>
			<td width="30%"></td>
			<td width="600px">
				<form action="queries" accept-charset="utf-8">
					<table width="100%">
						<tr>
							<td><input type="text" name="query"
								placeholder="Enter keywords..." id="text"><br>
							<br></td>
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
</body>
</html>
