<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Lỗi hệ thống</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
	<link href="${_ctx}/static/css/admin/vendor.min.css" rel="stylesheet" />
	<link href="${_ctx}/static/css/admin/app.min.css" rel="stylesheet" />
</head>
<body class="pace-done pace-top">
    <div id="app" class="app">
        <div class="error">
            <div class="error-code">401</div>
            <div class="error-content">
                <div class="error-message">Lỗi hệ thống</div>
                <div class="error-desc mb-4">
                    Bạn không có quyền truy cập vào hệ thống<br>
                    Vui lòng đăng nhập để tiếp tục sử dụng dịch vụ
                </div>
                <div><a href="${_ctx}/" class="btn btn-success px-3">Trang chủ</a></div>
            </div>
        </div>
    </div>
	<script src="${_ctx}/static/js/admin/vendor.min.js" ></script>
	<script src="${_ctx}/static/js/admin/app.min.js" ></script>
</body>
</html>