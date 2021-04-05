

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${pageContext.servletContext.contextPath}/">
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Simple Responsive Admin</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <style>
		table{
			border-collapse: collapse;
			width: 50%;
		}
		th,td{
			line-height: 25px;
			border: 1px solid black;
			padding: 5px;
		}
		th{
			background-color: gray;
		}
		btn_index
		{
			
		}
</style>
</head>
<body>
     <div id="wrapper">
      <h2 style="text-align:center;">ACCOUNT</h2>
     <div style="text-align:center;">
	     <a href="${pageContext.request.contextPath}/trangchuAdmin.htm">
	     	<h4>Về Trang Chủ</h4>
	     </a>
	     <a href="${pageContext.request.contextPath}/admin/category/add.htm">
	     	<h4>Thêm Loại Sản Phẩm</h4>
	     </a>
	     <a href="${pageContext.request.contextPath}/admin/category/list.htm">
	     	<h4>Trở Về</h4>
	     </a>
	     				<div>
							<p style="text-align: center; color: red">
								<u>${message}</u>
							</p>
						</div>
     <div style="text-align:center;">
									<form:form action="${pageContext.request.contextPath}/admin/category/edit.htm" method="post" modelAttribute="cate">
										<br>
										<label>Mã loại</label>
										<form:input path="id" value="${cateId}"/>
										<br>
										<label>Tên:</label>
										<form:input path="name" />
										<br>
										<input type="submit" value="Save" />
									</form:form>
         </div><br>
         <br>
         <br>
    <div class="footer">
      
    
            <div class="row">
                <div class="col-lg-12" >
                    &copy;  2020 yourdomain.com | Design by: <a href="http://binarytheme.com" style="color:#fff;" target="_blank">www.binarytheme.com</a>
                </div>
            </div>
       </div>
          

     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
   
</body>
</html>
