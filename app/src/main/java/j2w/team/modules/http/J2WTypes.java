package j2w.team.modules.http;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.NoSuchElementException;

/**
 * Created by sky on 15/2/23.
 */
public class J2WTypes {
    /**
     * 获取返回类型 
     * @param context
     * @param contextRawType
     * @param supertype
     * @return
     */
	public static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
		/** 判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口 **/
		if (!supertype.isAssignableFrom(contextRawType))
			throw new IllegalArgumentException();
        
		return resolve(context, contextRawType, getGenericSupertype(context, contextRawType, supertype));
	}

	/**
	 * @param context
	 * @param contextRawType
	 * @param toResolve
	 * @return
	 */
	public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
//		while (true) {
//			if (toResolve instanceof TypeVariable) {
//				TypeVariable<?> typeVariable = (TypeVariable<?>) toResolve;
//				toResolve = resolveTypeVariable(context, contextRawType, typeVariable);
//				if (toResolve == typeVariable) {
//					return toResolve;
//				}
//
//			} else if (toResolve instanceof Class && ((Class<?>) toResolve).isArray()) {
//				Class<?> original = (Class<?>) toResolve;
//				Type componentType = original.getComponentType();
//				Type newComponentType = resolve(context, contextRawType, componentType);
//				return componentType == newComponentType ? original : new GenericArrayTypeImpl(newComponentType);
//
//			} else if (toResolve instanceof GenericArrayType) {
//				GenericArrayType original = (GenericArrayType) toResolve;
//				Type componentType = original.getGenericComponentType();
//				Type newComponentType = resolve(context, contextRawType, componentType);
//				return componentType == newComponentType ? original : new GenericArrayTypeImpl(newComponentType);
//
//			} else if (toResolve instanceof ParameterizedType) {
//				ParameterizedType original = (ParameterizedType) toResolve;
//				Type ownerType = original.getOwnerType();
//				Type newOwnerType = resolve(context, contextRawType, ownerType);
//				boolean changed = newOwnerType != ownerType;
//
//				Type[] args = original.getActualTypeArguments();
//				for (int t = 0, length = args.length; t < length; t++) {
//					Type resolvedTypeArgument = resolve(context, contextRawType, args[t]);
//					if (resolvedTypeArgument != args[t]) {
//						if (!changed) {
//							args = args.clone();
//							changed = true;
//						}
//						args[t] = resolvedTypeArgument;
//					}
//				}
//
//				return changed ? new ParameterizedTypeImpl(newOwnerType, original.getRawType(), args) : original;
//
//			} else if (toResolve instanceof WildcardType) {
//				WildcardType original = (WildcardType) toResolve;
//				Type[] originalLowerBound = original.getLowerBounds();
//				Type[] originalUpperBound = original.getUpperBounds();
//
//				if (originalLowerBound.length == 1) {
//					Type lowerBound = resolve(context, contextRawType, originalLowerBound[0]);
//					if (lowerBound != originalLowerBound[0]) {
//						return new WildcardTypeImpl(new Type[] { Object.class }, new Type[] { lowerBound });
//					}
//				} else if (originalUpperBound.length == 1) {
//					Type upperBound = resolve(context, contextRawType, originalUpperBound[0]);
//					if (upperBound != originalUpperBound[0]) {
//						return new WildcardTypeImpl(new Type[] { upperBound }, EMPTY_TYPE_ARRAY);
//					}
//				}
//				return original;
//
//			} else {
//				return toResolve;
//			}
//		}
        return null;
	}

	private static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
		Class<?> declaredByRaw = declaringClassOf(unknown);

		// We can't reduce this further.
		if (declaredByRaw == null)
			return unknown;

		Type declaredBy = getGenericSupertype(context, contextRawType, declaredByRaw);
		if (declaredBy instanceof ParameterizedType) {
			int index = indexOf(declaredByRaw.getTypeParameters(), unknown);
			return ((ParameterizedType) declaredBy).getActualTypeArguments()[index];
		}

		return unknown;
	}

	private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
		GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
		return genericDeclaration instanceof Class ? (Class<?>) genericDeclaration : null;
	}

	static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
		if (toResolve == rawType)
			return context;

		// We skip searching through interfaces if unknown is an interface.
		if (toResolve.isInterface()) {
			Class<?>[] interfaces = rawType.getInterfaces();
			for (int i = 0, length = interfaces.length; i < length; i++) {
				if (interfaces[i] == toResolve) {
					return rawType.getGenericInterfaces()[i];
				} else if (toResolve.isAssignableFrom(interfaces[i])) {
					return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
				}
			}
		}

		// Check our supertypes.
		if (!rawType.isInterface()) {
			while (rawType != Object.class) {
				Class<?> rawSupertype = rawType.getSuperclass();
				if (rawSupertype == toResolve) {
					return rawType.getGenericSuperclass();
				} else if (toResolve.isAssignableFrom(rawSupertype)) {
					return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, toResolve);
				}
				rawType = rawSupertype;
			}
		}

		// We can't resolve this further.
		return toResolve;

	}

	private static int indexOf(Object[] array, Object toFind) {
		for (int i = 0; i < array.length; i++) {
			if (toFind.equals(array[i]))
				return i;
		}
		throw new NoSuchElementException();
	}
}
