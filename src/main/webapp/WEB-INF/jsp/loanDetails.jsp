<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

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
<style>
	th, td {
	  padding: 10px;
	}
</style>
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

			<div class="container-fluid pt-4">

				<div class="row">

					<div class="col-md-12 col-xl-8" style="margin:auto;">
						<div class="bg-secondary rounded h-100 p-4">
								<h6 style="text-align:center;color:red">Customer Loan Details</h6>
							<form action="/payment" method="post" modelAttribute="customer">
							<input type="hidden" name="id" value="${customer.id}">
							<table>
								<tr>
									<td>Name</td>
									<td>: <c:out value="${customer.name }" /></td>
								</tr>
								<tr>
									<td>Email</td>
									<td>: <c:out value="${customer.email}" /></td>
								</tr>
								<tr>
									<td>Aadhar Number</td>
									<td>: <c:out value="${customer.adhar_number}" /></td>
								</tr>
								<tr>
									<td>Account Number</td>
									<td>: <c:out value="${customer.account_number }" /></td>
								</tr>
								<tr>
									<td>Principle Amount</td>
									<td>: <c:out value="${customer.amount}" /></td>
								</tr>
								<tr>
									<td>Loan Initiation Date</td>
									<td>: <c:out value="${customer.insertDate}" /></td>
								</tr>
								<tr>
									<td>Interest Rate</td>
									<td>: <c:out value="${customer.percentage}" /></td>
								</tr>
								<tr>
									<td>Interest Amount/Month</td>
									<td>: <c:out value="${customer.interestAmount}" /></td>
								</tr>
								<tr>
									<td>Payment Due Date</td>
									<td>: <c:out value="${customer.dueDate}" /></td>
								</tr>
								<tr>
									<td>Customer Address</td>
									<td>: <c:out value="${customer.address}" /></td>
								</tr>
								<tr>
									<td><button style="color:red"> Go For Payment </button></td>
								</tr>
							</table>
							</form>

						</div>
					</div>

				</div>

			</div>
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