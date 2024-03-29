/*
 *      Copyright 2009-2011 Battams, Derek
 *       
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 */
package com.google.code.webhooks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.crypto.SecretKey;

/**
 * A javax.crypto.SecretKey implementation for Google Code web hooks
 * 
 * Use this SecretKey implementation to encode your Google Code project's web hooks secret
 * key to verify web hook messages received from Google Code.
 * 
 * Complete example showing how to verify the HMAC-MD5 authentication is available here:
 * http://webhooks.googlecode.com/
 * 
 * @version $Id$
 */
public class GoogleCodeSecretKey implements SecretKey {

	/*
	 * Given a file, attempt to read the stored Google Code secret key from it and return it on success;
	 * throws various File exceptions on failure
	 */
	static final private String readKey(File f) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(f));
		String key = r.readLine().trim();
		try {
			if(r.readLine() != null)
				throw new IOException("Google Code secret key can only be a single line of input!");
		} finally {
			r.close();
		}
		return key;
	}
	
	/**
	 * Generated by Eclipse
	 */
	private static final long serialVersionUID = -5778932727411827891L;
	
	/**
	 * The String object representing the user's secret key
	 */
	private String key;
	
	/**
	 * Create a new secret key based on the argument
	 * @param key The secret's value
	 */
	public GoogleCodeSecretKey(String key) {
		this.key = key;
	}
	
	/**
	 * Create a new secret based on the contents of the given File object
	 * @param f The file from which the key's value will be read
	 * @throws FileNotFoundException Thrown if the given File object does not exist
	 * @throws IOException Thrown if read errors occur or if the file contains more than one line of text (GC key can only be a single line)
	 */
	public GoogleCodeSecretKey(File f) throws IOException {
		this(readKey(f));
	}
	
	/**
	 * 
	 * @return The String value of the key; mainly useful only for debugging purposes
	 */
	public String getStringKey() {
		return key;
	}

	/*
	 * (non-Javadoc)
	 * @see java.security.Key#getAlgorithm()
	 */
	public String getAlgorithm() {
		return "HmacMD5";
	}

	/*
	 * (non-Javadoc)
	 * @see java.security.Key#getEncoded()
	 */
	public byte[] getEncoded() {
		return key.getBytes(Charset.forName("UTF-8"));
	}

	/*
	 * (non-Javadoc)
	 * @see java.security.Key#getFormat()
	 */
	public String getFormat() {
		return "RAW";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(!(o instanceof GoogleCodeSecretKey))
			return false;
		
		GoogleCodeSecretKey k = (GoogleCodeSecretKey)o;
		return k.getStringKey().equals(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return key.hashCode();
	}
}