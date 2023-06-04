<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="_ctx" value="${pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath }" scope="application"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Login System</title>
	<link href="${_ctx}/static/css/admin/vendor.min.css" rel="stylesheet" />
	<link href="${_ctx}/static/css/admin/transparent.min.css" rel="stylesheet" />
</head>
<body class='pace-top'>
	
	<!-- Loader -->
	<div id="loader" class="app-loader">
		<span class="spinner"></span>
	</div>
	<!-- * Loader -->

	<div id="app" class="app">
		<div class="login login-with-news-feed">
			<div class="news-feed">
				<div class="news-image" style="background-image: url(${_ctx}/static/dist/admin/login-bg-11.jpg)"></div>
			</div>

			<div class="login-container">
				<div class="login-header mb-30px">
					<div class="brand">
						<div class="d-flex align-items-center"><span class="logo"></span><b>Đăng nhập</b></div>
					</div>
					<div class="icon"><i class="fa fa-sign-in-alt"></i></div>
				</div>

				<div class="login-content">
					<form:form modelAttribute="loginForm" method="POST" cssClass="fs-13px">
						<div class="mb-3">
							<label class="mb-2">Tên đăng nhập <span class="text-danger">*</span></label>
							<form:input path="username" cssClass="form-control fs-13px" placeholder="Tên đăng nhập" />
							<div class="invalid-feedback" for="username">Tên đăng nhập không hợp lệ</div>
						</div>
						<div class="mb-3">
							<label class="mb-2">Mật khẩu <span class="text-danger">*</span></label>
							<form:input path="password" type="password" cssClass="form-control fs-13px" placeholder="Mật khẩu" />
							<div class="invalid-feedback" for="password">Mật khẩu không hợp lệ</div>
						</div>
						<div class="mb-3">
							<button type="submit" class="btn btn-primary d-block w-100 btn-lg fs-13px btn-submit">Đăng nhập</button>
						</div>
						<div class="mb-40px pb-40px text-white">Bạn chưa có tài khoản? Ấn <a href="${_ctx}/register" class="text-primary">vào đây</a> để đăng ký.</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<c:if test="${not empty mess}">
	<script>
	    alert("${mess}")
	</script>
	</c:if>

	<script src="${_ctx}/static/js/admin/vendor.min.js" ></script>
	<script src="${_ctx}/static/js/admin/app.min.js" ></script>
	<script src="${_ctx}/static/js/admin/common.min.js" ></script>
	<script>
	/*
		$(".btn-submit").click(function(){
			var checkUsername = isNullInput("username")
			var checkPassword = isNullInput("password")
			if(!checkUsername && !checkPassword){
				$("#loginForm").submit()
			}
		})
    */
	</script>
</body>
</html>