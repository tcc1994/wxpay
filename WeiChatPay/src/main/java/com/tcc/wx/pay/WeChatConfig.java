package com.tcc.wx.pay;


/** 
 * 微信支付配置文件 
 * @author chenp 
 * 
 */  
public class WeChatConfig {  

/** 
* 微信服务号APPID 
*/  
public static String APPID="wx80e6ff62bc5c4ec7";  
/** 
* 微信支付的商户号 
*/  
public static String MCHID="1493084922";  
/** 
* 微信支付的API密钥 
*/  
public static String APIKEY="i1I08B40Ll0B87465b8XVa92XE5gX0X3";  
/** 
* 微信支付成功之后的回调地址【注意：当前回调地址必须是公网能够访问的地址】 
*/  
public static String WECHAT_NOTIFY_URL_PC="http://3s.dkys.org:24457/wechat_notify_url_pc";   
/** 
* 微信统一下单API地址 
*/  
public static String UFDODER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";  
/** 
* true为使用真实金额支付，false为使用测试金额支付（1分） 
*/  
public static String WXPAY="false";  

}  
