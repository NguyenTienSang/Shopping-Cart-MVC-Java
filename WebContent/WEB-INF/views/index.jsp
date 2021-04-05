<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	<!DOCTYPE jsp>
	<jsp lang="en">
	<head>
	<base href="${pageContext.servletContext.contextPath}/">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Home | SHOP-EASY</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/font-awesome.min.css" rel="stylesheet">
		<link href="css/prettyPhoto.css" rel="stylesheet">
		<link href="css/price-range.css" rel="stylesheet">
		<link href="css/animate.css" rel="stylesheet">
		<link href="css/main.css" rel="stylesheet">
		<link href="css/responsive.css" rel="stylesheet">
		<link rel="shortcut icon" href="images/ico/favicon.ico">
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
		<link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
	</head><!--/head-->


<!-- Import header -->
	<body>
		<jsp:include page="header/header-middle.jsp"></jsp:include>
		<jsp:include page="header/header-bottom.jsp"></jsp:include>
		
		<section id="slider"><!--slider-->
			<div class="container">
				<div class="row">
					<div class="col-sm-12">
						<div id="slider-carousel" class="carousel slide" data-ride="carousel">
							<ol class="carousel-indicators">
								<li data-target="#slider-carousel" data-slide-to="0" class="active"></li>
								<li data-target="#slider-carousel" data-slide-to="1"></li>
								<li data-target="#slider-carousel" data-slide-to="2"></li>
							</ol>
							
							<div class="carousel-inner">
								<div class="item active">
									<div class="col-sm-6">
										<h1><span>SHOP-EASY</span></h1>
										<h2>DEAL TỐT <br> GIÁ TỐT</h2>
										<p></p>
										<button type="button" class="btn btn-default get">Get it now</button>
									</div>
									<div class="col-sm-6">
										<img src="images/home/girl4.jpg" class="girl img-responsive" alt="" />
									</div>
								</div>
								<div class="item">
									<div class="col-sm-6">
										<h1><span>SHOP-EASY</span></h1>
										<h2>DEAL KHỦNG <br> TRAO TAY </h2>
										<p></p>
										<button type="button" class="btn btn-default get">Get it now</button>
									</div>
									<div class="col-sm-6">
										<img src="images/home/man1.jpg" class="girl img-responsive" alt="" />
									</div>
								</div>
								
								<div class="item">
									<div class="col-sm-6">
										<h1><span>SHOP-EASY</span></h1>
										<h3>THỜI TRANG TUNG SALE</h3>
										<h2>NGÀY VUI TRỌN VẸN</h2>
										<p></p>
										<button type="button" class="btn btn-default get">Get it now</button>
									</div>
									<div class="col-sm-6">
										<img src="images/home/family1.jpg" class="girl img-responsive" alt="" />
									</div>
								</div>
								
							</div>
							
							<a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
								<i class="fa fa-angle-left"></i>
							</a>
							<a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
								<i class="fa fa-angle-right"></i>
							</a>
						</div>
						
					</div>
				</div>
			</div>
		</section><!--/slider-->
		
		<section>
			<div class="container">
				<div class="row">
					<div class="col-sm-3">
						<div class="left-sidebar">
							<h2>Danh Mục</h2>
							<div class="panel-group category-products" id="accordian"><!--category-productsr-->
								<c:forEach var="c" items="${listCategory}">
									<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
										<a href="client/products/${c.id}.htm">${c.name}</a>
										</h4>
									</div>
								</div>	
								</c:forEach>			
							</div><!--/category-products-->
						</div>
					</div>
					
					<div class="col-sm-9 padding-right">
						<div class="features_items"><!--features_items-->
							<h2 class="title text-center">Sản Phẩm</h2>
							<c:forEach var="p" items="${listPro}">
							<div class="col-sm-4">
									<div class="product-image-wrapper">
									<a href="login.htm">
										<div class="single-products">
											<div class="productinfo">
												<div class="product">
													<img src="images/home/${p.photo}" alt="" />
													<img class="new" src="images/home/new.png" alt="" />
												</div>
												<p class="productname text-center">${p.name}</p>
												<a href="#" class="price">
													${p.price}đ
												</a>
												<a href="#" class="safed">
													Đã bán ${p.sold}
												</a>
											</div>
											<div class="product-overlay"></div>
											<div class="overlay-content">
												<a href="user/cart/add/${p.id}.htm" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Thêm Vào Giỏ Hàng</a>
											</div>
										</div>
									<div class="choose">
									
									</div>
								</a>
								</div>
							</div>
							</c:forEach>
						</div><!--features_items-->
						
					</div>
				</div>
			</div>
		</section>
		
		
		<footer>
			<jsp:include page="footer/footerlevel1.jsp"></jsp:include>
		</footer>
	
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/jquery.scrollUp.min.js"></script>
		<script src="js/price-range.js"></script>
		<script src="js/jquery.prettyPhoto.js"></script>
		<script src="js/main.js"></script>
	</body>
	</jsp>