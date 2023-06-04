<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/admin/common/tags.jsp" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/admin/common/head.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/common/css.jsp" %>
<body>
	<%@ include file="/WEB-INF/jsp/admin/common/loader.jsp" %>

    <div id="app" class="app app-header-fixed app-sidebar-fixed">
    	<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
    	<%@ include file="/WEB-INF/jsp/admin/common/sidebar.jsp" %>

    	<!-- Content -->
		<div id="content" class="app-content">
			<ol class="breadcrumb float-xl-end">
				<li class="breadcrumb-item"><a href="javascript:;">Home</a></li>
				<li class="breadcrumb-item"><a href="javascript:;">User</a></li>
				<li class="breadcrumb-item active">Update</li>
			</ol>

			<h1 class="page-header">
			  User <small>update</small>
			</h1>
			<div class="row">
				<div class="col-xl-6">
					<div class="panel panel-inverse">
						<div class="panel-heading">
					    	<h4 class="panel-title">User Information</h4>
						    <div class="panel-heading-btn">
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-default" data-toggle="panel-expand" ><i class="fa fa-expand"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-success" data-toggle="panel-reload" ><i class="fa fa-redo"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-warning" data-toggle="panel-collapse" ><i class="fa fa-minus"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-danger" data-toggle="panel-remove" ><i class="fa fa-times"></i></a>
						    </div>
						</div>
						<div class="panel-body">
							<form:form modelAttribute="userForm" method="POST">

								<div class="form-floating mt-1">
									<div class="form-control fs-15px">${userForm.username}</div>
								  	<form:hidden path="username"/>
								  	<label for="username" class="d-flex align-items-center fs-13px">Tài khoản</label>
								</div>

								<div class="form-floating mt-1">
								  	<form:password path="password" cssClass="form-control fs-15px"/>
								  	<label for="password" class="d-flex align-items-center fs-13px">Mật khẩu</label>
								</div>

								<div class="form-floating mt-1">
								    <form:hidden path="fullName"/>
								  	<input type="text" id="fullNameDecode" class="form-control fs-15px">
								  	<label for="fullNameDecode" class="d-flex align-items-center fs-13px">Tên</label>
								</div>

								<div class="form-floating mt-1">
								    <form:hidden path="phone"/>
								  	<input type="number" id="phoneDecode" class="form-control fs-15px"/>
								  	<label for="phoneDecode" class="d-flex align-items-center fs-13px">SĐT</label>
								</div>

								<div class="form-floating mt-1">
								    <form:hidden path="email"/>
								  	<input type="text" id="emailDecode" class="form-control fs-15px">
								  	<label for="emailDecode" class="d-flex align-items-center fs-13px">Thư điện tử</label>
								</div>

								<div class="form-floating mt-1">
									<form:select path="gender" cssClass="form-control fs-15px">
										<form:option value="1">Nam</form:option>
										<form:option value="0">Nữ</form:option>
									</form:select>
								  	<label for="gender" class="d-flex align-items-center fs-13px">Giới tính</label>
								</div>

								<div class="form-floating mt-1">
									<form:select path="idRole" cssClass="form-control fs-15px">
										<c:forEach items="${roleList}" var="item" varStatus="loop">
											<form:option value="${item.id}">${item.name}</form:option>
										</c:forEach>
									</form:select>
								  	<label for="idRole" class="d-flex align-items-center fs-13px">Quyền hạn</label>
								</div>

								<button type="submit" class="btn btn-primary mt-1">Cập nhật</button>
								<a class="btn btn-danger mt-1" href="/user/list">Danh sách</a>
							</form:form>
						</div>
					</div>
				</div>
			</div>	
		</div>
		<!-- * Content -->

    </div>
    <!-- * App -->
	<%@ include file="/WEB-INF/jsp/admin/common/js.jsp" %>
	<script>
	$(document).ready(async function(){
        const param = {
            fullName: "${userForm.fullName}",
            phone: "${userForm.phone}",
            email: "${userForm.email}"
        }
        const res = await user.decrypt(1, param)
        const data = res.data

        $("#fullNameDecode").val(data.fullName)
        $("#phoneDecode").val(data.phone)
        $("#emailDecode").val(data.email)
    })
    $("#userForm").submit(async function(e){
        e.preventDefault();
        const param = {
            fullName: $("#fullNameDecode").val(),
            phone: $("#phoneDecode").val(),
            email: $("#emailDecode").val()
        }
        const res = await user.encrypt(1, param)
        const data = res.data
        $("#fullName").val(data.fullName)
        $("#phone").val(data.phone)
        $("#email").val(data.email)
        $(this).unbind('submit').submit();
    })
	</script>

</body>
</html>
