package com.tcl.mie.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class XXXX {

	public static void main(String[] args) throws IOException {

		System.out.println("aaa\\nbbb");
		System.out.println("aaa\nbbb");

		OutputStream os = new FileOutputStream("a.txt");
		os.write("fasdfa".getBytes());
	}

}
