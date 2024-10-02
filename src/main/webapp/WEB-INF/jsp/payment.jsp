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
	function submitForm() {
		var amount = document.getElementById("floatingInput").value;
		if (amount == null || amount == "") {
			alert("Please enter amount to proceed further!!!");
			return false
		}
		var retVal = confirm("You can't update amount after successfull transaction. do you want to proceed ?");
		if (retVal == true) {
			document.getElementById("myForm").submit();
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
			<!-- Form Start -->
			<form id="myForm" action="/payment-process" method="post"
				modelAttribute="transaction" onsubmit="return submitForm()">
				<input type="hidden" name="loanId" value="${customer.id}"> <input
					type="hidden" name="accountNumber"
					value="${customer.account_number}"> <input type="hidden"
					name="loanAmount" value="${customer.amount}"> <input
					type="hidden" name="interestRate" value="${customer.percentage}">
				<input type="hidden" name="interestAmount"
					value="${customer.interestAmount}">
				<div class="container-fluid pt-4">
					<div class="row">
						<c:if test="${not empty msg }">
							<b style="color: red; text-align: center"> <c:out
									value="${msg}" />
							</b>
						</c:if>
						<div class="col-sm-12 col-xl-8" style="margin: auto;">
							<div class="bg-secondary rounded h-100 p-4">
								<div class="form-floating mb-3">
									<input type="number" name="credit" class="form-control"
										id="floatingInput" placeholder="Interest Amount"
										value="${customer.interestAmount}" minlength="1" maxlength="7" />
									<label for="floatingInput"> Minimum Amount</label>
								</div>

								<br />
								<div class="form-floating">
									<input type="submit" value="Pay"
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