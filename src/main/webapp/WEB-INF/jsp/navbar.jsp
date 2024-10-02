<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
	<nav
		class="navbar navbar-expand bg-secondary navbar-dark sticky-top px-4 py-0">
		<a href="/index" class="navbar-brand d-flex d-lg-none me-4">
			<h2 class="text-primary mb-0">
				<i class="fa fa-user-edit"></i>
			</h2>
		</a> <a href="#" class="sidebar-toggler flex-shrink-0"> <i
			class="fa fa-bars"></i>
		</a>
		<div class="navbar-nav align-items-center ms-auto">
			<div class="nav-item dropdown">
				<a href="#" class="nav-link dropdown-toggle"
					data-bs-toggle="dropdown"> <i class="fa fa-bell me-lg-2"></i> <span
					class="d-none d-lg-inline-flex">Notifications <c:out value="${fn:length(todayPaymentList)}"/></span>
				</a>
				<c:if test="${not empty todayPaymentList}">
				<div
					class="dropdown-menu dropdown-menu-end bg-secondary border-0 rounded-0 rounded-bottom m-0">
					<c:forEach items="${todayPaymentList}" var="customer">
						<a href="/upcoming-payment" class="dropdown-item">
							<h6 class="fw-normal mb-0" style="color:green">Collect payment </h6> <small><c:out value="${customer.name}"/> (Mobile : <c:out value="${customer.mobile}"/>)</small>
						</a>
						<hr class="dropdown-divider">
					</c:forEach>

				</div>
				</c:if>
			</div>
			<div class="nav-item dropdown">
				<a href="#" class="nav-link dropdown-toggle"
					data-bs-toggle="dropdown"> <img class="rounded-circle me-lg-2"
					src="img/user.jpg" alt="" style="width: 40px; height: 40Spx;">
					<span class="d-none d-lg-inline-flex"> <c:if test="${ empty merchant}">
							<b style="color: green"><c:out value="${merchant.name}" /></b>
						</c:if>
				</span>
				</a>
				<div
					class="dropdown-menu dropdown-menu-end bg-secondary border-0 rounded-0 rounded-bottom m-0">
					<a href="/logout-process" class="dropdown-item" style="color: red">Log Out</a>
				</div>
			</div>
		</div>
	</nav>
</body>
</html>