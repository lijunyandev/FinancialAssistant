package com.jackli.financialassistant.calculator;

import java.util.LinkedList;
import java.util.List;


public class GetValue {
	/*
	public String bracke_dispose(String string) {

		int flag = 0, flag1 = 0, i = 0;
		int leng = string.length();
		List<Integer> list = new LinkedList<Integer>();
		Character c;
		String str = "", tmp = "", tmp3 = "", tmp4 = "";

		while (true) {
			if (i >= leng)
				break;

			c = string.charAt(i);

			if ('(' == c)
				list.add(0, i);

			if (')' == c) {
				if (0 == list.size()) {
					return "error";
				}

				str = string.substring(list.get(0) + 1, i);
				// tmp = this.special_dispose(str);
				tmp = this.alg_dispose(str);
				if ("error".equals(tmp))
					return "error";

				tmp3 = string.substring(0, list.get(0));
				tmp4 = string.substring(i + 1, leng);
				string = tmp3 + tmp + tmp4;

				flag = str.length() + 2;
				flag1 = tmp.length();
				leng += (flag1 - flag);
				i += (flag1 - flag);
				list.remove(0);
			}
			i++;
		}

		if (list.size() != 0) {
			return "error";
		}
		return string;
	}
	*/
	//处理加减乘除
	public String alg_dispose(String string) {
		if ("error".equals(string)) {
			return "error";
		}
		Character c;
		String str = "";
		List<Double> list = new LinkedList<Double>();
		double tmp = 0;
		boolean add_flag = false;
		for (int i = 0; i < string.length(); i++) {//158+369*65
			c = string.charAt(i);
			if (!Judge.isMathOperator(c)) {	//判断是不是+-*/操作符
				str += c;
			} else {
				if (str.length() != 0)
					list.add(new Double(str));
				str = "";
				switch (c) {
				case '+':
					if (2 == list.size()) {
						if (add_flag) {
							tmp = list.get(0) + list.get(1);
							list.clear();
							list.add(tmp);
						} else {
							tmp = list.get(0) - list.get(1);
							list.clear();
							list.add(tmp);
						}
						break;
					}
					add_flag = true;
					break;
				case '-':
					if (2 == list.size()) {
						if (add_flag) {
							tmp = list.get(0) + list.get(1);
							list.clear();
							list.add(tmp);
						} else {
							tmp = list.get(0) - list.get(1);
							list.clear();
							list.add(tmp);
						}
						add_flag = false;
						break;
					}
					break;
				case '×':
					if (i == (string.length() - 1))
						return "error";
					//乘号后面的一个数保存下来
					while (!(i == (string.length() - 1))
							&& !Judge.isMathOperator((c = string.charAt(i + 1)))) {
						str += c;
						i++;
					}
					if (str.length() != 0)
						list.add(new Double(str));
					str = "";
					//如果是a*b,则直接相乘，如果是a+b*c则先乘
					if (list.size() == 2) {
						tmp = list.get(0) * list.get(1);
						list.clear();
						list.add(0, tmp);
					} else {
						tmp = list.get(2) * list.get(1);
						list.remove(2);
						list.remove(1);
						list.add(tmp);
					}
					break;
				case '÷':
					if (i == (string.length() - 1))
						return "error";
					while (!(i == (string.length() - 1))
							&& !Judge.isMathOperator((c = string.charAt(i + 1)))) {
						str += c;
						i++;
					}

					if (str.length() != 0)
						list.add(new Double(str));
					str = "";

					if (list.size() == 2) {
						tmp = list.get(0) / list.get(1);
						list.clear();
						list.add(0, tmp);
					} else {
						tmp = list.get(2) / list.get(1);
						list.remove(2);
						list.remove(1);
						list.add(tmp);
					}
					break;
				default:
					break;
				}
			}
		}

		if (str.length() != 0)
			list.add(new Double(str));
		if (2 == list.size()) {
			if (add_flag) {
				tmp = list.get(0) + list.get(1);
				list.clear();
				list.add(tmp);
			} else {
				tmp = list.get(0) - list.get(1);
				list.clear();
				list.add(tmp);
			}
		}
		if (list.size() != 0)
			string = "" + list.get(0);
		return string;
	}
/*
	public String get_r_digit(String string, int index) {
		// 得到index右边的第一个数；
		String tmp = "";
		int length = string.length(), j = index;
		Character character;

		if (index >= length)
			tmp = "error";

		while (true) {
			if (j == length)
				break;

			character = string.charAt(j);
			if (Character.isDigit(character) || (character == '.')) {
				tmp += character;
			} else
				break;
			j++;
		}

		if (0 == tmp.length())
			tmp = "error";
		return tmp;
	}

	public String get_l_digit(String string, int index) {
		// 得到index左边的第一个数；
		String tmp = "";
		int j = index;
		Character c;

		if (index < 0)
			tmp = "error";

		while (true) {
			if (j < 0)
				break;

			c = string.charAt(j);
			if (Character.isDigit(c) || (c == '.')) {
				tmp = "" + c + tmp;
			} else
				break;
			j--;
		}

		if (0 == tmp.length())
			tmp = "error";

		return tmp;
	}

	public String advanced_dispose(String string) {

		string = this.bracke_dispose(string);
		string = this.alg_dispose(string);
		return string;
	}*/
}
