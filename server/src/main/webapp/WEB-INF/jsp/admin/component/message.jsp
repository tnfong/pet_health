<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jsp/admin/common/head.jsp" %>
<link href="${_ctx}/static/css/custom/style.css" rel="stylesheet">
<%@ include file="/WEB-INF/jsp/admin/common/css.jsp" %>

<body onload="disconnect()">
	<%@ include file="/WEB-INF/jsp/admin/common/loader.jsp" %>

    <div id="app" class="app app-header-fixed app-sidebar-fixed">
    	<%@ include file="/WEB-INF/jsp/admin/common/header.jsp" %>
    	<%@ include file="/WEB-INF/jsp/admin/common/sidebar.jsp" %>

		<div id="content" class="app-content">
			<div class="row rounded-lg overflow-hidden shadow">
                <!-- Users box-->
                <div class="col-3 px-0">
                  <div class="bg-white">
                    <div class="messages-box">
                      <div class="list-group rounded-0">
                        <c:forEach items="${groupData}" var="item">
                            <c:choose>
                                <c:when test="${idGroup != null && idGroup == item.id}">
                                    <a id="group-${item.id}" class="list-group-item list-group-item-action active rounded-0 bg-info">
                                </c:when>
                                <c:otherwise>
                                    <a id="group-${item.id}" class="list-group-item list-group-item-action rounded-0" href="${_ctx}/message?id=${item.id}&idUser=${item.idUser}" >
                                </c:otherwise>
                            </c:choose>
                            <div class="media"><img src="https://bootstrapious.com/i/snippets/sn-chat/avatar.svg" alt="user" width="50" class="rounded-circle">
                                <div class="media-body ml-4">
                                    <div class="d-flex align-items-center justify-content-between mb-1">
                                        <h6 class="mb-0">
                                            ${item.name}
                                            <small class="badge badge-pill rounded-circle bg-danger text-white txt-total-un-read" data-total="${item.totalUnRead}"><c:if test="${item.totalUnRead > 0}">${item.totalUnRead}</c:if></small>
                                        </h6>
                                        <small class="small font-weight-bold text-muted lasted-time">
                                            ${item.createdDate}
                                        </small>
                                    </div>
                                    <c:if test="${item.content != null}">
                                    <p class="lasted-message" class="font-italic mb-0 text-small text-muted">
                                        <small>
                                        <c:if test="${item.idUser == _userInfo.id}">
                                            Bạn:
                                        </c:if>
                                        ${item.content}
                                        </small>
                                    </p>
                                    </c:if>
                                </div>
                            </div>
                        </a>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Chat Box-->
                <div class="col-9 px-0">
                    <div class="px-4 py-5 chat-box bg-white" id="chat-box" style="height: calc(100vh - 140px);overflow-y: scroll;">
                        <c:forEach items="${messageData}" var="item">
                        <!-- Sender Message-->
                        <c:choose>
                            <c:when test="${_userInfo.id == item.idUser}">
                                <div class="media w-50 ml-auto mb-1">
                                  <div class="media-body">
                                    <div class="bg-info rounded py-2 px-3 mb-1">
                                      <p class="text-small mb-0 text-white">${item.content}</p>
                                    </div>
                                  </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="media w-50 mb-1">
                                    <img src="https://bootstrapious.com/i/snippets/sn-chat/avatar.svg" alt="user" width="50" class="rounded-circle">
                                    <div class="media-body ml-3">
                                        <div class="bg-light rounded py-2 px-3 mb-1">
                                            <p class="text-small mb-0 text-muted">${item.content}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        </c:forEach>
                        <!-- Reciever Message-->
                    </div>

                    <!-- Typing area -->
                    <div class="input-group">
                        <input type="text" placeholder="Type a message" aria-describedby="button-addon2" class="form-control rounded-0 border-0 py-4 bg-light" id="ip-message">
                        <div class="input-group-append">
                            <button id="btn-send" class="btn btn-info"> <i class="fa fa-paper-plane"></i></button>
                        </div>
                    </div>
                </div>
            </div>
		</div>
    </div>

	<%@ include file="/WEB-INF/jsp/admin/common/js.jsp" %>
    <script src="${_ctx}/static/js/admin/bootstrap.bundle.min.js"></script>
    <script src="${_ctx}/static/js/admin/sockjs.min.js"></script>
    <script src="${_ctx}/static/js/admin/stomp.min.js"></script>
	<script>
        var stompClient = null;
        $(document).ready(function(){
            connect()
            $('#chat-box').scrollTop($('#chat-box')[0].scrollHeight);
        })

        function connect() {
            var socket = new SockJS('/webws');
            stompClient = Stomp.over(socket);
            stompClient.connect({id: '${_userInfo.id}'}, function(frame) {
                stompClient.subscribe('/user/${_userInfo.id}/queue/messages', function(messageOutput) {
                    console.log("BODY: "+messageOutput.body)
                    showMessage(JSON.parse(messageOutput.body))
                });
            });

        }

        function disconnect() {
            if(stompClient != null) {
                stompClient.disconnect();
            }
        }

        function showMessage(data){
            var nameUserMess = "Bạn: "
            var itemHtml = "";
            if(data.idUser == ${_userInfo.id}){
                itemHtml = `
                <div class="media w-50 ml-auto mb-1">
                  <div class="media-body">
                    <div class="bg-info rounded py-2 px-3 mb-1">
                        <p class="text-small mb-0 text-white">`+data.content+`</p>
                    </div>
                  </div>
                </div>`
            }else{
                nameUserMess = ""
                itemHtml = `
                <div class="media w-50 mb-1">
                    <img src="https://bootstrapious.com/i/snippets/sn-chat/avatar.svg" alt="user" width="50" class="rounded-circle">
                    <div class="media-body ml-3">
                        <div class="bg-light rounded py-2 px-3 mb-1">
                            <p class="text-small mb-0 text-muted">`+data.content+`</p>
                        </div>
                    </div>
                </div>`
            }
            $(`#group-`+data.idGroup).find(".lasted-message").html(nameUserMess+data.content)
            $(`#group-`+data.idGroup).find(".lasted-time").html(new Date(data.createdDate).format("yyyy/MM/dd HH:mm:ss"))
            if(data.idGroup == ${idGroup}){
                $("#chat-box").append(itemHtml)
                $('#chat-box').scrollTop($('#chat-box')[0].scrollHeight);
            }else{
                //txt-total-un-read
                var totalUnread = $(`#group-`+data.idGroup).find(".txt-total-un-read").data("total") * 1
                totalUnread++
                $(`#group-`+data.idGroup).find(".txt-total-un-read").data("total", totalUnread)
                $(`#group-`+data.idGroup).find(".txt-total-un-read").html(totalUnread)
            }
        }

        function sendMessage() {
            var mess = $("#ip-message").val()
            if(mess.trim().length > 0){
                stompClient.send("/app/chat", {}, JSON.stringify({idUser: ${_userInfo.id}, idGroup: ${idGroup}, content: mess}));
                $("#ip-message").val("")
            }
        }

        $("#btn-send").click(function(){
            sendMessage()
        })

        $("#ip-message").keypress(function(e){
            var key = e.which;
            if(key == 13) {
                $("#btn-send").click()
            }
        })
    </script>
</body>
</html>
