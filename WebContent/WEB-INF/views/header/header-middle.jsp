<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>JAVA SERVER PAGE</title>
</head>
<body>
<base href="${pageContext.servletContext.contextPath}/">
		<div class="header-middle"><!--header-middle-->
				<div class="container">
					<div class="row">
						<div class="col-sm-4">
							<div class="logo pull-left">
								<a href="index.htm"><img src="images/home/logo.png" alt="" /></a>
							</div>
						</div>
						<div class="col-sm-8">
							<div class="shop-menu pull-right">
								<ul class="nav navbar-nav">
									<li><a href="${pageContext.request.contextPath}/user/orderList.htm"><i class="fa fa-first-order"></i>Đơn Hàng</a></li>
									<li><a href="user/checkoutCart.htm"><i class="fa fa-crosshairs"></i>Thanh Toán</a></li>
									<li><a href="user/cart.htm"><i class="fa fa-shopping-cart"></i>Giỏ Hàng</a></li>
									<li><a href="login.htm"><i class="fa fa-lock"></i>Đăng Nhập</a></li>
									<li><a href="signup.htm" class="active"><i class="fa fa-lock"></i>Đăng Ký</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div><!--/header-middle-->
</body>
</html>