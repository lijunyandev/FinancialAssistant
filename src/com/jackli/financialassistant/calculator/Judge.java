package com.jackli.financialassistant.calculator;

public class Judge {
	
	public String judge(String string, String c) {
		switch (string.charAt(string.length() - 1)) {
		case '+':
		case '-':
		case '×':
		case '÷':
			//如果最后一个字符已经是运算符了，则改为最新输入的运算符
			string = string.substring(0, string.length() - 1) + c;
			break;
		default:
			//否则直接添上运算符
			string += c;
			break;
		}
		return string;
	}

//	public static String dispose(String string) {
//		int leng = string.length() - 1;
//		Character character;
//		if (0 == leng) {
//			return "error";
//		}
//		for (int i = 0; i < leng; i++) {
//			character = string.charAt(i);
//			if (Character.isLetter(character)) {
//				return "error";
//			}
//		}
//
//		return string;
//	}

	//判断在什么情况下，在数字后面加“.”
	//只有当前输入中的数字是一个整数，不带小数点的情况下点击“.”才会在后面加上“.”
	public String judgePoint(String string) {	//123-->123.   123+456-->123+456.
		int p = string.length() - 1;
		boolean flag = true;
		//获取最后一个字符
		Character tmp = string.charAt(p);
		//如果总长度为1，则代表是一个个位数，则直接在后面加“.”
		if (0 == p){
			string += ".";
		}
		//如果最后一个是数字且长度不为1（大于1）
		if (Character.isDigit(tmp) && 0 != p) {
			while (flag) {
				//如果是一个算式，即其中包含不是数字的运算符且不是“.”，(且还没退出说明后面这个数字中间没有“.”)则在后面加上“.”
				if (!Character.isDigit(tmp)) {
					flag = false;
					if (tmp != '.')
						string += ".";
				}
				if (0 == --p && (tmp != '.')) {
					string += ".";
					break;
				}
				tmp = string.charAt(p);
			}
		}
		return string;
	}

	//判断是否是四则运算符
	public static boolean isMathOperator(Character c) {
		switch (c) {
		case '+':
		case '-':
		case '×':
		case '÷':
			return true;
		default:
			return false;
		}
	}

	//判断输入的是否为数字
	public String digit_judge(String string, String c, boolean flag) {
		//如果是0或者是刚刚“=”计算出值，则直接覆盖，否则在后面加上该数字
		if ("0".equals(string)) {
			string = c;
		} else if (flag) {
			string = c;
		} else
			string += c;
		return string;
	}

	//结果返回判断
	public String digit_dispose(String string) {
		if ("error".equals(string)) {
			return string;
		}
		Double double1 = new Double(string);	//字符串转换成Double类型
		if (double1 > 999999999999999.0)	//设置最大阀值
			return "∞";
//		long l = (long) (double1 * 1e4);
//		double1 = (Double) (l / 1e4);
		string = "" + double1;
		return string;
	}
}
