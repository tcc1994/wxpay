package com.tcc.wx.pay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public class Back {

	private Log lg = LogFactory.getLog(Back.class);
	
	/** 
	* pc端微信支付之后的回调方法 
	* @param request 
	* @param response 
	* @throws Exception 
	*/  
	@SuppressWarnings("unchecked")
	@RequestMapping(value="wechat_notify_url_pc",method=RequestMethod.POST)  
	public void wechat_notify_url_pc(HttpServletRequest request,HttpServletResponse response) throws Exception{    

	        //读取参数    
	        InputStream inputStream ;    
	        StringBuffer sb = new StringBuffer();    
	        inputStream = request.getInputStream();    
	        String s ;    
	        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));    
	        while ((s = in.readLine()) != null){    
	            sb.append(s);    
	        }    
	        in.close();    
	        inputStream.close();    

	        //解析xml成map    
	        Map<String, String> m = new HashMap<String, String>();    
	        m = XMLUtil.doXMLParse(sb.toString());    

	        //过滤空 设置 TreeMap    
	        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();          
	        Iterator<String> it = m.keySet().iterator();    
	        while (it.hasNext()) {    
	            String parameter = it.next();    
	            String parameterValue = m.get(parameter);    

	            String v = "";    
	            if(null != parameterValue) {    
	                v = parameterValue.trim();    
	            }    
	            packageParams.put(parameter, v);    
	        }    
	        // 微信支付的API密钥    
	        String key = WeChatConfig.APIKEY; // key    

	        lg.info("微信支付返回回来的参数："+packageParams);    
	        //判断签名是否正确    
	        if(PayForUtil.isTenpaySign("UTF-8", packageParams,key)) {    
	            //------------------------------    
	            //处理业务开始    
	            //------------------------------    
	            String resXml = "";    
	            if("SUCCESS".equals((String)packageParams.get("result_code"))){    
	                // 这里是支付成功    
	            //执行自己的业务逻辑开始  
	            String app_id = (String)packageParams.get("appid");  
	                String mch_id = (String)packageParams.get("mch_id");    
	                String openid = (String)packageParams.get("openid");   
	                String is_subscribe = (String)packageParams.get("is_subscribe");//是否关注公众号  

	                //附加参数【商标申请_0bda32824db44d6f9611f1047829fa3b_15460】--【业务类型_会员ID_订单号】  
	                String attach = (String)packageParams.get("attach");  
	                //商户订单号  
	                String out_trade_no = (String)packageParams.get("out_trade_no");    
	                //付款金额【以分为单位】  
	                String total_fee = (String)packageParams.get("total_fee");    
	                //微信生成的交易订单号  
	                String transaction_id = (String)packageParams.get("transaction_id");//微信支付订单号  
	                //支付完成时间  
	                String time_end=(String)packageParams.get("time_end");  

	                lg.info("app_id:"+app_id);  
	                lg.info("mch_id:"+mch_id);    
	                lg.info("openid:"+openid);    
	                lg.info("is_subscribe:"+is_subscribe);    
	                lg.info("out_trade_no:"+out_trade_no);    
	                lg.info("total_fee:"+total_fee);    
	                lg.info("额外参数_attach:"+attach);   
	                lg.info("time_end:"+time_end);   

	                //执行自己的业务逻辑结束  
	                lg.info("支付成功");    
	                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.    
	                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"    
	                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";    

	            } else {    
	                lg.info("支付失败,错误信息：" + packageParams.get("err_code"));    
	                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"    
	                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";    
	            }    
	            //------------------------------    
	            //处理业务完毕    
	            //------------------------------    
	            BufferedOutputStream out = new BufferedOutputStream(    
	                    response.getOutputStream());    
	            out.write(resXml.getBytes());    
	            out.flush();    
	            out.close();  
	        } else{    
	            lg.info("通知签名验证失败");    
	        }    

	    }  
}
