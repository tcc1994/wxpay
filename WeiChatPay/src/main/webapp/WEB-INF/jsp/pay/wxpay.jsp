<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/static/jquery-1.11.3.js"></script>
<script type="text/javascript" src="/static/qrcode.js"></script> 
</head>
<body>
	<div class="payType-btnfather">
		<input type="button" onclick="to_pay()" value="立即支付"/>
		<!-- <div class="payType-btn" id="wechatPop" onclick="to_pay()">立即支付</div> -->
	</div>
	<div id="payTypePop" class="payType-pop-up">
		<div class="payType-bg"></div>
		<div class="payType-contain">
			<div class="payType-title">
				微信扫码支付 <span id="offPop">&#10005</span>
			</div>
			<div class="payType-qrcode">
				<p class="paymoney">
					支付金额：<em>&yen;${fee }</em>
				</p>
				<div id="qrcode" width="255" height="255"></div>
				<p class="way">
					<input type="button" value="若微信支付已完成，请点击！" onclick="toDetail()" />
				</p>
			</div>

		</div>
	</div>
	<script type="text/javascript">
	
/* 	$(function(){
		$("#qrcode").empty();
		var qrcode = new QRCode(document.getElementById("qrcode"), {
			width : 255,
			height : 255
		});
		qrcode.makeCode("weixin://wxpay/bizpayurl?pr=K3Y9oHW");
		$("#payTypePop").show();
	}); */
		function to_pay(){
			$.ajax({
				type : "get",
				url : "/wxPay",
				success : function(resData) {
					// 使用js生成二维码
					$("#qrcode").empty();
					var qrcode = new QRCode(document.getElementById("qrcode"), {
						width : 255,
						height : 255
					});
					qrcode.makeCode(resData);
				},
				error : function() {
					alert("错误");
				}
			});
			//$("#qrcode").attr("src", "/pay/towechat?omId="+omId+"&viewNo="+viewNo+"&fee=" + fee);
			$("#payTypePop").show();
		}
	</script>
</body>
</html>