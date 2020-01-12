import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
题目描述：
清一色是麻将番种之一，指由一种花色的序数牌组成的和牌.
数字1-9，每个数字最多有4张牌
我们不考虑具体花色，我们只看数字组合。
刻子：三张一样的牌；如: 111, 222, 333, ..., 999
顺子：三张连续的牌；如: 123, 234, 345, ..., 789
对子：两张相同的牌；如: 11, 22, 33, ..., 99
需要实现一个程序，判断给定牌，是否可以和牌（胡牌）。
和牌要求：

麻将牌张数只能是 2, 5, 8, 11, 14
给定牌可以组合成，除1个对子以外其他都是刻子或顺子
举例： - "11" -> "11", 1对子，可以和牌
"11122233" -> "111"+"222"+"33", 2刻子，1对子，可以
"11223344567" -> "11"+"234"+"234"+"567", 1对子，3顺子，可以
-> "123"+"123"+"44"+"567", 另一种组合，也可以
输入描述:
合法C字符串，只包含'1'-'9'，且已经按从小到大顺序排好；字符串长度不超过15。同一个数字最多出现4次，与实际相符。
输出描述:
C字符串，"yes"或者"no"

 * @author Administrator
 *
 */
public class MajiangHuPai {
  public static void main(String[] args) {
	  System.out.println(isHupai("55"));
	  System.out.println(isHupai("55666"));
	  System.out.println(isHupai("12355666"));
	  System.out.println(isHupai("12355667788"));
	for(int i=1;i<1000;i++) {
		String pai=genratePai();
		try {
		  System.out.print(pai+" ");
	      System.out.println(isHupai(pai));	
		}catch (Exception e) {
			System.out.println(pai+":"+e.getMessage());
		}
	}
	
}

private static String genratePai() {
	int length=new Random( ).nextInt(6)+1;
	if(length==1) {
		length=2;
	}
	if(length==2) {
		length=5;
	}
	if(length==3) {
		length=8;
	}
	if(length==4) {
		length=11;
	}
	if(length==5) {
		length=14;
	}
	if(length==6) {
		length=new Random( ).nextInt(12)+2;
	}
	List<Integer> list=new ArrayList<>();
	Random ram=new Random( ); 
	for(int i=1;i<=length;i++) {
		list.add(ram.nextInt(9)+1);
	}
	Collections.sort(list);
	StringBuffer sb=new StringBuffer();
	for(int i=0;i<list.size();i++) {
		sb.append(list.get(i));
	}
	return sb.toString();
}

private static Boolean isHupai(String tt) {
	if(tt==null||"".equals(tt)) {
		return Boolean.FALSE;
	}
	checklength(tt);
	Map<Integer,Integer> map=generateEmptyMap();
	
	dealToMap(map,tt);
	lengthMoreThan4(map);
	return checkCanHupai(map);
	
	
}

private static Map<Integer, Integer> generateEmptyMap() {
	Map<Integer,Integer> map=new LinkedHashMap();
		/*
		 * for(Integer n=1;n<10;n++) { map.put(n.toString(),0); }
		 */
	return map;
}

private static Boolean checkCanHupai(Map<Integer, Integer> map) {
	List<String> list=new ArrayList<>();
	for(Integer key:map.keySet()) {	
		if(map.get(key)>=2) {
			map.put(key,map.get(key)-2);
			Map<Integer, Integer> tmp=new LinkedHashMap<>();
			tmp.putAll(map);
		
		    checkSHUNZI(map,list,key);		   
			map.put(key,map.get(key)+2);
		}
	}
	return list.size()>0;
}

/**
 *  根据刻字出现的位置 对剩下的牌进行顺子组合 成功 则写入list
 * @param map
 * @param list
 * @param position
 */
private static void checkSHUNZI(Map<Integer, Integer> map, List<String> list,Integer key) {
	StringBuffer sb=new StringBuffer();
	sb.append(key).append(key);
	for(Integer item:map.keySet()) {
		if(map.get(item)<=0) {
			continue;
		}
		while(map.get(item)>0) {
		if(map.get(item)==3) {
			sb.append(item).append(item).append(item);	
			map.put(item,0);
			continue;
		}
	    if(map.containsKey(item+1)&&map.get(item+1)>0
	    	&&map.containsKey(item+2)&&map.get(item+2)>0){
	    	sb.append(item).append(item+1).append(item+2);
	    	map.put(item,map.get(item)-1);
	    	map.put(item+1,map.get(item+1)-1);
	    	map.put(item+2,map.get(item+2)-1);
	    }
	    else {
	    	System.out.print(item+" 不能组成顺子");
	    	return;
	    }
		
	}
	}
	System.out.print(sb.toString());
	list.add(sb.toString());
}

private static void lengthMoreThan4(Map<Integer, Integer> map) {
	for(Map.Entry<Integer, Integer> item:map.entrySet()) {
		if(item.getValue()>4) {
			throw new RuntimeException("same num less than 4");
		}
	}
	
}

private static void dealToMap(Map<Integer,Integer> map,String tt) {
	
	for(int i=0;i<tt.length();i++) {
		String item=tt.substring(i, i+1);
		try {
			map.put(Integer.valueOf(item),map.getOrDefault(Integer.valueOf(item),0)+1);
		}catch (Exception e) {
			throw new RuntimeException("only deal num");
		}
		
	}
}

private static void checklength(String tt) {
	int len=tt.length();
	if(len==2||len==5||len==8||len==11||len==14) {
		return;
	}
	throw new RuntimeException("length illeagl");
}
}
