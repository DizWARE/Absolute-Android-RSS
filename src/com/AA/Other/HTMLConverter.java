/*Copyright 2010 University Of Utah Android Development Group
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.AA.Other;

/***
 * Used to convert HTML escape characters to readable Unicode characters
 */
public class HTMLConverter 
{	
	/***
	 * Given an escape character (&#<digits>), return the represented value
	 * 
	 * @param escapeCharacter - String with the format &#<digits>
	 * @return - the represented value.
	 * 
	 * Luck for us, java uses the same unicode conversion as HTML.
	 * Basically this rips out the number and converts it into the character it maps to
	 */
	public static String convertEscapeChar(String escapeCharacter)
	{
		String charCode = escapeCharacter.substring(escapeCharacter.indexOf("#")+1);
		return "" + (char) Integer.parseInt(charCode);
	}
}
