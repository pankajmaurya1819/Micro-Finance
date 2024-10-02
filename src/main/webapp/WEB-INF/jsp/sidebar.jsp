<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
	<div class="sidebar pe-4 pb-3">
		<nav class="navbar bg-secondary navbar-dark">
			<a href="/index" class="navbar-brand mx-4 mb-3">
				<h3 class="text-primary">
					<i class="fa fa-user-edit me-2"></i>Microfinance
				</h3>
			</a>
			<div class="d-flex align-items-center ms-4 mb-4">
				<div class="position-relative">
					<img class="rounded-circle" src="img/user.jpg" alt=""
						style="width: 40px; height: 40px;">
					<div
						class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
				</div>
				<div class="ms-3">
					<h6 class="mb-0">Pragati Loan</h6>
					<span>Admin</span>
				</div>
			</div>
			<div class="navbar-nav w-100">
				<a href="/index" class="nav-item nav-link active"><i
					class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
				<a href="/new-loan"
					class="nav-item nav-link"><i class="fa fa-keyboard me-2"></i>New Loan</a>
				<a href="/view-loans" class="nav-item nav-link"><i
					class="fa fa-table me-2"></i>View Loans</a>
				<a href="/view-transaction" class="nav-item nav-link"><i
					class="fa fa-table me-2"></i>View Transaction</a>
				<a href="/db-backup" class="nav-item nav-link"><i
					class="fa fa-table me-2"></i>Backup</a>
					
				<div class="nav-item dropdown">
					<a href="#" class="nav-link dropdown-toggle"
						data-bs-toggle="dropdown"><i class="fa fa-laptop me-2"></i>Payments</a>
					<div class="dropdown-menu bg-transparent border-0">
						<a href="/upcoming-payment" class="dropdown-item">Upcoming Payment</a> <a
							href="/missed-payment" class="dropdown-item">Missed Payment</a> <a
							href="/success-payment" class="dropdown-item">Successful Payment</a>
					</div>
				</div>
			</div>
		</nav>
	</div>
</body>
</html>