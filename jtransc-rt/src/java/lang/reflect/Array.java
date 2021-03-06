/*
 * Copyright 2016 Carlos Ballesteros Velasco
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package java.lang.reflect;

import com.jtransc.annotation.JTranscMethodBody;


@SuppressWarnings({"WeakerAccess", "unused"})
public final class Array {
	private Array() {
	}

	@JTranscMethodBody(target = "js", value = "return new JA_L(p0, N.istr(p1));")
	//@JTranscMethodBody(target = "cpp", value = "return new JA_L(p0, N::istr2(p1));")
	@JTranscMethodBody(target = "cpp", value = "return new JA_L(p0, L\"[Ljava/lang/Object;\");")
	@JTranscMethodBody(target = "cs", value = "return new JA_L(p0, N.istr(p1));")
	@JTranscMethodBody(target = "dart", value = "return new JA_L(p0, N.istr(p1));")
	native private static Object newObjectInstance(int length, String desc) throws NegativeArraySizeException;

	public static Object newInstance(Class<?> type, int length) throws NegativeArraySizeException {
		if (type == null) throw new NullPointerException("Array.newInstance");

		if (!type.isPrimitive()) return newObjectInstance(length, "[" + type.getName());
		if (type == Boolean.TYPE) return new boolean[length];
		if (type == Byte.TYPE) return new byte[length];
		if (type == Short.TYPE) return new short[length];
		if (type == Character.TYPE) return new char[length];
		if (type == Integer.TYPE) return new int[length];
		if (type == Long.TYPE) return new long[length];
		if (type == Float.TYPE) return new float[length];
		if (type == Double.TYPE) return new double[length];
		if (type == Void.TYPE) throw new RuntimeException("Invalid Array of void type");
		throw new RuntimeException("Invalid Array.newInstance with " + type.getName());
	}

	public static Object newInstance(Class<?> componentType, int... dimensions) throws IllegalArgumentException, NegativeArraySizeException {
		if (dimensions.length == 1) {
			return newInstance(componentType, dimensions[0]);
		} else {
			throw new RuntimeException("Not implemented dynamically creating multidimensional arrays!");
		}
	}

	@JTranscMethodBody(target = "js", value = "return p0.length;")
	@JTranscMethodBody(target = "cpp", value = "return GET_OBJECT_NPE(JA_0, p0)->length;")
	@JTranscMethodBody(target = "cs", value = "return ((JA_0)p0).length;")
	@JTranscMethodBody(target = "dart", value = "return (p0 as JA_0).length;")
	native public static int getLength(Object array) throws IllegalArgumentException;

	@JTranscMethodBody(target = "js", value = "return p0.get(p1);")
	@JTranscMethodBody(target = "cpp", value = "return GET_OBJECT_NPE(JA_L, p0)->get(p1);")
	@JTranscMethodBody(target = "cs", value = "return ((JA_L)p0)[p1];")
	@JTranscMethodBody(target = "dart", value = "return (p0 as JA_L).data[p1];")
	private static Object getInstance(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((Object[])array)[index];
	}

	public static Object get(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
	    Type elementType = getArrayElementType(array.getClass());
	    if (elementType == Boolean.TYPE) return getBoolean(array, index);
	    if (elementType == Byte.TYPE) return getByte(array, index);
	    if (elementType == Character.TYPE) return getChar(array, index);
	    if (elementType == Short.TYPE) return getShort(array, index);
	    if (elementType == Integer.TYPE) return getInt(array, index);
	    if (elementType == Long.TYPE) return getLong(array, index);
	    if (elementType == Float.TYPE) return getFloat(array, index);
	    if (elementType == Double.TYPE) return getDouble(array, index);
	    return getInstance(array, index);
    }

	public static boolean getBoolean(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((boolean[])array)[index];
	}

	public static byte getByte(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((byte[])array)[index];
	}

	public static char getChar(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((char[])array)[index];
	}

	public static short getShort(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((short[])array)[index];
	}

	public static int getInt(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((int[])array)[index];
	}

	public static long getLong(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((long[])array)[index];
	}

	public static float getFloat(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((float[])array)[index];
	}

	public static double getDouble(Object array, int index) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		return ((double[])array)[index];
	}


	@JTranscMethodBody(target = "js", value = "p0.set(p1, p2);")
	@JTranscMethodBody(target = "cpp", value = "GET_OBJECT_NPE(JA_L, p0)->set(p1, p2);")
	@JTranscMethodBody(target = "cs", value = "((JA_L)p0)[p1] = p2;")
	@JTranscMethodBody(target = "dart", value = "(p0 as JA_L).data[p1] = p2;")
	private static void setInstance(Object array, int index, Object value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
    	((Object[])array)[index] = value;
	}

	public static void set(Object array, int index, Object value) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		Type elementType = getArrayElementType(array.getClass());
		if (elementType == Boolean.TYPE) {
			setBoolean(array, index, (Boolean) value);
		} else if (elementType == Byte.TYPE) {
			setByte(array, index, (Byte) value);
		} else if (elementType == Character.TYPE) {
			setChar(array, index, (Character) value);
		} else if (elementType == Short.TYPE) {
			setShort(array, index, (Short) value);
		} else if (elementType == Integer.TYPE) {
			setInt(array, index, (Integer) value);
		} else if (elementType == Long.TYPE) {
			setLong(array, index, (Long) value);
		} else if (elementType == Float.TYPE) {
			setFloat(array, index, (Float) value);
		} else if (elementType == Double.TYPE) {
			setDouble(array, index, (Double) value);
		} else {
			setInstance(array, index, value);
		}
	}

	public static void setBoolean(Object array, int index, boolean v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((boolean[])array)[index] = v;
	}

	public static void setByte(Object array, int index, byte v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((byte[])array)[index] = v;
	}

	public static void setChar(Object array, int index, char v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((char[])array)[index] = v;
	}

	public static void setShort(Object array, int index, short v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((short[])array)[index] = v;
	}

	public static void setInt(Object array, int index, int v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((int[])array)[index] = v;
	}

	public static void setLong(Object array, int index, long v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((long[])array)[index] = v;
	}

	public static void setFloat(Object array, int index, float v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((float[])array)[index] = v;
	}

	public static void setDouble(Object array, int index, double v) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		((double[])array)[index] = v;
	}

	static private Type getArrayElementType(Class<?> clazz) {
		String name = clazz.getName();
		if (name.charAt(0) != '[') throw new RuntimeException("Not an array");
		try {
			return Class.forName(name.substring(1));
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
