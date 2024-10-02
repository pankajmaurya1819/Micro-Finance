<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<script type="text/javascript">
	function myPopup(id) {
		var retVal = confirm("Do you want to delete ?");
        if (retVal == true) {
          window.location = "/deleteCustomer?id="+id;
        } else {
            return false;
        }
	}
</script>
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


			<!-- Table Start -->
			<div class="container-fluid pt-4 px-4">
				<div class="row g-4S">

					<div class="col-sm-12 col-xl-6"></div>


					<div class="bg-secondary rounded h-100 p-4">
						
				        <c:if test="${empty msg}">
				           <h6 class="mb-4" style="color:red">Loan Details </h6>
				        </c:if>
				        <c:if test="${not empty msg}">
				           <h6 class="mb-4" style="color:red"><c:out value="${msg}"/> </h6>
				        </c:if>
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>

										<th scope="col">Name</th>
										<th scope="col">Account No</th>
										<th scope="col">Loan Date</th>
										<th scope="col">Amount</th>
										<th scope="col">Rate</th>
										<th scope="col">Due Date</th>
										<th scope="col">Action</th>
										<th scope="col">Action</th>
										<th scope="col">Action</th>
									</tr>
								</thead>
								<tbody>


									<c:forEach items="${customerList}" var="customer">
										<tr>

											<td><c:out value="${customer.name}" /></td>
											<td><c:out value="${customer.account_number}" /></td>
											<td><c:out value="${customer.insertDate}" /></td>
											<td><c:out value="${customer.amount}" /></td>
											<td><c:out value="${customer.percentage }" /></td>
											<td><c:out value="${customer.dueDate }" /></td>

											<td><a href="/getCustomer?id=<c:out value="${customer.id}"/>">Edit</a></td>
											<td><button onclick="myPopup(<c:out value="${customer.id}"/>)">Delete</button>
											<td><a href="/show-loan-details?id=<c:out value="${customer.id}"/>">Go </a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
			<!-- Table End -->
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