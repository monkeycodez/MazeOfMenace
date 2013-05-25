/*******************************************************************************
 * Copyright (c) 2013 Matthew Gruda.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     matthew - initial API and implementation
 ******************************************************************************/
package engine.image;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class TextureDB {

	private static HashMap<String, Texture> texs = new HashMap<String, Texture>();

	public static Texture getTexture(String s) {

		if (texs.containsKey(s)) {
			return texs.get(s);
		}
		try {
			texs.put(s,new Texture(getExtensionFormat(s),
					new FileInputStream(new File(s))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return texs.get(s);
	}

	private static String getExtensionFormat(String s) {
		String ext = s.substring(s.lastIndexOf('.')+1);
		if ("PNG".equalsIgnoreCase(ext)) {
			return "PNG";
		} else if ("GIF".equalsIgnoreCase(ext)) {
			return "GIF";
		} else if ("JPG".equalsIgnoreCase(ext)) {
			return "JPG";
		} else if ("TGA".equalsIgnoreCase(ext)) {
			return "TGA";
		} else {
			throw new BadFormatException(
					"Only png, gif, jpg, tga extensions are supported, extension supplied is: "
							+ ext);
		}
	}

	public static class NotInitedException extends Error {

		private static final long serialVersionUID = 1L;

		public NotInitedException(String s) {
			super(s);
		}
	}

	public static class BadFormatException extends Error {

		private static final long serialVersionUID = 1L;

		public BadFormatException(String s) {
			super(s);
		}
	}

}
