<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="sidebar" class="app-sidebar">
  <div class="app-sidebar-content" data-scrollbar="true" data-height="100%">
    <div class="menu">
      <div class="menu-profile">
        <a href="javascript:;" class="menu-profile-link" data-toggle="app-sidebar-profile" data-target="#appSidebarProfileMenu">
          <div class="menu-profile-cover with-shadow"></div>
          <div class="menu-profile-image">
            <!-- <img src="" alt="" /> -->
          </div>
          <div class="menu-profile-info">
            <div class="d-flex align-items-center">
              <div class="flex-grow-1">Admin</div>
              <div class="menu-caret ms-auto"></div>
            </div>
            <small>Administrator</small>
          </div>
        </a>
      </div>
      <div id="appSidebarProfileMenu" class="collapse">
        <div class="menu-item pt-5px">
          <a href="javascript:;" class="menu-link">
            <div class="menu-icon"><i class="fa fa-sign-out-alt"></i></div>
            <div class="menu-text">Đăng xuất</div>
          </a>
        </div>
        <div class="menu-divider m-0"></div>
      </div>
      <div class="menu-header">Main menu</div>

      <!-- POS -->
      <div class="menu-item">
        <a href="${_ctx}/" class="menu-link">
          <div class="menu-icon">
            <i class="fa fa-home"></i>
          </div>
          <div class="menu-text">
            Trang chủ
          </div>
        </a>
      </div>
      <!-- * POS -->
      
      <!-- User Management -->
      <div class="menu-item has-sub">
        <a href="javascript:;" class="menu-link">
          <div class="menu-icon"><i class="fa fa-user-friends"></i></div>
          <div class="menu-text">Quản lý người dùng</div>
          <div class="menu-caret"></div>
        </a>
        <div class="menu-submenu">
          <div class="menu-item"><a href="/user" class="menu-link"><div class="menu-text">Danh sách</div></a></div>
        </div>
      </div>
      <!-- * User Management -->

      <!-- Service Management -->
      <div class="menu-item has-sub">
        <a href="javascript:;" class="menu-link">
          <div class="menu-icon"><i class="fa fa-cogs"></i></div>
          <div class="menu-text">Quản lý dịch vụ</div>
          <div class="menu-caret"></div>
        </a>
        <div class="menu-submenu">
          <div class="menu-item"><a href="/service" class="menu-link"><div class="menu-text">Danh sách</div></a></div>
        </div>
      </div>
      <!-- * Service Management -->

      <!-- Booking Management -->
      <div class="menu-item has-sub">
        <a href="javascript:;" class="menu-link">
          <div class="menu-icon"><i class="fa fa-calendar-alt"></i></div>
          <div class="menu-text">Quản lý đặt lịch</div>
          <div class="menu-caret"></div>
        </a>
        <div class="menu-submenu">
          <div class="menu-item"><a href="/book" class="menu-link"><div class="menu-text">Danh sách</div></a></div>
        </div>
      </div>
      <!-- * Booking Management -->

      <!-- Message Management -->
      <div class="menu-item has-sub">
        <a href="/message" class="menu-link">
          <div class="menu-icon"><i class="fa fa-comments"></i></div>
          <div class="menu-text">Tin nhắn</div>
        </a>
      </div>
      <!-- * Message Management -->

      <!-- Config Management -->
      <div class="menu-item has-sub">
        <a href="/config" class="menu-link">
          <div class="menu-icon"><i class="fa fa-cogs"></i></div>
          <div class="menu-text">Cấu hình</div>
        </a>
      </div>
      <!-- * Config Management -->

      <div class="menu-item d-flex">
        <a href="javascript:;" class="app-sidebar-minify-btn ms-auto d-flex align-items-center text-decoration-none" data-toggle="app-sidebar-minify" ><i class="fa fa-angle-double-left"></i></a>
      </div>
    </div>
  </div>
</div>
<div class="app-sidebar-bg"></div>
<div class="app-sidebar-mobile-backdrop">
  <a href="#" data-dismiss="app-sidebar-mobile" class="stretched-link"></a>
</div>