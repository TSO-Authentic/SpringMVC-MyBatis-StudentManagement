<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>(BUD001)Student Search</title>
<jsp:include page="style.jsp" />
<script type="text/javascript">
	window.onload = function() {
		menu();

	}
</script>
</head>
<body class="main_body">


	<jsp:include page="header.jsp" />

	<div id="container">
		<div id="left_menu">
			<!-- menu html code exist the menu function of general.js -->
				<jsp:include page="left-menu.jsp"/>
		</div>
		<div id="main_contents">
			<div id="contents">
				<div class="search_form">
					<h3>Student Search Page</h3>
					<form:form action="/SpringMVC-MyBatis-StudentManagement/searchStudent/" method="get" modelAttribute="sStuBean">
						<table class="tableForm">
							<tr>
								<td class="tblLabel">Student No.</td>
								<td class="tblInputShort"><form:input type="text" path="stuId"
									class="txt" /></td>
								<td class="tblLabel">Student Name</td>
								<td class="tblInputShort"><form:input type="text" path="stuName"
									class="txt" /></td>
								</tr >
								<tr>
									<td class="tblLabel">Class Name</td>
									<td class="tblInputNormal" colspan="3"><form:input type="text"
										id="companyName" path="className" class="txtlong" /></td>
								</tr>
						</table>
						<br /> <input type="submit" value="Search" class="button" /> <input
							type="button" value="Reset" onclick="location.href='/SpringMVC-MyBatis-StudentManagement/setupStudentSearch/'" class="button" />
						<br />
						<br />
						<div id="errormsg">
							<label id="message">${Error}</label>
							<label style="color: blue;">${Success}</label>
						</div>
						
					</form:form>
					<div id="list">
						<form name="listForm">
							<br />
							<br /> <br />
							<table class="resultTable">
								<c:if test="${studentList!=null}">
									<tr class="tblHeader">
										<th width="5%">No</th>
										<th width="10%">Student No</th>
										<th width="25%">Student Name</th>
										<th width="40%">Class Name</th>
										<th width="10%">Registered Date</th>
										<th width="10%">Status</th>
									</tr>
								</c:if>
								<c:forEach var="data" items="${studentList}" varStatus="a">
									<tr>
										<td>${a.count}</td>
										<td>${data.studentId}</td>
										<td><a
											href="/SpringMVC-MyBatis-StudentManagement/setupUpdateStudent/${data.studentId}">${data.studentName}
										</a></td>
										<td>${data.className}</td>
										<td>${data.registerDate}</td>
										<td>${data.status}</td>
									</tr>
								</c:forEach>
							</table>
							<br />

						</form>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div class="footer">
		<span>Copyright &#169; ACE Inspiration 2016</span>
	</div>
</body>
</html>