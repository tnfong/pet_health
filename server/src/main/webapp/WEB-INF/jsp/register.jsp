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

			<div class="register-container">
				<div class="register-header mb-25px h1">
					<div class="mb-1">Đăng ký tài khoản</div>
				</div>
				<div class="register-content">
					<form:form modelAttribute="registerForm" method="POST" cssClass="fs-13px">
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
							<label class="mb-2">Họ tên <span class="text-danger">*</span></label>
							<form:input path="fullName" cssClass="form-control fs-13px" placeholder="Họ tên" />
							<div class="invalid-feedback" for="fullName">Họ tên không hợp lệ</div>
						</div>
						<div class="mb-3">
							<label class="mb-2">Địa chỉ</label>
							<form:input path="address" cssClass="form-control fs-13px" placeholder="Địa chỉ" />
						</div>
						<div class="mb-3">
							<label class="mb-2">Số điện thoại <span class="text-danger">*</span></label>
							<form:input path="phone" cssClass="form-control fs-13px" maxlength="14" minlength="14" data-mask="(000) 000-0000"/>
							<div class="invalid-feedback" for="phone">Số điện thoại không hợp lệ</div>
						</div>
						<div class="mb-3">
							<label class="mb-2">Email</label>
							<form:input path="email" cssClass="form-control fs-13px" placeholder="Email" />
						</div>
						<div class="mb-4">
							<label class="mb-2">Giới tính</label>
							<form:select path="gender" cssClass="form-control fs-13px">
								<form:option cssClass="text-black" value="0">Nam</form:option>
								<form:option cssClass="text-black" value="1">Nữ</form:option>
							</form:select>
						</div>
						<!-- <div class="form-check mb-4">
							<input class="form-check-input" type="checkbox" value="" id="agreementCheckbox">
							<label class="form-check-label" for="agreementCheckbox">By clicking Sign Up, you agree to our <a href="javascript:;">Terms</a> and that you have read our <a href="javascript:;">Data Policy</a>, including our <a href="javascript:;">Cookie Use</a>.</label>
						</div> -->
						<div class="mb-4">
							<button type="button" class="btn btn-primary d-block w-100 btn-lg h-45px fs-13px btn-submit">Đăng kí</button>
						</div>
						<div class="mb-4 pb-5">Bạn đã có tài khoản? Vui lòng click <a href="${_ctx}/login">vào đây</a> để đăng nhập.</div>
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
    
	<script src="${_ctx}/static/plugins/jquery.masked/jquery.mask.js"></script>      
	<script>
		$(document).ready(function(){
			$("#phone").mask('(000) 000-0000')
		})

		$(".btn-submit").click(function(){
			var checkUsername = isNullInput("username")
			var checkPassword = isNullInput("password")
			var checkFullName = isNullInput("fullName")
			var checkPhone = isNullInput("phone")
			if(!checkUsername && !checkPassword && !checkFullName && !checkPhone){
				$("#registerForm").submit()
			}
		})
	</script>
</body>
</html>