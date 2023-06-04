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

		<div id="content" class="app-content">
			<ol class="breadcrumb float-xl-end">
				<li class="breadcrumb-item"><a href="javascript:;">Trang chủ</a></li>
				<li class="breadcrumb-item"><a href="javascript:;">Đặt lịch</a></li>
				<li class="breadcrumb-item active">Danh sách</li>
			</ol>

			<h1 class="page-header d-flex align-items-center">
			  Quản lý đặt lịch
			</h1>

			<!-- Panel search -->
			<div class="panel panel-inverse">
				<div class="panel-heading">
			    	<h4 class="panel-title">Bộ lọc</h4>
				    <div class="panel-heading-btn">
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-default" data-toggle="panel-expand" ><i class="fa fa-expand"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-warning" data-toggle="panel-collapse" ><i class="fa fa-minus"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-danger" data-toggle="panel-remove" ><i class="fa fa-times"></i></a>
				    </div>
				</div>
				<div class="panel-body">
					<form:form modelAttribute="search" method="GET">
						<div class="row">
							<div class="col-xxl-3 col-xl-4 col-md-6">
								<div class="input-group mb-3">
									<span class="input-group-text">Trạng thái</span>
									<form:select path="idStatus" cssClass="form-control">
										<form:option value="0">Tất cả</form:option>
										<c:forEach items="${statusList}" var="item" varStatus="loop">
											<form:option value="${item.id}">${item.name}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-xxl-3 col-xl-4 col-md-6">
								<button type="submit" class="btn btn-primary"><i class="fa fa-search me-2"></i>Tìm kiếm</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<!-- * Panel search -->

			<!-- Panel list -->
			<div class="panel panel-inverse">
				<div class="panel-heading">
			    	<h4 class="panel-title">Danh sách</h4>
				    <div class="panel-heading-btn">
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-default" data-toggle="panel-expand" ><i class="fa fa-expand"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-success" data-toggle="panel-reload" ><i class="fa fa-redo"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-warning" data-toggle="panel-collapse" ><i class="fa fa-minus"></i></a>
				    	<a href="javascript:;" class="btn btn-xs btn-icon btn-danger" data-toggle="panel-remove" ><i class="fa fa-times"></i></a>
				    </div>
				</div>
				<div class="panel-body">
					<table class="table table-bordered table-hover">
						<thead class="text-center">
							<tr>
								<th rowspan="2">#</th>
								<th colspan="2">Khách hàng</th>
								<th colspan="2">Bác sĩ</th>
								<th colspan="6">Thông tin chung</th>
								<th rowspan="2" style="width: 80px;"></th>
							</tr>
							<tr>
								<th>Tên</th>
								<th>SĐT</th>
								<th>Tên</th>
								<th>SĐT</th>
								<th>Dịch vụ</th>
								<th>Thời gian hẹn</th>
								<th>Trạng thái</th>
								<th>Bệnh án</th>
								<th>Hóa đơn</th>
								<th>Thời gian tạo</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${data.getContent().size() > 0}">
								<c:forEach items="${data.getContent()}" var="item" varStatus="loop">
                                    <tr class="book" onload="bookDecrypt(this)" data-user-full-name="${item.userFullName}" data-user-phone="${item.userPhone}" data-doctor-full-name="${item.doctorFullName}" data-doctor-phone="${item.doctorPhone}">
                                        <td>${loop.index + 1}</td>
                                        <td field="userFullName"></td>
                                        <td field="userPhone"></td>
                                        <td field="doctorFullName"></td>
                                        <td field="doctorPhone"></td>
                                        <td>${item.serviceName}</td>
                                        <td>${item.time}</td>
                                        <td>${item.statusName}</td>
                                        <td class="text-center">
                                        	<c:if test="${item.idService == 4}">
	                                        	<c:if test="${item.idGuest != null}">
	                                        		<a href="/book/${item.id}/guest" title="Thông tin bệnh án"><i class="fas fa-info-circle"></i></a>
	                                        	</c:if>
	                                        	<c:if test="${item.idGuest == null}">
	                                        		<a href="javascript:;" title="Chưa có dữ liệu bệnh án"><i class="fas fa-info-circle text-warning"></i></a>
	                                        	</c:if>
                                        	</c:if>
                                        </td>
                                        <td class="text-center">
                                        	
	                                        	<c:if test="${item.idBill != null}">
		                                        	<a href="/book/${item.id}/bill" title="Chi tiết hóa đơn"><i class="fas fa-money-check-alt"></i></a>
		                                        </c:if>
	                                        	<c:if test="${item.idBill == null}">
	                                        		<c:if test="${item.idGuest != null || item.idService != 4}">
		                                        		<a href="/book/${item.id}/bill" title="Chưa có hóa đơn"><i class="fas fa-money-check-alt text-warning"></i></a>
	                                        		</c:if>
		                                        </c:if>
                                        </td>
                                        <td>${item.createdDate}</td>
                                        <td>
                                        	<c:if test="${item.idStatus eq 3 || (item.idStatus eq 4 && item.idGuest != null)}">
                                        		<button class="btn btn-success btn-xs" title="Xác nhận"><i class="far fa-check-circle text-light"></i></button>
                                        		<button class="btn btn-danger btn-xs" title="Hủy"><i class="far fa-time-circle text-light"></i></button>
                                        	</c:if>
                                        </td>
                                    </tr>
								</c:forEach>
							</c:if>
							<c:if test="${data.getContent().size() == 0}">
								<tr><td class="text-center" colspan="100%"><i class="fa fa-inbox"></i> Không có dữ liệu</td></tr>
							</c:if>
						</tbody>
					</table>
					<div class="text-end">
						<div class="btn-group paging" onload="generalPage('.paging');"
						 data-page="${search.getPage()}"
						 data-count="${data.getTotalElements()}"
						 data-limit="${search.getRow()}"></div>
					</div>
				</div>
			</div>
			<!-- * Panel list -->
		</div>
    </div>

	<%@ include file="/WEB-INF/jsp/admin/common/js.jsp" %>
</body>
</html>
