package occ;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.javacpp.tools.*;

@Properties(value = @Platform(compiler = "cpp11",

		includepath = { "/usr/local/include/opencascade", "." }, //
		preloadpath = { "/path/to/deps/" }, //
		linkpath = { "/usr/local/lib" }, //
		link = { "TKernel" }, //
		include = { //
				//"Standard_DefineAlloc.hxx",//
				//"Standard.hxx", //
				//			"Standard_Std.hxx", //
				//			"Standard_Size.hxx", //
				//"Standard_TypeDef.hxx",
				//"Standard_values.h",
				//"stddef.h",
				//"stdlib.h",
				//"Standard_Macro.hxx", //
				//"Standard_Boolean.hxx", //
				//"Standard_Integer.hxx", //
				//"Standard_Real.hxx", //
				//"Standard_Character.hxx", //
				//"Standard_ExtCharacter.hxx", //
				//"Standard_CString.hxx", //
				//"Standard_ExtString.hxx", //
				//"Standard_Address.hxx", //

				//		"Standard_PrimitiveTypes.hxx", //
				"Standard_Transient.hxx", //
				"Standard_Handle.hxx", //
				"Standard_OStream.hxx", //
				"Standard_Type.hxx", //
				"occ-handle-adapter.hxx", //
				//        		"Standard_Std.hxx",
				//        		"Standard_Address.hxx",
				//        		"Standard_math.hxx",
				//        		"Standard_TypeDef.hxx",
				//        		"Standard_Type.hxx",
				//
				//        		"Standard.hxx",
				"FakeOccDemoApi.hxx", }, //
		preload = { "" }//
), target = "occ.TKernel")

@Downcast(classcache = true)

public class TKernelConfig implements InfoMapper {
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.PARAMETER })
	@Adapter(value = "occ_smart_ptr_adpter") // Refers to the C++ adapter class
	public @interface OccSmartPtr { // Creates a Java annotation type to be used for smart pointers
		/** template type */
		String value() default "";
	}

	public void map(InfoMap infoMap) {
		infoMap.put(new Info("_LIBCPP_INLINE_VISIBILITY").cppTypes().annotations());
		infoMap.put(new Info("Standard_EXPORT", "").cppTypes().annotations());
		infoMap.put(new Info("Standard_FALLTHROUGH").cppTypes().annotations());
		infoMap.put(new Info("Standard_NODISCARD").cppTypes().annotations());
		infoMap.put(new Info("DEFINE_STANDARD_ALLOC").cppTypes().annotations());
		infoMap.put(new Info("Standard_OVERRIDE").cppTypes().annotations());
		infoMap.put(new Info("Standard_THREADLOCAL").cppTypes().annotations());
		infoMap.put(new Info("Standard_EXPORTEXTERN").cppTypes().annotations());
		infoMap.put(new Info("Standard_EXPORTEXTERNC").cppTypes().annotations());
		infoMap.put(new Info("Standard_IMPORT").cppTypes().annotations());
		infoMap.put(new Info("Standard_IMPORTC").cppTypes().annotations());
		infoMap.put(new Info("Standard_ENABLE_DEPRECATION_WARNINGS").cppTypes().annotations());
		infoMap.put(new Info("Standard_UNUSED").cppTypes().annotations());
		//infoMap.put(new Info("DEFINE_STANDARD_RTTIEXT").cppTypes().annotations());
		//infoMap.put(new Info("DEFINE_STANDARD_ALLOC").cppText("void XXX (){}"));
		//infoMap.put(new Info("TARGET_OS_IPHONE","0").cppTypes().annotations());

		// following maps C++ smart pointer (opencascade::handle) to the c++ adapter class by attaching the annotation
		// @MySmartPtr to the corresponding java adapter class  parameters whose corresponding C++ type is opencascade::handle
		infoMap.put(new Info("opencascade::handle").skip().annotations("@Downcast(base=\"Standard_Transient\")", "@OccSmartPtr"));
		//infoMap.put(new Info("Standard_Transient").cppTypes("Standard_Transient").annotations());
		infoMap.put(new Info("__QNX__").define(false));
		infoMap.put(new Info("_MYSKIP__").define(false));
		infoMap.put(new Info("_WIN32").define(false));
		infoMap.put(new Info("__JAVACPP__").define(true));
		infoMap.put(new Info("strcasecmp").define(false));
		infoMap.put(new Info("defined(_WIN32) && !defined(OCCT_STATIC_BUILD) && !defined(HAVE_NO_DLL)").define(false));
		infoMap.put(new Info("Standard_Integer").cppTypes("int"));
		infoMap.put(new Info("Standard_Real").cppTypes("double"));
		infoMap.put(new Info("Standard_Boolean").cppTypes("boolean"));
		infoMap.put(new Info("Standard_ShortReal").cppTypes("float"));
		infoMap.put(new Info("Standard_Character").cppTypes("int8_t"));
		infoMap.put(new Info("Standard_Byte").cppTypes("uint8_t"));
		infoMap.put(new Info("Standard_Address").cppTypes("intptr_t"));
		infoMap.put(new Info("Standard_Size").cppTypes("size_t"));

		// Unicode primitives, char16_t, char32_t
		infoMap.put(new Info("Standard_Utf8Char").cppTypes("int8_t")); //!< signed   UTF-8 char
		infoMap.put(new Info("Standard_Utf8UChar").cppTypes("uint8_t")); //!< unsigned UTF-8 char
		infoMap.put(new Info("Standard_ExtCharacter").cppTypes("char16_t"));
		infoMap.put(new Info("Standard_Utf16Char").cppTypes("char16_t"));
		infoMap.put(new Info("Standard_Utf32Char").cppTypes("char32_t"));

		infoMap.put(new Info("Standard_WideChar").cppTypes("wchar_t"));

		infoMap.put(new Info("Standard_CString").cppTypes("char*"));
		infoMap.put(new Info("Standard_ExtString").cppTypes("char16_t*"));

		infoMap.put(new Info("CHAR_BIT").cppTypes("8"));
		infoMap.put(new Info("BITSPERBYTE").cppTypes("8"));
		infoMap.put(new Info("Standard_True").javaText("public native @MemberGetter @Const @ByRef int _Stadard_True();"));
		infoMap.put(new Info("Standard_False").javaText("public native @MemberGetter @Const @ByRef int _Stadard_False();"));
		//infoMap.put(new Info("Standard_Transient.hxx").linePatterns("public:\\s+DEFINE_STANDARD_ALLOC", "_ALLOC").skip());
		//infoMap.put(new Info("DEFINE_STANDARD_ALLOC").cppText("#define DEFINE_STANDARD_ALLOC void somename(){}").skip());
		infoMap.put(new Info("DEFINE_STANDARD_ALLOC").cppText("#define DEFINE_STANDARD_ALLOC"));

	}

}
