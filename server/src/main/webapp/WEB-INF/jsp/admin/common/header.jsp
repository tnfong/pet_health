<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<c:if test="${not empty mess}">
<script>
    alert("${mess}")
</script>
</c:if>

<div id="header" class="app-header">
  <div class="navbar-header">
    <a href="${_ctx}/" class="navbar-brand"><span class="navbar-logo"></span> <b class="me-1">CMS</b></a>
    <button type="button" class="navbar-mobile-toggler" data-toggle="app-sidebar-mobile">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
  </div>

  <div class="navbar-nav">
    <div class="navbar-item navbar-user dropdown">
      <a href="#" class="navbar-link dropdown-toggle d-flex align-items-center" data-bs-toggle="dropdown">
        <!-- <img src="" alt="" /> -->
        <span>
          <span class="d-none d-md-inline">Administrator</span>
          <b class="caret"></b>
        </span>
      </a>
      <div class="dropdown-menu dropdown-menu-end me-1">
        <a href="${_ctx}/oauth/logout" class="dropdown-item"><i class="fa fa-sign-out-alt"></i> Đăng xuất</a>
      </div>
    </div>
  </div>
</div>