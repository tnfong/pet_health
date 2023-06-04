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
				<li class="breadcrumb-item active">Bệnh án</li>
			</ol>

			<h1 class="page-header">
			  Thông tin bệnh án
			</h1>
			<div class="row">
				<div class="col-xl-6">
					<div class="panel panel-inverse">
						<div class="panel-heading">
					    	<h4 class="panel-title">Thông tin bệnh án</h4>
						    <div class="panel-heading-btn">
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-default" data-toggle="panel-expand" ><i class="fa fa-expand"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-success" data-toggle="panel-reload" ><i class="fa fa-redo"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-warning" data-toggle="panel-collapse" ><i class="fa fa-minus"></i></a>
						    	<a href="javascript:;" class="btn btn-xs btn-icon btn-danger" data-toggle="panel-remove" ><i class="fa fa-times"></i></a>
						    </div>
						</div>
						<div class="panel-body">
                            <div class="form-floating mt-1">
                                <div class="form-control fs-15px" id="doctorFullName"></div>
                                <label class="d-flex align-items-center fs-13px">Tên bác sĩ</label>
                            </div>
                            <div class="form-floating mt-1">
                                <div class="form-control fs-15px" id="doctorPhone"></div>
                                <label class="d-flex align-items-center fs-13px">SĐT bác sĩ</label>
                            </div>
                            <div class="form-floating mt-1">
                                <div class="form-control fs-15px">${guestInfo.symptoms}</div>
                                <label class="d-flex align-items-center fs-13px">Triệu chứng</label>
                            </div>
                            <div class="form-floating mt-1">
                                <div class="form-control fs-15px">${guestInfo.diagnostic}</div>
                                <label class="d-flex align-items-center fs-13px">Chuẩn đoán</label>
                            </div>
                            <div class="form-floating mt-1">
                                <div class="form-control fs-15px">${guestInfo.instructions}</div>
                                <label class="d-flex align-items-center fs-13px">Đơn thuốc</label>
                            </div>
                            <a class="btn btn-danger mt-1" href="/book/list">Danh sách</a>
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
				fullName: "${doctorInfo.fullName}",
				phone: "${doctorInfo.phone}"
			}
	        const res = await user.decrypt(1, param)
	        const data = res.data

	        $("#doctorFullName").html(data.fullName)
	        $("#doctorPhone").html(data.phone)
		})
	</script>
</body>
</html>
