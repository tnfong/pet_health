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
				<li class="breadcrumb-item"><a href="javascript:;">Trang chủ</a></li>
				<li class="breadcrumb-item"><a href="javascript:;">Đặt lịch</a></li>
				<li class="breadcrumb-item active">Hóa đơn</li>
			</ol>

			<h1 class="page-header">
			  Cập nhật hóa đơn
			</h1>
			<div class="row">
				<div class="col-xl-6">
					<div class="panel panel-inverse">
						<div class="panel-heading">
					    	<h4 class="panel-title">Thông tin hóa đơn</h4>
						    <div class="panel-heading-btn">
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-default" data-toggle="panel-expand" ><i class="fa fa-expand"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-success" data-toggle="panel-reload" ><i class="fa fa-redo"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-warning" data-toggle="panel-collapse" ><i class="fa fa-minus"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-danger" data-toggle="panel-remove" ><i class="fa fa-times"></i></a>
						    </div>
						</div>
						<div class="panel-body">
							<form:form modelAttribute="billForm" method="POST">
								<form:hidden path="idBook" />
								<form:hidden path="idUser" />
								<div class="form-floating mt-1">
								  	<div class="form-control fs-15px">${billForm.userFullName}</div>
								  	<label class="d-flex align-items-center fs-13px">Tên khách hàng</label>
								</div>
								<div class="form-floating mt-1">
								  	<div class="form-control fs-15px">${billForm.userPhone}</div>
								  	<label class="d-flex align-items-center fs-13px">Số điện thoại</label>
								</div>
								<div class="form-floating mt-1">
								  	<div class="form-control fs-15px">${billForm.serviceName}</div>
								  	<label class="d-flex align-items-center fs-13px">Tên dịch vụ</label>
								</div>
								<div class="form-floating mt-1">
									<form:input path="price" cssClass="form-control fs-15px" type="number" />
								  	<label class="d-flex align-items-center fs-13px">Giá tiền</label>
								</div>
								<div class="form-floating mt-1">
									<form:input path="note" cssClass="form-control fs-15px" />
								  	<label class="d-flex align-items-center fs-13px">Ghi chú</label>
								</div>

								<button type="submit" class="btn btn-primary mt-1">Lưu</button>
								<a class="btn btn-danger mt-1" href="/book/list">Danh sách</a>
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
</body>
</html>
