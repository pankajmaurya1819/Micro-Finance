<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>Pragati Loan</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicon -->
<link href="img/favicon.ico" rel="icon">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Roboto:wght@500;700&display=swap"
	rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/owlcarousel/assets/owl.carousel.min.css"
	rel="stylesheet">
<link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css"
	rel="stylesheet" />

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">
</head>

<body>
	<div class="container-fluid position-relative d-flex p-0">
		<!-- Spinner Start -->
		<div id="spinner"
			class="show bg-dark position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
			<div class="spinner-border text-primary"
				style="width: 3rem; height: 3rem;" role="status">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<!-- Spinner End -->


		<!-- Sidebar Start -->
		<jsp:include page="sidebar.jsp" />
		<!-- Sidebar End -->


		<!-- Content Start -->
		<div class="content">
			<!-- Navbar Start -->
			<jsp:include page="navbar.jsp" />
			<!-- Navbar End -->


			<!-- Form Start -->

			<form action="/updateCustomerData" method="post"
				modelAttribute="customer">
				<input type="hidden" name="id" value="${customer.id}">
				<div class="container-fluid pt-4">
					<div class="row">
						<div class="col-sm-12 col-xl-8" style="margin: auto">
							<div class="bg-secondary rounded h-100 p-4">
								<c:if test="${not empty msg }">
									<h6 class="mb-4" style="color: red; text-align: center"> <c:out
											value="${msg}" />
									</h6>
								</c:if>
								<c:if test="${empty msg }">
									<h6 class="mb-4" style="text-align: center; color: red">Edit
										Loan Details</h6>
								</c:if>
								<div class="form-floating mb-3">
									<input type="text" name="name" value="${customer.name}"
										class="form-control" id="floatingInput"
										placeholder="customer_name"> <label
										for="floatingInput">Name</label>
								</div>

								<div class="form-floating mb-3">
									<input type="email" name="email" value="${customer.email}"
										class="form-control" id="floatingInput"
										placeholder="name@example.com"> <label
										for="floatingInput">Email address</label>
								</div>
								<div class="form-floating mb-3">
									<input type="tel" name="mobile" value="${customer.mobile}"
										class="form-control" id="floatingInput"
										placeholder="customer mobile" maxlength="10"> <label
										for="floatingInput"> +91 Mobile</label>
								</div>
								<div class="form-floating mb-3">
									<input type="tel" name="adhar_number"
										value="${customer.adhar_number}" class="form-control"
										id="floatingInput" placeholder="customer aadhar"
										maxlength="12"> <label for="floatingInput">Adhar</label>
								</div>
								<div class="form-floating mb-3">
									<input type="number" name="amount" value="${customer.amount}"
										class="form-control" id="floatingInput"
										placeholder="customer amount"> <label
										for="floatingInput"> Amount</label>
							
								</div>

								<div class="form-floating mb-3">
									<input type="number" name="percentage"
										value="${customer.percentage}" class="form-control"
										id="floatingInput" placeholder="customer percentage">
									<label for="floatingInput">Interest rate % </label>
								</div>

								<div class="form-floating mb-3">
									<input type="text" name="reference"
										value="${customer.reference}" class="form-control"
										id="floatingInput" placeholder="customer reference name">
									<label for="floatingInput">Reference</label>
								</div>

								<div class="form-floating mb-3">
									<input type="text" name="address" value="${customer.address}"
										class="form-control" id="floatingInput"
										placeholder="customer address" style="height: 150px;">
									<label for="floatingInput">Address</label>
								</div>
								<br />
								<div class="form-floating">
									<input type="submit" value="Submit"
										style="color: red; width: 200px; height: 50px">
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>


			<!-- Form End -->
		</div>
		<!-- Content End -->


		<!-- Back to Top -->
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
			class="bi bi-arrow-up"></i></a>
	</div>

	<!-- JavaScript Libraries -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib/chart/chart.min.js"></script>
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>
	<script src="lib/tempusdominus/js/moment.min.js"></script>
	<script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
	<script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

	<!-- Template Javascript -->
	<script src="js/main.js"></script>
</body>

</html>