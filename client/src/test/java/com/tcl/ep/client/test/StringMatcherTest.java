package com.tcl.ep.client.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMatcherTest {

    public static void main(String[] args) {
	String requestUri = "http://web/ep";
	String epClientPrefix = "http://web/ep";
	int length=epClientPrefix.length();
	String epClientUrlPattern = epClientPrefix.substring(0,length-1);
	Pattern pattern=Pattern.compile(epClientUrlPattern);
	Matcher matcher=pattern.matcher(requestUri.trim());
	if (requestUri.startsWith(epClientPrefix)||matcher.matches()) {
	    System.out.println(true);;
	}
	else {
	    System.out.println(false);;
	}
    }

}
