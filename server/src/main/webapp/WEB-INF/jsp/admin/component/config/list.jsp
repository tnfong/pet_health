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

			<h1 class="page-header d-flex align-items-center">Cài đặt hệ thống</h1>
			<div class="col-xl-6">
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
						<c:if test="${configList.size() > 0}">
							<c:forEach items="${configList}" var="item" varStatus="loop">
							<form method="POST">
								<div class="input-group mb-1">
									<button class="btn btn-primary">${item.name}</button>
								  	<input type="hidden" name="id" value="${item.id}" />
								  	<input type="hidden" name="key" value="${item.key}" />
								  	<input type="hidden" name="name" value="${item.name}" />
								  	<input id="value" name="value" class="form-control fs-15px" value="${item.value}" />
									<button type="submit" class="btn btn-indigo">Lưu</button>
								</div>
							</form>
							</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
		</div>
    </div>

	<%@ include file="/WEB-INF/jsp/admin/common/js.jsp" %>
</body>
</html>
