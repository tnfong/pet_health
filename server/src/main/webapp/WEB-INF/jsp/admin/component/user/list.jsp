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
				<li class="breadcrumb-item"><a href="javascript:;">Người dùng</a></li>
				<li class="breadcrumb-item active">Danh sách</li>
			</ol>

			<h1 class="page-header d-flex align-items-center">
			  Quản lý người dùng <small class="ms-2"><a href="/user/add" class="btn btn-success btn-sm"><i class="fa fa-plus-circle me-2"></i> Thêm mới</a></small>
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
									<span class="input-group-text">Tài khoản</span>
									<form:input path="username" cssClass="form-control"/>
								</div>
							</div>
							<div class="col-xxl-3 col-xl-4 col-md-6">
								<div class="input-group mb-3">
									<span class="input-group-text">Số điện thoại</span>
									<form:input path="phone" cssClass="form-control"/>
								</div>
							</div>
							<div class="col-xxl-3 col-xl-4 col-md-6">
								<div class="input-group mb-3">
									<span class="input-group-text">Thư điện tử</span>
									<form:input path="email" cssClass="form-control"/>
								</div>
							</div>
							<div class="col-xxl-3 col-xl-4 col-md-6">
								<div class="input-group mb-3">
									<span class="input-group-text">Quyền hạn</span>
									<form:select path="idRole" cssClass="form-control">
										<form:option value="0">Tất cả</form:option>
										<c:forEach items="${roleList}" var="item" varStatus="loop">
											<form:option value="${item.id}">${item.name}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="col-5">
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
						<thead>
							<tr>
								<th>#</th>
								<th>Tài khoản</th>
								<th>Tên</th>
								<th>SĐT</th>
								<th>Mail</th>
								<th>Giới tính</th>
								<th>Quyền hạn</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${data.getContent().size() > 0}">
								<c:forEach items="${data.getContent()}" var="item" varStatus="loop">
                                    <tr class="user" onload="userDecrypt(this)" data-full-name="${item.fullName}" data-phone="${item.phone}" data-email="${item.email}">
                                        <td>${loop.index + 1}</td>
                                        <td>${item.username}</td>
                                        <td field="fullName"></td>
                                        <td field="phone"></td>
                                        <td field="email"></td>
                                        <td>${item.gender}</td>
                                        <td>${item.roleName}</td>
                                        <td>
                                            <a href="/user/update/${item.id}"><i class="fa fa-cog"></i></a>
                                            <a href="/user/delete/${item.id}"><i class="fa fa-trash-alt text-danger"></i></a>
                                        </td>
                                    </tr>
								</c:forEach>
							</c:if>
							<c:if test="${data.getContent().size() == 0}">
								<tr>
									<td class="text-center" colspan="9"><i class="fa fa-inbox"></i> Không có dữ liệu</td>
								</tr>
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
