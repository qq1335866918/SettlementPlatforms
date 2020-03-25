package com.tt1000.settlementplatform;

import org.json.simple.JSONObject;

import com.qq.weixiao.wxcode.CampusCode;
/**
 * 调用解码的例子
 *
 */
public class CodeDecodeExample {

	public static void main(String[] args) {

		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMLxqKhe6cGizEY+6MebaQLA/ikY0zYNGz4DynemnYNOFkpiusEE++los16Q/oSAxYQhgojjRZ/Y2fq2YaN3rmUCAwEAAQ==";
		CampusCode campusCode = new CampusCode(publicKey, "1rJioSkDjSou0mTW");  //不需要解释规则
//		CampusCode campusCode = new CampusCode(publicKey, "1rJioSkDjSou0mTW", "{\"o\":{},\"i\":{\"identity_type\":\"4\",\"contract_type\":\"5\"}}");
//		CampusCode campusCode = new CampusCode(publicKey, "1rJioSkDjSou0mTW", "{\"o\":{},\"i\":{\"identity_type\":\"4\",\"entrusts\":\"5\"}}");
		
		//离线码
//		campusCode.setClientTimeStamp(1525696466);  //调试时候，故意对齐本案例中的码的生成时间，正式上线请删除该行代码
//		JSONObject ret = campusCode.decode("http://wx.url.cn/v003.4144010559.p8e1ul.fEHMoIpxm92xzOCS6w71QJ6FCuTLfUIThyuPqnjU8fvVzKpZMYvoZiZ68rXUzGPh3kgjaXgyiDG16_WQCqfVNQ");
		//在线码
		campusCode.setClientTimeStamp(1525746200);  //调试时候，故意对齐本案例中的码的生成时间，正式上线请删除该行代码
		JSONObject ret = campusCode.decode("http://wx.url.cn/v002.4144010559.baV7pm.U53JItdVpdvz-unznNxPuHyEkLHG6sGBpDaw2lxc5vZue_WYqYBSbdxayhFhfOHICKKbpII0K28HVd7ulQO1oA");

		System.out.println(ret); //{"code":0,"data":{"is_offline":0,"contract_type":"1","school_code":"4144010559","card_number":"00001234","identity_type":"0"},"message":"scuess"}

		//只有code为0，表示能正确解码
		if(Integer.parseInt(ret.get("code").toString()) == 0) {
			JSONObject data = (JSONObject) ret.get("data");
			System.out.println(data.get("card_number")); //学工号，key固定为card_number
			System.out.println(data.get("identity_type")); //对应构造方法中，规则解释方法json的字段
			System.out.println(data.get("entrusts")); //对应构造方法中，规则解释方法json的字段
		}else {
			System.out.println(ret.get("code")); //错误码
			System.out.println(ret.get("message"));//错误提示
		}
	}
}
